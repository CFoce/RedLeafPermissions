package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.GroupDao;
import com.akaiha.perms.entity.dao.PermDao;

import net.md_5.bungee.api.CommandSender;

public class AddPrefixCommand implements BasicCommand {
	
	private Perms plugin;
	
	public AddPrefixCommand(Perms plugin) {
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
        		PermDao pDao = new PermDao();
        		if (dao.has(args[0]) && !pDao.has(args[0], args[1])) {
        			if (args.length > 2 && args[2].equalsIgnoreCase("true")) {
        				pDao.create(args[0], args[1], true);
        			} else {
        				pDao.create(args[0], args[1]);
        			}
        		}
            }
		});
		
		return false;
	}

	@Override
	public String getPermission() {
		return "perms.edit.addperm";
	}
}