package com.akaiha.redleaf.listener;

import com.akaiha.redleaf.entity.dao.PlayerDao;

import net.md_5.bungee.api.connection.ProxiedPlayer;
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
			
		} else {
			
		}
	}
}