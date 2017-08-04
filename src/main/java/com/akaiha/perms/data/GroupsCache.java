package com.akaiha.perms.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.akaiha.perms.Perms;
import com.akaiha.perms.entity.Child;
import com.akaiha.perms.entity.Group;
import com.akaiha.perms.entity.GroupMemory;
import com.akaiha.perms.entity.Perm;
import com.akaiha.perms.entity.Server;
import com.akaiha.perms.entity.dao.ChildDao;
import com.akaiha.perms.entity.dao.GroupDao;
import com.akaiha.perms.entity.dao.PermDao;
import com.akaiha.perms.entity.dao.ServerDao;

import net.md_5.bungee.api.config.ServerInfo;

public class GroupsCache {

	private static Perms plugin;
	public static volatile ConcurrentHashMap<String, GroupMemory> sGroups;
	public static volatile ConcurrentHashMap<String, GroupMemory> sDefaults;
	
	public GroupsCache(Perms plugin) {
		GroupsCache.plugin = plugin;
	}
	
	public static void groupsToMemory() {
		ConcurrentHashMap<String, GroupMemory> defMap = new ConcurrentHashMap<String, GroupMemory>();
		ServerDao serverDao = new ServerDao();
		PermDao permDao = new PermDao();
		GroupDao groupDao = new GroupDao();
		
		List<Group> groups = groupDao.getAll();
		for (int j = 0; j < groups.size(); j++) {
			GroupMemory gMem = new GroupMemory();
			gMem.setName(groups.get(j).getName());
			gMem.setPrefix(groups.get(j).getPrefix());
			
			List<Perm> perms = permDao.getByGroup(gMem.getName());
			List<String> addperms = new ArrayList<String>();
			List<String> antiperms = new ArrayList<String>();
			List<String> bungee = new ArrayList<String>();
			List<String> children = new ArrayList<String>();
			List<String> servers = new ArrayList<String>();
			
			for (int i = 0; i < perms.size(); i++) {
				if (perms.get(i).getBungee()) {
					bungee.add(perms.get(i).getPerm());
				} else {
					String temp = perms.get(i).getPerm();
					if (temp.charAt(0) == '-') {
						temp = temp.substring(1);
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
			
			List<Server> serv = serverDao.getByGroup(gMem.getName());
			for (int i = 0; i < serv.size(); i++) {
				servers.add(serv.get(i).getName());
			}
			
			loadChildren(children,gMem.getName());
			
			gMem.setPerms(addperms);
			gMem.setAntiperms(antiperms);
			gMem.setBungee(bungee);
			gMem.setServers(servers);
			gMem.setChildren(children);
			defMap.put(groups.get(j).getName(), gMem);
		}
		sGroups = defMap;
	}
	
	private static void loadChildren(List<String> children, String group) {
		ChildDao childDao = new ChildDao();
		List<Child> child = childDao.getByGroup(group);
		for (int i = 0; i < child.size(); i++) {
			children.add(child.get(i).getChildName());
			loadChildren(children,child.get(i).getChildName());
		}
	}
	
	public static void defaultsToMemory() {
		ConcurrentHashMap<String, GroupMemory> defMap = new ConcurrentHashMap<String, GroupMemory>();
		ServerDao serverDao = new ServerDao();
		PermDao permDao = new PermDao();
		Map<String, ServerInfo> map = plugin.getProxy().getServers();
		for (Map.Entry<String, ServerInfo> entry : map.entrySet()) {
			List<Perm> perms = new ArrayList<Perm>();
			List<Server> defaults = serverDao.getDefaultsByServer(entry.getValue().getName());
			GroupMemory gMem = new GroupMemory();
			for (int i = 0; i < defaults.size(); i++) {
				perms.addAll(permDao.getByGroup(defaults.get(i).getGroupName()));
			}
			List<String> addperms = new ArrayList<String>();
			List<String> antiperms = new ArrayList<String>();
			List<String> bungee = new ArrayList<String>();
			for (int i = 0; i < perms.size(); i++) {
				if (perms.get(i).getBungee()) {
					bungee.add(perms.get(i).getPerm());
				} else {
					String temp = perms.get(i).getPerm();
					if (temp.charAt(0) == '-') {
						temp = temp.substring(1);
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
			gMem.setPerms(addperms);
			gMem.setAntiperms(antiperms);
			gMem.setBungee(bungee);
			defMap.put(entry.getValue().getName(), gMem);
		}
		sDefaults = defMap;
	}
	
	public void allNull() {
		plugin = null;
		sGroups = null;
		sDefaults = null;
	}
}
