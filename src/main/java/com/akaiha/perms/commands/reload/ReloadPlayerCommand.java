package com.akaiha.perms.commands.reload;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;

import net.md_5.bungee.api.CommandSender;

public class ReloadPlayerCommand implements BasicCommand
{
	private Perms plugin;
	public ReloadPlayerCommand(Perms plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final String[] args)
	{
		if (!sender.hasPermission(getPermission()))
			return false;

		if (args.length >= 1) {
			plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
	            @Override
	            public void run() {
	            	
	            }
			});
		}
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "perms.list.help";
	}
}