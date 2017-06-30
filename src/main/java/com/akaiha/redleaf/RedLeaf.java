package com.akaiha.redleaf;

import com.akaiha.redleaf.commands.PermsCommand;
import com.akaiha.redleaf.config.Config;
import com.akaiha.redleaf.database.DatabaseConfig;

import net.md_5.bungee.api.plugin.Plugin;

public class RedLeaf extends Plugin
{
	public static Config config;

	public void onEnable()
	{
		config = new Config(this);
		new DatabaseConfig(this);

		// REGISTERING COMMANDS
		registerCommands();
	}

	public void onDisable()
	{
		config.save();
		config = null;
	}

	public void registerCommands()
	{
		getProxy().getPluginManager().registerCommand(this, new PermsCommand(this));
	}
}
