package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.ChildDao;

import net.md_5.bungee.api.CommandSender;

public class RemoveChildCommand implements BasicCommand {
	
	private Perms plugin;
	
	public RemoveChildCommand(Perms plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;

		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	ChildDao cDao = new ChildDao();
        		if (cDao.has(args[0], args[1])) {
        			cDao.delete(args[0], args[1]);
        		}
            }
		});
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.removechild";
	}
}
