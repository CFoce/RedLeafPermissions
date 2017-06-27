package com.akaiha.redleaf;

import com.akaiha.redleaf.config.Config;
import com.akaiha.redleaf.database.DatabaseConfig;
import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.ProfileRepository;

import net.md_5.bungee.api.plugin.Plugin;

public class RedLeaf extends Plugin {

	public static Config config;
	
	public void onEnable() {
		config = new Config(this);
		new DatabaseConfig(this);
		ProfileRepository repo = new HttpProfileRepository("minecraft"); 
		repo.findProfilesByNames("DietBandit");
	}
	
	public void onDisable() {
		config = null;
	}
}
