package com.akaiha.redleaf.listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.akaiha.redleaf.entity.Child;
import com.akaiha.redleaf.entity.Group;
import com.akaiha.redleaf.entity.Perm;
import com.akaiha.redleaf.entity.Player;
import com.akaiha.redleaf.entity.Server;
import com.akaiha.redleaf.entity.dao.ChildDao;
import com.akaiha.redleaf.entity.dao.PermDao;
import com.akaiha.redleaf.entity.dao.PlayerDao;
import com.akaiha.redleaf.entity.dao.ServerDao;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class PermsListener implements Listener {

	private PlayerDao playerDao = new PlayerDao();
	private ChildDao childDao = new ChildDao();
	private PermDao permDao = new PermDao();
	private ServerDao serverDao = new ServerDao();
	private Plugin plugin;
	private Map<String,String> permSet = new ConcurrentHashMap<String,String>();
	private Map<String,String> permSetDefaults = new ConcurrentHashMap<String,String>();
	
	public PermsListener(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void connected(final ServerConnectedEvent event) {
		getDefaults(event);
		getNonDefaults(event);
	}
	
	private List<Perm> getAllPerms(String serverName, List<Group> groups) {
		List<Perm> perms = new ArrayList<Perm>();
		List<Child> child = new ArrayList<Child>();
		plugin.getLogger().severe("" + groups.size());
		for (int i = 0; i < groups.size(); i++) {
			perms.addAll(permDao.getByGroup(groups.get(i).getName()));
			child = childDao.getByGroup(groups.get(i).getName());
			for (int j = 0; j < child.size(); j++) {
				perms.addAll(permDao.getByGroup(child.get(j).getChildName()));
				List<Group> group = new ArrayList<Group>();
				Group g = new Group();
				g.setName(child.get(j).getChildName());
				group.add(g);
				perms.addAll(getAllPerms(serverName, group));
			}
		}
		
		return perms;
	}
	
	private void processPerms(ProxiedPlayer player, String uuid, List<Perm> perms, Map<String,String> set) {
		for (int i = 0; i < perms.size(); i++) {
			if (perms.get(i).getBungee()) {
				player.setPermission(perms.get(i).getPerm(), true);
			} else {
				set.put(uuid, set.get(uuid) + "," + perms.get(i).getPerm());
			}
		}
	}
	
	private void sendPerms(String uuid, ServerInfo server, Map<String,String> set) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF(permSetDefaults.get(uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.sendData("Perms", stream.toByteArray());
	}
	
	private void getDefaults(final ServerConnectedEvent event) {
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	ProxiedPlayer player = event.getPlayer();
        		String uuid = player.getUniqueId().toString().replaceAll("-", "");
        		permSetDefaults.put(uuid, uuid);
        		ServerInfo server = event.getServer().getInfo();
        		String serverName = server.getName();
        		List<Perm> perms = new ArrayList<Perm>();
        		
        		List<Server> defaults = serverDao.getDefaultsByServer(serverName);
        		for (int i = 0; i < defaults.size(); i++) {
        			perms.addAll(permDao.getByGroup(defaults.get(i).getGroupName()));
        		}
            	
            	plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
                    @Override
                    public void run() {
                    	processPerms(player,uuid,perms,permSetDefaults);
                    	
                		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
                            @Override
                            public void run() {
                            	sendPerms(uuid,server,permSetDefaults);
                            }
                		});
                    }
        		}, 1L, TimeUnit.MILLISECONDS);
            }
		});
	}
	
	private void getNonDefaults(final ServerConnectedEvent event) {
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	ProxiedPlayer player = event.getPlayer();
        		String uuid = player.getUniqueId().toString().replaceAll("-", "");
        		permSet.put(uuid, uuid);
        		ServerInfo server = event.getServer().getInfo();
        		String serverName = server.getName();
        		List<Perm> perms = new ArrayList<Perm>();
        		if(playerDao.has(uuid)) {
        			List<Player> groups = playerDao.getByUUID(uuid);
        			List<Group> group = new ArrayList<Group>();
        			for (int i = 0; i < groups.size(); i++) {
        				Group g = new Group();
        				g.setName(groups.get(i).getGroupName());
        				group.add(g);
        			}
        			perms.addAll(getAllPerms(serverName, group));
            	
            	plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
                    @Override
                    public void run() {
                    	processPerms(player,uuid,perms,permSet);
                    	
                		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
                            @Override
                            public void run() {
                            	sendPerms(uuid,server,permSet);
                            }
                		});
                    }
        		}, 1L, TimeUnit.MILLISECONDS);
        		}
            }
		});
	}
}