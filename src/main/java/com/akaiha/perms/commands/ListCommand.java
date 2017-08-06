package com.akaiha.perms.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import com.akaiha.core.util.UtilEnum;
import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.list.ListChildrenCommand;
import com.akaiha.perms.commands.list.ListGroupsCommand;
import com.akaiha.perms.commands.list.ListHelpCommand;
import com.akaiha.perms.commands.list.ListPermsCommand;
import com.akaiha.perms.commands.list.ListPlayerCommand;
import com.akaiha.perms.commands.list.ListPlayersCommand;
import com.akaiha.perms.commands.list.ListServersCommand;
import com.akaiha.perms.enums.ListCommands;

import net.md_5.bungee.api.CommandSender;

public class ListCommand implements BasicCommand {
	
	private Perms plugin;
	private EnumMap<ListCommands, BasicCommand> commands;
	
	public ListCommand(Perms plugin) {
		this.plugin = plugin;
		this.commands = new EnumMap<ListCommands, BasicCommand>(ListCommands.class);
		loadCommands();
	}

	public void loadCommands() {
		commands.put(ListCommands.HELP, new ListHelpCommand(plugin));
		commands.put(ListCommands.CHILDREN, new ListChildrenCommand(plugin));
		commands.put(ListCommands.GROUPS, new ListGroupsCommand(plugin));
		commands.put(ListCommands.PERMS, new ListPermsCommand(plugin));
		commands.put(ListCommands.PLAYERS, new ListPlayersCommand(plugin));
		commands.put(ListCommands.SERVERS, new ListServersCommand(plugin));
		commands.put(ListCommands.PLAYER, new ListPlayerCommand(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			commands.get(ListCommands.HELP).onCommand(sender, args);
			return true;
		}

		if (args.length > 0 && UtilEnum.isInEnum(args[0].toUpperCase(), ListCommands.class)) {
			ListCommands subArg = ListCommands.valueOf(args[0].toUpperCase());
			if (commands.containsKey(subArg)) {
				List<String> subArgs = new ArrayList<String>(Arrays.asList(args));
				subArgs.remove(0);
				args = subArgs.toArray(new String[0]);
				commands.get(subArg).onCommand(sender, args);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getPermission() {
		return "perms.list.help";
	}
}