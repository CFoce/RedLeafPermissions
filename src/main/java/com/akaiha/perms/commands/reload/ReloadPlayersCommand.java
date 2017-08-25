package com.akaiha.perms.commands.reload;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.data.PlayersCache;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ReloadPlayersCommand implements BasicCommand {
	
	private Perms plugin;
	
	public ReloadPlayersCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;

		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&aStarting Players Reload!")));
            	PlayersCache.playersToMemory();
            	sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&aPlayers Reload Finished!")));
            }
		});
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.reload.players";
	}
}