package com.akaiha.perms.commands.list;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.Group;
import com.akaiha.perms.entity.dao.GroupDao;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ListGroupsCommand implements BasicCommand
{
	private Perms plugin;
	
	public ListGroupsCommand(Perms plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final String[] args)
	{
		if (!sender.hasPermission(getPermission()))
			return false;
		
		plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
            	GroupDao dao = new GroupDao();
                List<Group> list = dao.getAll();
            	plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
                    @Override
                    public void run() {
                    	String output = "Groups:";
                    	for(int i = 0; i < list.size(); i++) {
                    		if (i != list.size() - 1) {
                    			output += " " + list.get(i).getName() + " " + list.get(i).getPrefix() + ",";
                    		} else {
                    			output += " " + list.get(i).getName() + " " + list.get(i).getPrefix();
                    		}
                    	}
                    	sender.sendMessage(new TextComponent(output));
                    }
        		}, 1L, TimeUnit.MILLISECONDS);
            }
		});
		
		return false;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.list";
	}
}
