package com.catand.catandminemod.command;

import com.catand.catandminemod.functions.ChatSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CMMChatPlayerListCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "mcl";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/mcl";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		ChatSender.sendPlayerList();
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
}