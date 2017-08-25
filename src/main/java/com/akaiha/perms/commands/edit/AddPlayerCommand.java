package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.GroupDao;
import com.akaiha.perms.entity.dao.PlayerDao;
import com.akaiha.core.util.UtilPlayer;
import com.akaiha.core.util.mojang.HttpProfileRepository;
import com.akaiha.core.util.mojang.Profile;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class AddPlayerCommand implements BasicCommand {
	
	private Perms plugin;
	
	public AddPlayerCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;
		
		if (args.length > 0) {
			plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
	            @Override
	            public void run() {
	            	GroupDao dao = new GroupDao();
	        		PlayerDao pDao = new PlayerDao();
	        		Profile[] profile = new HttpProfileRepository("minecraft").findProfilesByNames(args[1]);
	        		String output;
	        		if (profile.length > 0) {
	        			if (dao.has(args[0]) && !pDao.has(profile[0].getId(), args[0])) {
		        			pDao.create(UtilPlayer.insertDashUUID(profile[0].getId()), profile[0].getName(),args[0]);
		        			output = "&aPlayer &f" + args[1] + " &aAdded To Group&f " + args[0] + " &a!";
		        		} else {
		        			output = "&aPlayer &f" + args[1] + " &aCan Not Be Added To Group&f " + args[0] + " &a!";
		        		}
	        		} else {
	        			output = "&aPlayer &f" + args[1] + " &aNot Found!";
	        		}
	        		sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', output)));
	            }
			});
		} else {
			sender.sendMessage(new TextComponent("/perm edit addplayer <group> <player>"));
		}
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.addplayer";
	}
}
