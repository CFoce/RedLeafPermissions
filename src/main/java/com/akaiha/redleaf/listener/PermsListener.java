package com.akaiha.redleaf.listener;

import java.util.Collection;
import java.util.Iterator;

import com.akaiha.redleaf.entity.dao.PlayerDao;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PermsListener implements Listener {

	public PlayerDao playerDao = new PlayerDao();
	
	@EventHandler
	public void listener(ServerConnectedEvent event) {
		ProxiedPlayer player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		if(playerDao.has(uuid)) {
			
		}
		
		
		
	}
	
	@EventHandler
	public void listener(ServerConnectEvent event) {
		ProxiedPlayer player = event.getPlayer();
		Collection<String> perms = player.getPermissions();
		for (Iterator<String> iterator = perms.iterator(); iterator.hasNext();) {
	        player.setPermission(iterator.next(), false);
	    }
	}
}