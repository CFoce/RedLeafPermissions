package com.akaiha.redleaf.commands;

import net.md_5.bungee.api.CommandSender;

public abstract interface BasicCommand
{
	public abstract boolean onCommand(CommandSender commandSender, final String[] args);
	public abstract String getPermission();
}
