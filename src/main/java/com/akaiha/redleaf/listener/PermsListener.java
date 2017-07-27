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
import com.akaiha.redleaf.entity.dao.GroupDao;
import com.akaiha.redleaf.entity.dao.PermDao;
import com.akaiha.redleaf.entity.dao.PlayerDao;
import com.akaiha.redleaf.entity.dao.ServerDao;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
	
	public PermsListener(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void connected(ServerConnectedEvent event) {
		getNonDefaults(event);
	}
	
	private List<Perm> getAllPerms(String serverName, List<Group> groups) {
		List<Perm> perms = new ArrayList<Perm>();
		List<Child> child = new ArrayList<Child>();
		for (int i = 0; i < groups.size(); i++) {
			if (serverDao.has(groups.get(i).getName(), serverName)) {
				perms.addAll(permDao.getByGroup(groups.get(i).getName()));
				child = childDao.getByGroup(groups.get(i).getName());
				for (int j = 0; j < child.size(); j++) {
					List<Group> group = new ArrayList<Group>();
					Group g = new Group();
					g.setName(child.get(j).getChildName());
					group.add(g);
					perms.addAll(getAllPerms(serverName, group));
				}
			}
		}
		return perms;
	}
	
	private String[][] processPerms(ProxiedPlayer player, String uuid, List<Perm> perms) {
		List<String> addperms = new ArrayList<String>();
		List<String> antiperms = new ArrayList<String>();
		for (int i = 0; i < perms.size(); i++) {
			if (perms.get(i).getBungee()) {
				player.setPermission(perms.get(i).getPerm(), true);
			} else {
				String temp = perms.get(i).getPerm();
				if (temp.contains("-")) {
					temp = temp.replace("-", "");
					if (!addperms.contains(temp)) {
						antiperms.add(temp);
					}
				} else {
					if (antiperms.contains(temp)) {
						antiperms.remove(temp);
					}
					addperms.add(temp);
				}
			}
		}
		String[][] array = new String[2][];
		array[0] = addperms.toArray(new String[addperms.size()]);
		array[1] = antiperms.toArray(new String[antiperms.size()]);
		return array;
	}
	
	private void sendPerms(String channel, ServerInfo server, JsonObject jObj) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
        	out.writeUTF(channel);
        	out.writeUTF(new Gson().toJson(jObj));
    		server.sendData("Return", stream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void getNonDefaults(final ServerConnectedEvent event) {
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	ProxiedPlayer player = event.getPlayer();
        		String uuid = player.getUniqueId().toString();
        		ServerInfo server = event.getServer().getInfo();
        		String serverName = server.getName();
        		List<Perm> perms = new ArrayList<Perm>();
        		
        		JsonObject jObj = new JsonObject();
        		jObj.addProperty("uuid", uuid);
        		
        		List<Server> defaults = serverDao.getDefaultsByServer(serverName);
        		for (int i = 0; i < defaults.size(); i++) {
        			perms.addAll(permDao.getByGroup(defaults.get(i).getGroupName()));
        		}
        		
        		if(playerDao.has(uuid)) {
        			List<Player> groups = playerDao.getByUUID(uuid);
        			if (groups.get(0).getName() != player.getName()) {
        				plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
        		            @Override
        		            public void run() {
        		            	playerDao.changeName(uuid, player.getName());
        		            }
        				});
        			}
        			List<Group> group = new ArrayList<Group>();
        			String prefixs = null;
        			for (int i = 0; i < groups.size(); i++) {
        				if (serverDao.has(groups.get(i).getGroupName(), serverName)) {
        					GroupDao dao = new GroupDao();
            				Group g = dao.get(groups.get(i).getGroupName());
            				String prefix = g.getPrefix();
            				if (prefix != null) {
            					if (prefixs == null) {
            						prefixs = "";
            					}
            					prefixs+= prefix;
            				}
            				group.add(g);
        				}
        			}
        			jObj.addProperty("groups", prefixs);
        			perms.addAll(getAllPerms(serverName, group));
        		}
        		
            	plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
                    @Override
                    public void run() {
                    	String[][] arrayPerms = processPerms(player,uuid,perms);
                    	
                    	JsonArray addperms = new JsonArray();
                        for(String entry : arrayPerms[0]) {
                            addperms.add(entry);
                        }
                    	jObj.add("perms", addperms);
                    	
                    	JsonArray antiperms = new JsonArray();
                        for(String entry : arrayPerms[1]) {
                        	antiperms.add(entry);
                        }
                    	jObj.add("antiperms", antiperms);
                    	
                		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
                            @Override
                            public void run() {
                            	sendPerms("perms",server,jObj);
                            }
                		});
                    }
        		}, 1L, TimeUnit.MILLISECONDS);
            }
		});
	}
}