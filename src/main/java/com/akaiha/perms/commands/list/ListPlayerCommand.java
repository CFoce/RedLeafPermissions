package com.akaiha.perms.commands.list;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.Player;
import com.akaiha.perms.entity.dao.PlayerDao;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ListPlayerCommand implements BasicCommand
{
	private Perms plugin;
	public ListPlayerCommand(Perms plugin)
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
	            	PlayerDao dao = new PlayerDao();
	                List<Player> list = dao.getByGroup(args[0]);
	            	plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
	                    @Override
	                    public void run() {
	                    	String output = "Group: " + args[0] + " Players: ";
	                    	for(int i = 0; i < list.size(); i++) {
	                    		if (i != list.size() - 1) {
	                    			output += list.get(i).getName() + ", ";
	                    		} else {
	                    			output += list.get(i).getName();
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
		return "redleaf.group.listplayer";
	}
}