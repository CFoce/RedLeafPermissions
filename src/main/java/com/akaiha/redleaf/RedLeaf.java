package com.akaiha.redleaf;

import com.akaiha.redleaf.config.Config;
import com.akaiha.redleaf.database.DatabaseConfig;

import net.md_5.bungee.api.plugin.Plugin;

public class RedLeaf extends Plugin {

	public static Config config;
	
	public void onEnable() {
		config = new Config(this);
		new DatabaseConfig(this);
	}
	
	public void onDisable() {}
}
