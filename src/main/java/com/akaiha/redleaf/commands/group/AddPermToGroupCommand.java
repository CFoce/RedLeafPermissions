package com.akaiha.redleaf.commands.group;

import java.util.Arrays;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.entity.dao.GroupDao;
import com.akaiha.redleaf.entity.dao.PermDao;

import net.md_5.bungee.api.CommandSender;

public class AddPermToGroupCommand implements BasicCommand
{
	RedLeaf plugin;
	private volatile String[] args;
	
	public AddPermToGroupCommand(RedLeaf plugin)
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
        		PermDao pDao = new PermDao();
        		if (dao.has(args[0]) && !pDao.has(args[0], args[1])) {
        			if (args.length > 2 && args[2].equalsIgnoreCase("true")) {
        				pDao.create(args[0], args[1], true);
        			} else {
        				pDao.create(args[0], args[1]);
        			}
        		}
            }
		});
		
		return false;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group.addperm";
	}
}
