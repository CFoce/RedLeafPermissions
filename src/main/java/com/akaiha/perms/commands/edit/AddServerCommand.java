package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.GroupDao;
import com.akaiha.perms.entity.dao.ServerDao;

import net.md_5.bungee.api.CommandSender;

public class AddServerCommand implements BasicCommand {
	
	private Perms plugin;
	
	public AddServerCommand(Perms plugin) {
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
        		ServerDao sDao = new ServerDao();
        		if (dao.has(args[0]) && !sDao.has(args[0], args[1])) {
        			if (args.length > 2 && args[2].equalsIgnoreCase("true")) {
        				sDao.create(args[0], args[1], true);
        			} else {
        				sDao.create(args[0], args[1]);
        			}
        		}
            }
		});
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.addserver";
	}
}
