package com.akaiha.perms.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.akaiha.perms.entity.Player;
import com.akaiha.perms.entity.dao.PlayerDao;

public class PlayersCache {

	public static volatile ConcurrentHashMap<String, List<String>> playerGroups;

	public static void playersToMemory() {
		ConcurrentHashMap<String, List<String>> defMap = new ConcurrentHashMap<String, List<String>>();
		PlayerDao pDao = new PlayerDao();
		List<Player> players = pDao.getAll();
		
		for (int i = 0; i < players.size(); i++) {
			String UUID = players.get(i).getUuid();
			if (!defMap.containsKey(UUID)) {
				defMap.put(UUID, new ArrayList<String>());
			}
			defMap.get(UUID).add(players.get(i).getGroupName());
		} 
		
		playerGroups = defMap;
	}
	
	public static void playerToMemory(String UUID) {
		PlayerDao pDao = new PlayerDao();
		if (pDao.has(UUID)) {
			List<Player> player = pDao.getByUUID(UUID);
			List<String> groups = new ArrayList<String>();
			for (int i = 0; i < player.size(); i++) {
				groups.add(player.get(i).getGroupName());
			}
			playerGroups.remove(UUID);
			playerGroups.put(UUID, groups);
		}
	}
	
	public void allNull() {
		playerGroups = null;
	}
}
