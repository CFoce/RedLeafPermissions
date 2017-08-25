package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class HelpGroupCommand implements BasicCommand {
	
	private Perms plugin;
	
	public HelpGroupCommand(Perms plugin) {
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
			
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.help";
	}
	
	private void printHelp(CommandSender sender) {
		if(sender.hasPermission("perms.edit.create")) {
			sender.sendMessage(new TextComponent("/perm edit create <group>"));
		}
		if(sender.hasPermission("perms.edit.delete")) {
			sender.sendMessage(new TextComponent("/perm edit delete <group>"));
		}
		if(sender.hasPermission("perms.edit.addchild")) {
			sender.sendMessage(new TextComponent("/perm edit addchild <group> <child>"));
		}
		if(sender.hasPermission("perms.edit.addperm")) {
			sender.sendMessage(new TextComponent("/perm edit addperm <group> <perm> [bungee]"));
		}
		if(sender.hasPermission("perms.edit.addplayer")) {
			sender.sendMessage(new TextComponent("/perm edit addplayer <group> <player>"));
		}
		if(sender.hasPermission("perms.edit.addprefix")) {
			sender.sendMessage(new TextComponent("/perm edit addprefix <group> <pefix>"));
		}
		if(sender.hasPermission("perms.edit.addserver")) {
			sender.sendMessage(new TextComponent("/perm edit addserver <group> <server>"));
		}
		if(sender.hasPermission("perms.edit.removechild")) {
			sender.sendMessage(new TextComponent("/perm edit removechild <group> <child>"));
		}
		if(sender.hasPermission("perms.edit.removeperm")) {
			sender.sendMessage(new TextComponent("/perm edit removeperm <group> <perm> [bungee]"));
		}
		if(sender.hasPermission("perms.edit.removeplayer")) {
			sender.sendMessage(new TextComponent("/perm edit removeplayer <group> <player>"));
		}
		if(sender.hasPermission("perms.edit.removeprefix")) {
			sender.sendMessage(new TextComponent("/perm edit removeprefix <group>"));
		}
		if(sender.hasPermission("perms.edit.removeserver")) {
			sender.sendMessage(new TextComponent("/perm edit removeserver <group> <server>"));
		}
	}
}