package com.akaiha.redleaf.commands.group;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.entity.Perm;
import com.akaiha.redleaf.entity.dao.PermDao;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ListGroupPermCommand implements BasicCommand
{
	RedLeaf plugin;
	public ListGroupPermCommand(RedLeaf plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final String[] args)
	{
		if (!sender.hasPermission(getPermission()))
			return false;

		if (args.length >= 1) {
			plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
	            @Override
	            public void run() {
	            	PermDao dao = new PermDao();
	                List<Perm> list = dao.getByGroup(args[0]);
	            	plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
	                    @Override
	                    public void run() {
	                    	String output = "Group: " + args[0];
	                    	for(int i = 0; i < list.size(); i++) {
	                    		if (i != list.size() - 1) {
	                    			output += " Perm: " + list.get(i).getPerm() + " Bungee: " + list.get(i).getBungee() + ",";
	                    		} else {
	                    			output += " Perm: " + list.get(i).getPerm() + " Bungee: " + list.get(i).getBungee();
	                    		}
	                    	}
	                    	sender.sendMessage(new TextComponent(output));
	                    }
	        		}, 1L, TimeUnit.MILLISECONDS);
	            }
			});
		}
		
		return true;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.listperm";
	}
}
