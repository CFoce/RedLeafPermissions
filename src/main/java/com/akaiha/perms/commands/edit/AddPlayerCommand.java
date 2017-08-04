package com.akaiha.perms.commands.edit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.dao.GroupDao;
import com.akaiha.perms.entity.dao.PlayerDao;
import com.akaiha.core.util.UtilPlayer;
import com.akaiha.core.util.mojang.HttpProfileRepository;
import com.akaiha.core.util.mojang.Profile;

import net.md_5.bungee.api.CommandSender;

public class AddPlayerCommand implements BasicCommand {
	
	private Perms plugin;
	
	public AddPlayerCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;
		
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	GroupDao dao = new GroupDao();
        		PlayerDao pDao = new PlayerDao();
        		Profile[] profile = new HttpProfileRepository("minecraft").findProfilesByNames(args[1]);
        		if (dao.has(args[0]) && !pDao.has(profile[0].getId(), args[0])) {
        			pDao.create(UtilPlayer.insertDashUUID(profile[0].getId()), profile[0].getName(),args[0]);
        		}
            }
		});
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.edit.addplayer";
	}
}
