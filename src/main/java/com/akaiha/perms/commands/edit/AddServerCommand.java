package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.GroupDao;
import com.akaiha.perms.entity.dao.ServerDao;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class AddServerCommand implements BasicCommand {
	
	private Perms plugin;
	
	public AddServerCommand(Perms plugin) {
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
	        		ServerDao sDao = new ServerDao();
	        		String output;
	        		if (dao.has(args[0]) && !sDao.has(args[0], args[1])) {
	        			if (args.length > 2 && args[2].equalsIgnoreCase("true")) {
	        				sDao.create(args[0], args[1], true);
	        				output = "&aServer &f" + args[1] + " &aAdded To Group&f " + args[0] + " &a As Default!";
	        			} else {
	        				sDao.create(args[0], args[1]);
	        				output = "&aServer &f" + args[1] + " &aAdded To Group&f " + args[0] + " &a!";
	        			}
	        		} else {
	        			output = "&aServer &f" + args[1] + " &aCan Not Be Added To Group&f " + args[0] + " &a!";
	        		}
	        		sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', output)));
	            }
			});
		} else {
			sender.sendMessage(new TextComponent("/perm edit addserver <group> <server> [default]"));
		}
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.addserver";
	}
}
