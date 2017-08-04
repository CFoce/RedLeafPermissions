package com.akaiha.perms.listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import com.akaiha.perms.Perms;
import com.akaiha.perms.data.GroupsCache;
import com.akaiha.perms.entity.GroupMemory;
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

	private UtilListener ul;
	
	public PermsListener(Perms plugin) {
		ul = new UtilListener(plugin);
	}
	
	@EventHandler
	public void bungeeConnect(PostLoginEvent event) {
		ul.getGroups(event.getPlayer());
	}
	
	@EventHandler
	public void bungeeDisConnect(PlayerDisconnectEvent event) {
		String uuid = event.getPlayer().getUniqueId().toString();
		if (ul.playerGroups.containsKey(uuid)) {
			ul.playerGroups.remove(uuid);
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
	
	private void sendPerms(ServerInfo server, JsonObject jObj) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
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
		
		GroupMemory dMem = GroupsCache.sDefaults.get(serverName);
		JsonArray addperms = new JsonArray();
    	JsonArray antiperms = new JsonArray();
		for (String temp : dMem.getPerms()) {
			addperms.add(temp);
		}
        for (String temp : dMem.getAntiperms()) {
        	antiperms.add(temp);
        }
        processBungeePerms(player, dMem.getBungee());
		
		if(ul.playerLocks.containsKey(uuid) || ul.playerGroups.containsKey(uuid)) {
			while (ul.playerLocks.containsKey(uuid)) {
				try {
					synchronized (ul.playerLocks.get(uuid)) {
						ul.playerLocks.get(uuid).wait();
					}
					ul.playerLocks.remove(uuid);
				} catch (InterruptedException e) {}
			}
			String prefixs = "";
			for (String group : ul.playerGroups.get(uuid)) {
				GroupMemory gMem = GroupsCache.sGroups.get(group);
				if (gMem.getServers().contains(serverName)) {
					if (gMem.getPrefix() != null) {
						prefixs += gMem.getPrefix();
					}
					processGroups(player, gMem, addperms, antiperms);
					for (String temp : gMem.getChildren()) {
						GroupMemory cMem = GroupsCache.sGroups.get(temp);
						if (cMem.getServers().contains(serverName)) {
							processGroups(player, cMem, addperms, antiperms);
						}
					}
				}
			}
			
			if (prefixs != "") {
				jObj.addProperty("groups", prefixs);
			}
		}
		
    	jObj.add("perms", addperms);
    	jObj.add("antiperms", antiperms);
    	jObj.addProperty("channel", "perms");
    	
    	sendPerms(server,jObj);
	}
}