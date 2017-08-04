package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.PermDao;

import net.md_5.bungee.api.CommandSender;

public class RemovePermCommand implements BasicCommand {
	
	private Perms plugin;
	
	public RemovePermCommand(Perms plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;

		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	PermDao pDao = new PermDao();
        		if (pDao.has(args[0], args[1])) {
        			pDao.delete(args[0], args[1]);
        		}
            }
		});
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.removeperm";
	}
}
