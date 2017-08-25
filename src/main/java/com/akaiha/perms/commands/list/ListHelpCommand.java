package com.akaiha.perms.commands.list;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ListHelpCommand implements BasicCommand
{
	private Perms plugin;
	public ListHelpCommand(Perms plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final String[] args)
	{
		if (!sender.hasPermission(getPermission()))
			return false;

		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	printHelp(sender);
            }
		});
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "perms.list.help";
	}
	
	private void printHelp(CommandSender sender) {
		if(sender.hasPermission("perms.list.groups")) {
			sender.sendMessage(new TextComponent("/perm list groups"));
		}
		if(sender.hasPermission("perms.list.children")) {
			sender.sendMessage(new TextComponent("/perm list children <group>"));
		}
		if(sender.hasPermission("perms.list.perms")) {
			sender.sendMessage(new TextComponent("/perm list perms <group>"));
		}
		if(sender.hasPermission("perms.list.servers")) {
			sender.sendMessage(new TextComponent("/perm list servers <group>"));
		}
		if(sender.hasPermission("perms.list.players")) {
			sender.sendMessage(new TextComponent("/perm list players <group>"));
		}
		if(sender.hasPermission("perms.list.player")) {
			sender.sendMessage(new TextComponent("/perm list player <player>"));
		}
	}
}