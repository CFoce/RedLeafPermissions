package com.akaiha.redleaf.listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.entity.GroupMemory;
import com.akaiha.redleaf.entity.Player;
import com.akaiha.redleaf.entity.dao.PlayerDao;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PermsListener implements Listener {

	private RedLeaf plugin;
	private ConcurrentHashMap<String, List<String>> playerGroups = new ConcurrentHashMap<String, List<String>>();
	private ConcurrentHashMap<String, Object> playerLocks = new ConcurrentHashMap<String, Object>();
	
	public PermsListener(RedLeaf plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void bungeeConnect(PostLoginEvent event) {
		ProxiedPlayer player = event.getPlayer();
		String uuid = event.getPlayer().getUniqueId().toString();
		PlayerDao playerDao = new PlayerDao();
		if(playerDao.has(uuid)) {
			playerLocks.put(uuid, new Object());
			plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
	            @Override
	            public void run() {
	            	List<Player> groups = playerDao.getByUUID(uuid);
	    			List<String> list = new ArrayList<String>();
	    			for (Player p : groups) {
	    				list.add(p.getGroupName());
	    			}
	    			playerGroups.put(uuid, list);
	    			synchronized (playerLocks.get(uuid)) {
	    				playerLocks.get(uuid).notify();
	    			}
	    			playerLocks.remove(uuid);
	    			if (groups.get(0).getName() != player.getName()) {
	    				plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
	    		            @Override
	    		            public void run() {
	    		            	playerDao.changeName(uuid, player.getName());
	    		            }
	    				});
	    			}
	            }
			});
		}
	}
	
	@EventHandler
	public void bungeeDisConnect(PlayerDisconnectEvent event) {
		String uuid = event.getPlayer().getUniqueId().toString();
		if (playerGroups.containsKey(uuid)) {
			playerGroups.remove(uuid);
		}
	}
	
	@EventHandler
	public void serverConnect(ServerConnectEvent event) {
		getJObj(event);
	}
	
	private void processBungeePerms(ProxiedPlayer player, List<String> perms) {
		for (int i = 0; i < perms.size(); i++) {
				player.setPermission(perms.get(i), true);
		}
	}
	
	private void processGroups(ProxiedPlayer player, GroupMemory gMem, JsonArray addperms, JsonArray antiperms) {
		for (String temp : gMem.getPerms()) {
			addperms.add(temp);
		}
        for (String temp : gMem.getAntiperms()) {
        	antiperms.add(temp);
        }
        processBungeePerms(player, gMem.getBungee());
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
	
	private void getJObj(final ServerConnectEvent event) {
		ProxiedPlayer player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		ServerInfo server = event.getTarget();
		String serverName = server.getName();
		
		JsonObject jObj = new JsonObject();
		jObj.addProperty("uuid", uuid);
		
		GroupMemory dMem = plugin.sDefaults.get(serverName);
		JsonArray addperms = new JsonArray();
    	JsonArray antiperms = new JsonArray();
		for (String temp : dMem.getPerms()) {
			addperms.add(temp);
		}
        for (String temp : dMem.getAntiperms()) {
        	antiperms.add(temp);
        }
        processBungeePerms(player, dMem.getBungee());
		
		if(playerLocks.containsKey(uuid)) {
			while (playerLocks.containsKey(uuid)) {
				try {
					synchronized (playerLocks.get(uuid)) {
						playerLocks.get(uuid).wait();
					}
				} catch (InterruptedException e) {}
			}
			String prefixs = null;
			for (String group : playerGroups.get(uuid)) {
				GroupMemory gMem = plugin.sGroups.get(group);
				if (gMem.getServers().contains(serverName)) {
					if (gMem.getPrefix() != null) {
						if (prefixs == null) {
							prefixs = gMem.getPrefix();
						} else {
							prefixs += gMem.getPrefix();
						}
					}
					processGroups(player, gMem, antiperms, antiperms);
					for (String temp : gMem.getChildren()) {
						GroupMemory cMem = plugin.sGroups.get(temp);
						if (cMem.getServers().contains(serverName)) {
							processGroups(player, cMem, antiperms, antiperms);
						}
					}
				}
			}
			
			if (prefixs != null) {
				jObj.addProperty("groups", prefixs);
			}
		}
		
    	jObj.add("perms", addperms);
    	jObj.add("antiperms", antiperms);
    	
    	sendPerms("perms",server,jObj);
	}
}