package com.akaiha.redleaf.commands.group;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;

import net.md_5.bungee.api.CommandSender;

public class ListGroupServerCommand implements BasicCommand
{
	RedLeaf plugin;
	public ListGroupServerCommand(RedLeaf plugin)
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
            	
            }
		});
		// TODO: EXECUTE LIST SERVER OF THE GROUP
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.listserver";
	}
}
