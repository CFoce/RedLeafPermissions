package com.akaiha.perms.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import com.akaiha.perms.Perms;
import com.akaiha.perms.commands.edit.AddChildCommand;
import com.akaiha.perms.commands.edit.AddPermCommand;
import com.akaiha.perms.commands.edit.AddPlayerCommand;
import com.akaiha.perms.commands.edit.AddServerCommand;
import com.akaiha.perms.commands.edit.CreateGroupCommand;
import com.akaiha.perms.commands.edit.DeleteGroupCommand;
import com.akaiha.perms.commands.edit.RemoveChildCommand;
import com.akaiha.perms.commands.edit.RemovePermCommand;
import com.akaiha.perms.commands.edit.RemovePlayerCommand;
import com.akaiha.perms.commands.edit.RemoveServerCommand;
import com.akaiha.perms.enums.EditCommands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class EditCommand implements BasicCommand {
	
	private Perms plugin;
	private EnumMap<EditCommands, BasicCommand> commands;
	
	public EditCommand(Perms plugin) {
		this.plugin = plugin;
		this.commands = new EnumMap<EditCommands, BasicCommand>(EditCommands.class);
		loadCommands();
	}

	public void loadCommands() {
		commands.put(EditCommands.CREATE, new CreateGroupCommand(plugin));
		commands.put(EditCommands.DELETE, new DeleteGroupCommand(plugin));
		commands.put(EditCommands.ADDPLAYER, new AddPlayerCommand(plugin));
		commands.put(EditCommands.ADDSERVER, new AddServerCommand(plugin));
		commands.put(EditCommands.ADDPERM, new AddPermCommand(plugin));
		commands.put(EditCommands.ADDCHILD, new AddChildCommand(plugin));
		commands.put(EditCommands.REMOVEPLAYER, new RemovePlayerCommand(plugin));
		commands.put(EditCommands.REMOVESERVER, new RemoveServerCommand(plugin));
		commands.put(EditCommands.REMOVEPERM, new RemovePermCommand(plugin));
		commands.put(EditCommands.REMOVECHILD, new RemoveChildCommand(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if ((args.length > 0 && args[0].equalsIgnoreCase("help"))) {
			if (sender.hasPermission(getPermission())) 
				sender.sendMessage(new TextComponent("help command"));
			return true;
		}

		if (args.length > 0) {
			EditCommands subArg = EditCommands.valueOf(args[0].toUpperCase());

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
		return "perms.edit.help";
	}
}
