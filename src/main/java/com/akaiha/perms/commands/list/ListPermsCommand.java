package com.akaiha.perms.commands.list;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.BasicCommand;
import com.akaiha.perms.entity.Perm;
import com.akaiha.perms.entity.dao.PermDao;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class ListPermsCommand implements BasicCommand {
	
	private Perms plugin;
	
	public ListPermsCommand(Perms plugin) {
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
	            	PermDao dao = new PermDao();
	                List<Perm> list = dao.getByGroup(args[0]);
	                String setup;
	                if (list.size() == 0) {
                		setup = "&aThe group&f " + args[0] + " &adoes not exist!";
                	} else {
                		setup = "&aGroup:&f " + args[0];
                		for(int i = 0; i < list.size(); i++) {
                    		setup += " &aPerm:&f " + list.get(i).getPerm() + " &aBungee:&f " + list.get(i).getBungee();
                    		if (i != list.size() - 1) {
                    			setup += "&a,";
                    		}
                    	}
                	}
                	String output = setup;
                	
	            	plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
	                    @Override
	                    public void run() {
	                    	sender.sendMessage(new TextComponent(output));
	                    }
	        		}, 1L, TimeUnit.MILLISECONDS);
	            }
			});
		} else {
			sender.sendMessage(new TextComponent("/perm list perms <group>"));
		}
		
		return true;
	}

	@Override
	public String getPermission() {
		return "perms.list.perms";
	}
}
