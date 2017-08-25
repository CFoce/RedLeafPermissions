package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.ChildDao;
import com.akaiha.perms.entity.dao.GroupDao;
import com.akaiha.perms.entity.dao.PermDao;
import com.akaiha.perms.entity.dao.PlayerDao;
import com.akaiha.perms.entity.dao.ServerDao;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class DeleteGroupCommand implements BasicCommand {
	
	private Perms plugin;
	
	public DeleteGroupCommand(Perms plugin) {
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
	            	PermDao permDao = new PermDao();
	            	PlayerDao playerDao = new PlayerDao();
	            	ChildDao childDao = new ChildDao();
	            	ServerDao serverDao = new ServerDao();
	            	String output;
	        		if (dao.has(args[0])) {
	        			permDao.deleteByGroup(args[0]);
	        			playerDao.deleteByGroup(args[0]);
	        			childDao.deleteByGroup(args[0]);
	        			serverDao.deleteByGroup(args[0]);
	        			dao.delete(args[0]);
	        			output = "&aGroup &f" + args[0] + " &aDeleted!";
	        		} else {
	        			output = "&aGroup &f" + args[0] + " &aDoes Not Exist!";
	        		}
	        		sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', output)));
	            }
			});
		} else {
			sender.sendMessage(new TextComponent("/perm edit delete <group>"));
		}
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.delete";
	}
}
