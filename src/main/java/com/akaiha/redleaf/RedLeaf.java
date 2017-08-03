package com.akaiha.redleaf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.akaiha.redleaf.commands.PermsCommand;
import com.akaiha.redleaf.config.Config;
import com.akaiha.redleaf.database.DatabaseConfig;
import com.akaiha.redleaf.entity.Child;
import com.akaiha.redleaf.entity.Group;
import com.akaiha.redleaf.entity.GroupMemory;
import com.akaiha.redleaf.entity.Perm;
import com.akaiha.redleaf.entity.Server;
import com.akaiha.redleaf.entity.dao.ChildDao;
import com.akaiha.redleaf.entity.dao.GroupDao;
import com.akaiha.redleaf.entity.dao.PermDao;
import com.akaiha.redleaf.entity.dao.ServerDao;
import com.akaiha.redleaf.listener.PermsListener;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

public class RedLeaf extends Plugin
{
	public static Config config;
	public volatile ConcurrentHashMap<String, GroupMemory> sGroups;  //key is group name
	public volatile ConcurrentHashMap<String, GroupMemory> sDefaults; //key is server name

	public void onEnable() {
		config = new Config(this);
		new DatabaseConfig(this);

		// REGISTERING COMMANDS
		registerCommands();
		// register listener
		registerListener();
		registerChannels();
		
		defaultsToMemory();
		groupsToMemory();
	}

	public void onDisable() {
		config.save();
		config = null;
		sGroups = null;
		sDefaults = null;
	}

	private void registerCommands() {
		getProxy().getPluginManager().registerCommand(this, new PermsCommand(this));
	}
	
	private void registerListener() {
		getProxy().getPluginManager().registerListener(this, new PermsListener(this));
	}
	
	private void registerChannels() {
		getProxy().registerChannel("Return");
	}
	
	public void groupsToMemory() {
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
	
	private void loadChildren(List<String> children, String group) {
		ChildDao childDao = new ChildDao();
		List<Child> child = childDao.getByGroup(group);
		for (int i = 0; i < child.size(); i++) {
			children.add(child.get(i).getChildName());
			loadChildren(children,child.get(i).getChildName());
		}
	}
	
	public void defaultsToMemory() {
		ConcurrentHashMap<String, GroupMemory> defMap = new ConcurrentHashMap<String, GroupMemory>();
		ServerDao serverDao = new ServerDao();
		PermDao permDao = new PermDao();
		Map<String, ServerInfo> map = getProxy().getServers();
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
}
