package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.GroupDao;

import net.md_5.bungee.api.CommandSender;

public class CreateGroupCommand implements BasicCommand {
	
	private Perms plugin;
	
	public CreateGroupCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;
		
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	GroupDao dao = new GroupDao();
        		if (!dao.has(args[0]))
        			dao.create(args[0]);
            }
		});
			
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.create";
	}
}
