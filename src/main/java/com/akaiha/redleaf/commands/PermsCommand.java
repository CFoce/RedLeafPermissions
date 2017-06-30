package com.akaiha.redleaf.commands;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.commands.group.DemoteCommand;
import com.akaiha.redleaf.commands.group.GroupCommand;
import com.akaiha.redleaf.commands.group.PromoteCommand;
import com.akaiha.redleaf.enums.RedLeafCommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class PermsCommand extends Command
{
	private EnumMap<RedLeafCommands, BasicCommand> commands;
	RedLeaf plugin;
	public PermsCommand(RedLeaf plugin)
	{
		super("perms");
		this.plugin = plugin;
		this.commands = new EnumMap<RedLeafCommands, BasicCommand>(RedLeafCommands.class);
		loadCommands();
	}

	private void loadCommands()
	{
		commands.put(RedLeafCommands.GROUP, new GroupCommand(plugin));
		commands.put(RedLeafCommands.GROUP, new PromoteCommand(plugin));
		commands.put(RedLeafCommands.GROUP, new DemoteCommand(plugin));
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help")))
		{
			// TODO: EXECUTE REDLEAF HELP HERE
			return;
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
				return;
			}

			// TODO: SEND 'UNKNOWN COMMAND' MESSAGE
		}
	}
}