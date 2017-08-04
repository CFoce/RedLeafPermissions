package com.akaiha.perms.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.akaiha.perms.Perms;
import com.akaiha.perms.entity.Player;
import com.akaiha.perms.entity.dao.PlayerDao;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class UtilListener {

	private Perms plugin;
	public ConcurrentHashMap<String, List<String>> playerGroups = new ConcurrentHashMap<String, List<String>>();
	public ConcurrentHashMap<String, Object> playerLocks = new ConcurrentHashMap<String, Object>();
	
	public UtilListener(Perms plugin) {
		this.plugin = plugin;
	}
	
	public void getGroups(ProxiedPlayer player) {
		String uuid = player.getUniqueId().toString();
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
}
