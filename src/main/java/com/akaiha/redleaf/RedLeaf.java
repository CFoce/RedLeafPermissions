package com.akaiha.redleaf;

import org.bukkit.plugin.java.JavaPlugin;

import com.akaiha.redleaf.config.Config;
import com.akaiha.redleaf.database.DatabaseConfig;

public class RedLeaf extends JavaPlugin {

	public static Config config;
	
	public void onEnable() {
		config = new Config(this);
		new DatabaseConfig();
	}
	
	public void onDisable() {}
}
