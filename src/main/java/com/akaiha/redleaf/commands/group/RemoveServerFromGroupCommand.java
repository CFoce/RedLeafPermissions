package com.akaiha.redleaf.commands.group;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.entity.dao.ServerDao;

import net.md_5.bungee.api.CommandSender;

public class RemoveServerFromGroupCommand implements BasicCommand
{
	RedLeaf plugin;
	public RemoveServerFromGroupCommand(RedLeaf plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, final String[] args)
	{
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
	public String getPermission()
	{
		return "redleaf.group.removeserver";
	}
}
