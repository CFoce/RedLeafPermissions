package com.akaiha.perms.commands;

import com.akaiha.perms.Perms;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class HelpCommand implements BasicCommand {
	
	private Perms plugin;
	
	public HelpCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;
		
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	printHelp(sender);
            }
		});
		
		return false;
	}

	@Override
	public String getPermission() {
		return "perms.help";
	}
	
	private void printHelp(CommandSender sender) {
		if(sender.hasPermission("perms.reload.help")) {
			sender.sendMessage(new TextComponent("/perm reload help"));
		}
		if(sender.hasPermission("perms.promote")) {
			sender.sendMessage(new TextComponent("/perm promote <rank> <player>"));
		}
		if(sender.hasPermission("perms.demote")) {
			sender.sendMessage(new TextComponent("/perm demote <rank> <player>"));
		}
		if(sender.hasPermission("perms.list.help")) {
			sender.sendMessage(new TextComponent("/perm list help"));
		}
		if(sender.hasPermission("perms.edit.help")) {
			sender.sendMessage(new TextComponent("/perm edit help"));
		}
		if(sender.hasPermission("perms.remove")) {
			sender.sendMessage(new TextComponent("/perm remove <player>"));
		}
	}
}