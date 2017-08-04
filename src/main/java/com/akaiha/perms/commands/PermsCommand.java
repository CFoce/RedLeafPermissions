package com.akaiha.perms.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import com.akaiha.perms.Perms;
import com.akaiha.perms.enums.PermsCommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class PermsCommand extends Command {
	
	private EnumMap<PermsCommands, BasicCommand> commands;
	private Perms plugin;
	
	public PermsCommand(Perms plugin) {
		super("perm");
		this.plugin = plugin;
		this.commands = new EnumMap<PermsCommands, BasicCommand>(PermsCommands.class);
		loadCommands();
	}

	private void loadCommands() {
		commands.put(PermsCommands.EDIT, new EditCommand(plugin));
		commands.put(PermsCommands.RELOAD, new ReloadCommand(plugin));
		commands.put(PermsCommands.PROMOTE, new PromoteCommand(plugin));
		commands.put(PermsCommands.DEMOTE, new DemoteCommand(plugin));
		commands.put(PermsCommands.LIST, new ListCommand(plugin));
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
			// TODO: EXECUTE REDLEAF HELP HERE
			return;
		}

		if (args.length > 0) {
			PermsCommands subArg = PermsCommands.valueOf(args[0].toUpperCase());
			if (commands.containsKey(subArg)) {
				List<String> subArgs = new ArrayList<String>(Arrays.asList(args));
				subArgs.remove(0);
				args = subArgs.toArray(new String[0]);
				commands.get(subArg).onCommand(sender, args);
				return;
			}
			// TODO: SEND 'UNKNOWN COMMAND' MESSAGE
		}
	}
	
	public String getPermission() {
		return "perms.help";
	}
}
