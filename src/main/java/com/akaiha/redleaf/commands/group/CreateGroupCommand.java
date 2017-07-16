package com.akaiha.redleaf.commands.group;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.entity.dao.GroupDao;

import net.md_5.bungee.api.CommandSender;

public class CreateGroupCommand implements BasicCommand
{
	RedLeaf plugin;
	
	public CreateGroupCommand(RedLeaf plugin)
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
            	GroupDao dao = new GroupDao();
        		if (!dao.has(args[0]))
        			dao.create(args[0]);
            }
		});
			
		return true;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.create";
	}
}
