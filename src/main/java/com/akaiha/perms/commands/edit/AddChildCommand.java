package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.ChildDao;
import com.akaiha.perms.entity.dao.GroupDao;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class AddChildCommand implements BasicCommand {
	
	private Perms plugin;
	
	public AddChildCommand(Perms plugin) {
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
	            	GroupDao dao = new GroupDao();
	        		ChildDao cDao = new ChildDao();
	        		String output;
	        		if (dao.has(args[0]) && !cDao.has(args[0], args[1]) && !cDao.has(args[1], args[0])) {
	        			cDao.create(args[0], args[1]);
	        			output = "&aChild &f" + args[1] + " &aAdded To Group&f " + args[0] + " &a!";
	        		} else {
	        			output = "&aChild &f" + args[1] + " &aCan Not Be Created For Group&f " + args[0] + " &a!";
	        		}
	        		sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', output)));
	            }
			});
		} else {
			sender.sendMessage(new TextComponent("/perm edit addchild <group> <child>"));
		}
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.addchild";
	}
}
