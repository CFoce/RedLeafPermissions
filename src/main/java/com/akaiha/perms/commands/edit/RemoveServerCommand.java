package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.ServerDao;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class RemoveServerCommand implements BasicCommand {
	
	private Perms plugin;
	
	public RemoveServerCommand(Perms plugin) {
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
	            	ServerDao sDao = new ServerDao();
	            	String output;
	        		if (sDao.has(args[0], args[1])) {
	        			sDao.delete(args[0], args[1]);
	        			output = "&aServer &f" + args[1] + " &aRemoved From Group&f " + args[0] + " &a!";
	        		} else {
	        			output = "&aServer &f" + args[1] + " &aDoes Not Exist For Group&f " + args[0] + " &a!";
	        		}
	        		sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', output)));
	            }
			});
		} else {
			sender.sendMessage(new TextComponent("/perm edit removeserver <group> <server>"));
		}
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.removeserver";
	}
}
