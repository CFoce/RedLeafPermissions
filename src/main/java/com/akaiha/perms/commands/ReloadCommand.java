package com.akaiha.perms.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import com.akaiha.core.util.UtilEnum;
import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.reload.ReloadGroupsCommand;
import com.akaiha.perms.commands.reload.ReloadHelpCommand;
import com.akaiha.perms.commands.reload.ReloadPlayerCommand;
import com.akaiha.perms.commands.reload.ReloadPlayersCommand;
import com.akaiha.perms.enums.ReloadCommands;

import net.md_5.bungee.api.CommandSender;

public class ReloadCommand implements BasicCommand {
	
	private Perms plugin;
	private EnumMap<ReloadCommands, BasicCommand> commands;
	
	public ReloadCommand(Perms plugin) {
		this.plugin = plugin;
		this.commands = new EnumMap<ReloadCommands, BasicCommand>(ReloadCommands.class);
		loadCommands();
	}

	public void loadCommands() {
		commands.put(ReloadCommands.HELP, new ReloadHelpCommand(plugin));
		commands.put(ReloadCommands.GROUPS, new ReloadGroupsCommand(plugin));
		commands.put(ReloadCommands.PLAYER, new ReloadPlayerCommand(plugin));
		commands.put(ReloadCommands.PLAYERS, new ReloadPlayersCommand(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			commands.get(ReloadCommands.HELP).onCommand(sender, args);
			return false;
		}
			
		if (args.length > 0 && UtilEnum.isInEnum(args[0].toUpperCase(), ReloadCommands.class)) {
			ReloadCommands subArg = ReloadCommands.valueOf(args[0].toUpperCase());
			if (commands.containsKey(subArg)) {
				List<String> subArgs = new ArrayList<String>(Arrays.asList(args));
				subArgs.remove(0);
				args = subArgs.toArray(new String[0]);
				commands.get(subArg).onCommand(sender, args);
				return false;
			}
		}
		return false;
	}

	@Override
	public String getPermission() {
		return "";
	}
}
