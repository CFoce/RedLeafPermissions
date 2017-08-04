package com.akaiha.perms.commands;

import com.akaiha.perms.Perms;
import com.akaiha.perms.data.GroupsCache;

import net.md_5.bungee.api.CommandSender;

public class ReloadCommand implements BasicCommand {

	private Perms plugin;
	
	public ReloadCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;
		
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	GroupsCache.defaultsToMemory();
            	GroupsCache.groupsToMemory();
            }
		});
		
		return false;
	}

	@Override
	public String getPermission() {
		return "perms.reload.help";
	}
}
