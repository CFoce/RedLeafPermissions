package com.akaiha.redleaf.commands.group;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.entity.Group;
import com.akaiha.redleaf.entity.dao.GroupDao;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ListGroupCommand implements BasicCommand
{
	RedLeaf plugin;
	public ListGroupCommand(RedLeaf plugin)
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
