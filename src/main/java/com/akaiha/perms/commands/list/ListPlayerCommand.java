package com.akaiha.perms.commands.list;

import java.util.ArrayList;
import java.util.List;

import com.akaiha.core.util.UtilPlayer;
import com.akaiha.core.util.mojang.HttpProfileRepository;
import com.akaiha.core.util.mojang.Profile;
import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.Player;
import com.akaiha.perms.entity.dao.PlayerDao;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ListPlayerCommand implements BasicCommand {
	
	private Perms plugin;
	
	public ListPlayerCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;
		
		if (args.length > 0) {
			plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
	            @Override
	            public void run() {
	            	PlayerDao dao = new PlayerDao();
	            	Profile[] profile = new HttpProfileRepository("minecraft").findProfilesByNames(args[0]);
	            	List<Player> list = new ArrayList<Player>();
	            	if (profile.length > 0) {
	            		list = dao.getByUUID(UtilPlayer.insertDashUUID(profile[0].getId()));
	            	} else {
	            		list = dao.getByName(args[0]);
	            	}
	            	String setup;
	            	if (list.size() == 0) {
	            		setup = "&aThe player&f " + args[0] + " &adoes not exist!";
	            	} else {
	            		setup = "&aPlayer:&f " + args[0] + " &aUUID:&f " + list.get(0).getUuid() + " &aGroups:&f ";
	            		for(int i = 0; i < list.size(); i++) {
	            			setup += list.get(i).getName();
	            			if (i != list.size() - 1) {
	                			setup += "&a,&f ";
	                		}
	                	}
	            	}

                	sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', setup)));
	            }
			});
		} else {
			sender.sendMessage(new TextComponent("/perm list player <player>"));
		}
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.list.player";
	}
}