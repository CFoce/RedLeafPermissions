package com.akaiha.perms.commands.list;

import java.util.List;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.Group;
import com.akaiha.perms.entity.dao.GroupDao;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ListGroupsCommand implements BasicCommand {
	
	private Perms plugin;
	
	public ListGroupsCommand(Perms plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final String[] args) {
		if (!sender.hasPermission(getPermission()))
			return false;
		
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	GroupDao dao = new GroupDao();
                List<Group> list = dao.getAll();
                String setup;
                if (list.size() == 0) {
            		setup = "&aThere are no groups in the database!";
            	} else {
            		setup = "&aGroups: ";
            		for(int i = 0; i < list.size(); i++) {
                		setup += "&aName:&f" + list.get(i).getName() + " &aPrefix:&f" + list.get(i).getPrefix();
                		if (i != list.size() - 1) {
                			setup += "&a,&f ";
                		}
                	}
            	}

            	sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', setup)));
            }
		});
		
		return false;
	}

	@Override
	public String getPermission() {
		return "perms.list.groups";
	}
}
