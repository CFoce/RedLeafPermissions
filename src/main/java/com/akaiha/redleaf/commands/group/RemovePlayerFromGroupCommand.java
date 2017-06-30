package com.akaiha.redleaf.commands.group;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;

import net.md_5.bungee.api.CommandSender;

public class RemovePlayerFromGroupCommand implements BasicCommand
{
	RedLeaf plugin;
	public RemovePlayerFromGroupCommand(RedLeaf plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args)
	{
		if (sender.hasPermission(getPermission()))
			return false;

		// TODO: EXECUTE REMOVE PLAYER FROM THE GROUP
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.removeplayer";
	}
}
