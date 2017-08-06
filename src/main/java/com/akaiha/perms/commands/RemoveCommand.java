package com.akaiha.perms.commands;

import com.akaiha.perms.Perms;

import net.md_5.bungee.api.CommandSender;

public class RemoveCommand implements BasicCommand {
	
	private Perms plugin;
	
	public RemoveCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;
		
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	// TODO: EXECUTE DEMOTE
            }
		});
		
		return false;
	}

	@Override
	public String getPermission() {
		return "perms.remove";
	}
}