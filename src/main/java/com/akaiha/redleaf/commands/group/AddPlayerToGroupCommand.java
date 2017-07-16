package com.akaiha.redleaf.commands.group;

import java.util.Arrays;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.entity.dao.GroupDao;
import com.akaiha.redleaf.entity.dao.PlayerDao;
import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;

import net.md_5.bungee.api.CommandSender;

public class AddPlayerToGroupCommand implements BasicCommand
{
	RedLeaf plugin;
	private volatile String[] args;
	
	public AddPlayerToGroupCommand(RedLeaf plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] arg)
	{
		if (!sender.hasPermission(getPermission()))
			return false;
		
		this.args = Arrays.copyOf(arg,arg.length);
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	GroupDao dao = new GroupDao();
        		PlayerDao pDao = new PlayerDao();
        		Profile[] profile = new HttpProfileRepository("minecraft").findProfilesByNames(args[1]);
        		if (dao.has(args[0]) && !pDao.has(profile[0].getId(), args[0])) {
        			pDao.create(profile[0].getId(), profile[0].getName(),args[0]);
        		}
            }
		});
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.addplayer";
	}
}
