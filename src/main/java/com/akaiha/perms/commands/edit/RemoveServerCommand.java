package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.ServerDao;

import net.md_5.bungee.api.CommandSender;

public class RemoveServerCommand implements BasicCommand {
	
	private Perms plugin;
	
	public RemoveServerCommand(Perms plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;

		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	ServerDao sDao = new ServerDao();
        		if (sDao.has(args[0], args[1])) {
        			sDao.delete(args[0], args[1]);
        		}
            }
		}); 
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.removeserver";
	}
}
