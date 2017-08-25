package com.akaiha.perms.commands.reload;

import com.akaiha.core.util.UtilPlayer;
import com.akaiha.core.util.mojang.HttpProfileRepository;
import com.akaiha.core.util.mojang.Profile;
import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.data.PlayersCache;
import com.akaiha.perms.entity.dao.PlayerDao;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ReloadPlayerCommand implements BasicCommand {
	
	private Perms plugin;
	
	public ReloadPlayerCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;

		if (args.length > 0) {
			plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
	            @Override
	            public void run() {
	        		PlayerDao pDao = new PlayerDao();
	        		Profile[] profile = new HttpProfileRepository("minecraft").findProfilesByNames(args[0]);
	        		String output = "&aPlayer &f" + args[0] + " &aNot Found!";
	        		if (profile.length > 0) {
		        		String UUID = UtilPlayer.insertDashUUID(profile[0].getId());
	        			if (pDao.has(UUID)) {
	        				PlayersCache.playerToMemory(UUID);
	        				output = "&aPlayer &f" + args[0] + " &aReloaded!";
	        			}
	        		}
	        		sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', output)));
	            }
			});
		} else {
			sender.sendMessage(new TextComponent("/perm reload player <player>"));
		}
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.reload.player";
	}
}