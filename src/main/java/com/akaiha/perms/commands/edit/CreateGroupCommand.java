package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.GroupDao;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class CreateGroupCommand implements BasicCommand {
	
	private Perms plugin;
	
	public CreateGroupCommand(Perms plugin) {
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
	            	String output;
	        		if (!dao.has(args[0])) {
	        			dao.create(args[0]);
	        			output = "&aGroup &f" + args[0] + " &aCreated!";
	        		} else {
	        			output = "&aGroup &f" + args[0] + " &aAlready Created!";
	        		}
	        		sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', output)));
	            }
			});
		} else {
			sender.sendMessage(new TextComponent("/perm edit create <group>"));
		}
			
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.create";
	}
}
