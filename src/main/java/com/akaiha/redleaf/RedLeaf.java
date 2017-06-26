package com.akaiha.redleaf;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.akaiha.redleaf.config.Config;

public class RedLeaf extends JavaPlugin {
	
	private Plugin plugin;
	private Config config;
	
	public void onEnable() {
		this.plugin = this;
		this.config = new Config(plugin);
	}
	
	public void onDisable() {}
}
