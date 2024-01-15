package com.catand.catandminemod.command;

import com.catand.catandminemod.functions.ChatSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CMMChatCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "mc";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/mc";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		StringBuilder sb = new StringBuilder();
		for (String arg : args)
			sb.append(arg).append(" ");
		ChatSender.sendChat(sb.toString());
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
}