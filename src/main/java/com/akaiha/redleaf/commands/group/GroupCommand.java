package com.akaiha.redleaf.commands.group;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.BasicCommand;
import com.akaiha.redleaf.enums.GroupCommands;

import net.md_5.bungee.api.CommandSender;

public class GroupCommand implements BasicCommand
{
	private RedLeaf plugin;
	private EnumMap<GroupCommands, BasicCommand> commands;
	
	public GroupCommand(RedLeaf plugin)
	{
		this.plugin = plugin;
		this.commands = new EnumMap<GroupCommands, BasicCommand>(GroupCommands.class);
		loadCommands();
	}

	public void loadCommands()
	{
		commands.put(GroupCommands.CREATE, new CreateGroupCommand(plugin));
		commands.put(GroupCommands.DELETE, new DeleteGroupCommand(plugin));
		commands.put(GroupCommands.LIST, new ListGroupCommand(plugin));
		commands.put(GroupCommands.ADDPLAYER, new AddPlayerToGroupCommand(plugin));
		commands.put(GroupCommands.ADDSERVER, new AddServerToGroupCommand(plugin));
		commands.put(GroupCommands.ADDPERM, new AddPermToGroupCommand(plugin));
		commands.put(GroupCommands.ADDCHILD, new AddChildToGroupCommand(plugin));
		commands.put(GroupCommands.LISTPLAYER, new ListGroupPlayerCommand(plugin));
		commands.put(GroupCommands.LISTSERVER, new ListGroupServerCommand(plugin));
		commands.put(GroupCommands.LISTPERM, new ListGroupPermCommand(plugin));
		commands.put(GroupCommands.LISTCHILD, new ListGroupChildCommand(plugin));
		commands.put(GroupCommands.REMOVEPLAYER, new RemovePlayerFromGroupCommand(plugin));
		commands.put(GroupCommands.REMOVESERVER, new RemoveServerFromGroupCommand(plugin));
		commands.put(GroupCommands.REMOVEPERM, new RemovePermFromGroupCommand(plugin));
		commands.put(GroupCommands.REMOVECHILD, new RemoveChildFromGroupCommand(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args)
	{
		if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help")))
		{
			// TODO: EXECUTE GROUP HELP HERE
			return true;
		}

		if (args.length > 0)
		{
			String subArg = args[0].toLowerCase();
			List<String> subArgs = Arrays.asList(args);
			subArgs.remove(0);
			args = subArgs.toArray(new String[0]);

			if (commands.containsKey(subArg))
			{
				commands.get(subArg).onCommand(sender, args);
				return true;
			}

			// TODO: SEND 'UNKNOWN COMMAND' MESSAGE
		}
		return false;
	}

	@Override
	public String getPermission()
	{
		return "redleaf.group";
	}
}
