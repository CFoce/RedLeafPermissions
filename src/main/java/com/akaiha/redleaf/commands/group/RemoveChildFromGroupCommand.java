package com.akaiha.redleaf.commands.group;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.entity.dao.ChildDao;

import net.md_5.bungee.api.CommandSender;

public class RemoveChildFromGroupCommand implements BasicCommand
{
	RedLeaf plugin;
	public RemoveChildFromGroupCommand(RedLeaf plugin)
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
            	ChildDao cDao = new ChildDao();
        		if (cDao.has(args[0], args[1])) {
        			cDao.delete(args[0], args[1]);
        		}
            }
		});
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.removechild";
	}
}