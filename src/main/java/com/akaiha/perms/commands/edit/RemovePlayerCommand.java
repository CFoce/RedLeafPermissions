package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.PlayerDao;
import com.akaiha.core.util.UtilPlayer;
import com.akaiha.core.util.mojang.HttpProfileRepository;
import com.akaiha.core.util.mojang.Profile;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class RemovePlayerCommand implements BasicCommand {
	
	private Perms plugin;
	
	public RemovePlayerCommand(Perms plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;

		if (args.length > 1) {
			plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
	            @Override
	            public void run() {
	            	PlayerDao pDao = new PlayerDao();
	        		Profile[] profile = new HttpProfileRepository("minecraft").findProfilesByNames(args[1]);
	        		String output;
	        		if (profile.length > 0) {
	        			String UUID = UtilPlayer.insertDashUUID(profile[0].getId());
	        			if (pDao.has(UUID, args[0])) {
		        			pDao.delete(UUID,args[0]);
		        			output = "&aPlayer &f" + args[1] + " &aRemoved From Group&f " + args[0] + " &a!";
		        		} else {
		        			output = "&aPlayer &f" + args[1] + " &aDoes Not Exist For Group&f " + args[0] + " &a!";
		        		}
	        		} else {
	        			output = "&aPlayer &f" + args[1] + " &aNot Found!";
	        		}
	        		sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', output)));
	            }
			});
		} else {
			sender.sendMessage(new TextComponent("/perm edit removeplayer <group> <player>"));
		}
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.removeplayer";
	}
}
