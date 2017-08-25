package com.akaiha.perms.commands.reload;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.data.GroupsCache;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ReloadGroupsCommand implements BasicCommand {
	
	private Perms plugin;
	
	public ReloadGroupsCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;

		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&aStarting Groups Reload!")));
            	GroupsCache.defaultsToMemory();
            	sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&aDefault Groups Reloaded!")));
            	GroupsCache.groupsToMemory();
            	sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&aNon-Default Groups Reloaded!")));
            	sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&aGroups Reload Finished!")));
            }
		});
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.reload.groups";
	}
}