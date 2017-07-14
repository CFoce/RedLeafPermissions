package com.akaiha.redleaf;

import com.akaiha.redleaf.commands.PermsCommand;
import com.akaiha.redleaf.config.Config;
import com.akaiha.redleaf.database.DatabaseConfig;
import com.akaiha.redleaf.listener.PermsListener;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

public class RedLeaf extends Plugin
{
	public static Config config;
	//public static

	public void onEnable() {
		config = new Config(this);
		new DatabaseConfig(this);

		// REGISTERING COMMANDS
		registerCommands();
		// register listener
		registerListener();
		BungeeCord.getInstance().registerChannel("Perms");
	}

	public void onDisable() {
		config.save();
		config = null;
	}

	private void registerCommands() {
		getProxy().getPluginManager().registerCommand(this, new PermsCommand(this));
	}
	
	private void registerListener() {
		getProxy().getPluginManager().registerListener(this, new PermsListener());
	}
}
