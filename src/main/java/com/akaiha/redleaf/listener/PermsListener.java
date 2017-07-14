package com.akaiha.redleaf.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.akaiha.redleaf.entity.Child;
import com.akaiha.redleaf.entity.Group;
import com.akaiha.redleaf.entity.Perm;
import com.akaiha.redleaf.entity.Player;
import com.akaiha.redleaf.entity.dao.ChildDao;
import com.akaiha.redleaf.entity.dao.GroupDao;
import com.akaiha.redleaf.entity.dao.PermDao;
import com.akaiha.redleaf.entity.dao.PlayerDao;
import com.akaiha.redleaf.entity.dao.ServerDao;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PermsListener implements Listener {

	private PlayerDao playerDao = new PlayerDao();
	private ChildDao childDao = new ChildDao();
	private PermDao permDao = new PermDao();
	private ServerDao serverDao = new ServerDao();
	private GroupDao groupDao = new GroupDao();
	
	@EventHandler
	public void listener(ServerConnectedEvent event) {
		ProxiedPlayer player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		String serverName = player.getServer().getInfo().getName();
		List<Perm> perms = new ArrayList<Perm>();
		if(playerDao.has(uuid)) {
			List<Player> groups = playerDao.getByUUID(uuid);
			List<Group> group = new ArrayList<Group>();
			for (int i = 0; i < groups.size(); i++) {
				Group g = new Group();
				g.setName(groups.get(i).getName());
				group.add(g);
			}
			perms.addAll(getAllPerms(serverName, group));
		}
		
		String permSet = "";
		for (int i = 0; i < perms.size(); i++) {
			if (perms.get(i).getBungee()) {
				player.setPermission(perms.get(i).getPerm(), true);
			} else {
				permSet += "," + perms.get(i).getPerm();
			}
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
	
	private List<Perm> getAllPerms(String serverName, List<Group> groups) {
		List<Perm> perms = new ArrayList<Perm>();
		List<Child> child = new ArrayList<Child>();
		for (int i = 0; i < groups.size(); i++) {
			if (serverDao.getByGroup(groups.get(i).getName()).get(0).getName() != serverName)
				continue;
			perms.addAll(permDao.getByGroup(groups.get(i).getName()));
			child = childDao.getByGroup(groups.get(i).getName());
			for (int j = 0; j < child.size(); j++) {
				if (serverDao.getByGroup(child.get(j).getGroupName()).get(0).getName() != serverName)
					continue;
				perms.addAll(permDao.getByGroup(child.get(j).getGroupName()));
				List<Group> group = new ArrayList<Group>();
				Group g = new Group();
				g.setName(child.get(j).getGroupName());
				group.add(g);
				perms.addAll(getAllPerms(serverName, group));
			}
		}
		
		return perms;
	}
}