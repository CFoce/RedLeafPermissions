package com.akaiha.perms.listener;

import java.util.List;

import com.akaiha.perms.Perms;
import com.akaiha.perms.entity.Player;
import com.akaiha.perms.entity.dao.PlayerDao;

public class UtilListener {

	private Perms plugin;
	
	public UtilListener(Perms plugin) {
		this.plugin = plugin;
	}
	
	public void nameCheck(final String uuid, final String name) {
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
        		PlayerDao playerDao = new PlayerDao();
        		if(playerDao.has(uuid)) {
        			List<Player> groups = playerDao.getByUUID(uuid);
	            	if (groups.get(0).getName() != name) {
	            		playerDao.changeName(uuid, name);
	            	}
        		}
            }
		});
	}
}
