package com.akaiha.redleaf.commands.group;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.entity.dao.PermDao;

import net.md_5.bungee.api.CommandSender;

public class RemovePermFromGroupCommand implements BasicCommand
{
	RedLeaf plugin;
	public RemovePermFromGroupCommand(RedLeaf plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args)
	{
		if (sender.hasPermission(getPermission()))
			return false;

		PermDao pDao = new PermDao();
		if (pDao.has(args[0], args[1])) {
			pDao.delete(args[0], args[1]);
		}
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.removeperm";
	}
}
