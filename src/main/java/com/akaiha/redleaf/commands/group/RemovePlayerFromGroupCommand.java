package com.akaiha.redleaf.commands.group;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.entity.dao.PlayerDao;
import com.akaiha.redleaf.util.UtilPlayer;
import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;

import net.md_5.bungee.api.CommandSender;

public class RemovePlayerFromGroupCommand implements BasicCommand
{
	RedLeaf plugin;
	public RemovePlayerFromGroupCommand(RedLeaf plugin)
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
            	PlayerDao pDao = new PlayerDao();
        		Profile[] profile = new HttpProfileRepository("minecraft").findProfilesByNames(args[1]);
        		if (pDao.has(profile[0].getId(), args[0])) {
        			pDao.delete(UtilPlayer.insertDashUUID(profile[0].getId()),args[0]);
        		}
            }
		});
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.removeplayer";
	}
}
