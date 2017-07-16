package com.akaiha.redleaf.commands.group;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.entity.dao.ChildDao;
import com.akaiha.redleaf.entity.dao.GroupDao;

import net.md_5.bungee.api.CommandSender;

public class AddChildToGroupCommand implements BasicCommand
{
	RedLeaf plugin;
	public AddChildToGroupCommand(RedLeaf plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args)
	{
		if (!sender.hasPermission(getPermission()))
			return false;
		
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	GroupDao dao = new GroupDao();
        		ChildDao cDao = new ChildDao();
        		if (dao.has(args[0]) && !cDao.has(args[0], args[1]) && !cDao.has(args[1], args[0])) {
        			cDao.create(args[0], args[1]);
        		}
            }
		});
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.addchild";
	}
}
