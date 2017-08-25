package com.akaiha.perms;

import java.util.logging.Logger;

import com.akaiha.perms.commands.PermsCommand;
import com.akaiha.perms.config.Config;
import com.akaiha.perms.data.GroupsCache;
import com.akaiha.perms.data.PlayersCache;
import com.akaiha.perms.database.DatabaseConfig;
import com.akaiha.perms.listener.PermsListener;

import net.md_5.bungee.api.plugin.Plugin;

public class Perms extends Plugin {
	
	public static Config config;
	private PermsCommand pc;
	private PermsListener pl;
	private static GroupsCache gc;
	private static PlayersCache pCache;
	private Logger log;

	public void onEnable() {
		log = getLogger();
		log.info("Loading");
		config = new Config(this);
		log.info("Config Loaded");
		new DatabaseConfig(this);
		log.info("Database Configured");
		getProxy().getPluginManager().registerCommand(this, pc = new PermsCommand(this));
		getProxy().getPluginManager().registerListener(this, pl =  new PermsListener(this));
		log.info("Listeners & Commands Registered");
		gc = new GroupsCache(this);
		pCache = new PlayersCache();
		GroupsCache.defaultsToMemory();
		log.info("Default Groups Loaded");
		GroupsCache.groupsToMemory();
		log.info("Non-Default Groups Loaded");
		PlayersCache.playersToMemory();
		log.info("Players Loaded");
		log.info("Loading Finished");
	}

	public void onDisable() {
		log.info("Unloading");
		getProxy().getPluginManager().unregisterCommand(pc);
		getProxy().getPluginManager().unregisterListener(pl);
		log.info("Listeners & Commands UnRegistered");
		pCache.allNull();
		gc.allNull();
		log.info("Database Unloaded From Memory");
		config.save();
		log.info("Config Saved");
		config = null;
		log.info("Unloading Finished");
		log = null;
	}
	
}