package com.akaiha.perms.commands.list;

import java.util.List;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.Player;
import com.akaiha.perms.entity.dao.PlayerDao;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ListPlayersCommand implements BasicCommand {
	
	private Perms plugin;
	
	public ListPlayersCommand(Perms plugin) {
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
	                List<Player> list = dao.getByGroup(args[0]);
	                String setup;
	                if (list.size() == 0) {
                		setup = "&aThe group&f " + args[0] + " &adoes not exist!";
                	} else {
                		setup = "&aGroup:&f " + args[0] + " &aPlayers:&f ";
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
			sender.sendMessage(new TextComponent("/perm list players <group>"));
		}
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.list.players";
	}
}
