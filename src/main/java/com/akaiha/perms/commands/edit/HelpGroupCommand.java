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
		if(sender.hasPermission("perms.edit.help")) {
			sender.sendMessage(new TextComponent("/perm edit help"));
		}
		
	}
}