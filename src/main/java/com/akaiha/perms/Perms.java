package com.akaiha.perms;

import com.akaiha.perms.commands.PermsCommand;
import com.akaiha.perms.config.Config;
import com.akaiha.perms.data.GroupsCache;
import com.akaiha.perms.database.DatabaseConfig;
import com.akaiha.perms.listener.PermsListener;

import net.md_5.bungee.api.plugin.Plugin;

public class Perms extends Plugin {
	
	public static Config config;
	private PermsCommand pc;
	private PermsListener pl;
	private static GroupsCache gc;

	public void onEnable() {
		config = new Config(this);
		new DatabaseConfig(this);
		getProxy().getPluginManager().registerCommand(this, pc = new PermsCommand(this));
		getProxy().getPluginManager().registerListener(this, pl =  new PermsListener(this));
		getProxy().registerChannel("Return");
		gc = new GroupsCache(this);
		GroupsCache.defaultsToMemory();
		GroupsCache.groupsToMemory();
	}

	public void onDisable() {
		getProxy().getPluginManager().unregisterCommand(pc);
		getProxy().getPluginManager().unregisterListener(pl);
		gc.allNull();
		config.save();
		config = null;
	}
	
}