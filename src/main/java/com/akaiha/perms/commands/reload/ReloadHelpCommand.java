package com.akaiha.perms.commands.reload;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ReloadHelpCommand implements BasicCommand
{
	private Perms plugin;
	public ReloadHelpCommand(Perms plugin)
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
	            	printHelp(sender);
	            }
			});
		}
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "perms.reload.help";
	}
	
	private void printHelp(CommandSender sender) {
		if(sender.hasPermission("perms.reload.groups")) {
			sender.sendMessage(new TextComponent("/perm reload groups"));
		}
		if(sender.hasPermission("perms.reload.player")) {
			sender.sendMessage(new TextComponent("/perm reload player <player>"));
		}
		if(sender.hasPermission("perms.reload.players")) {
			sender.sendMessage(new TextComponent("/perm reload players"));
		}
	}
}