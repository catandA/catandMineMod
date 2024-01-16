package com.catand.catandminemod.command;

import com.catand.catandminemod.Utils.LogUtils;
import com.catand.catandminemod.functions.ChatSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import static com.catand.catandminemod.CatandMineMod.mc;

public class CMMChatShowCommand extends CommandBase {
	@Override
	public String getCommandName() {
		return "mcshow";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/mcshow <helmet|chestplate|leggings|boots>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (mc.thePlayer == null) return;
		InventoryPlayer inventoryPlayer = mc.thePlayer.inventory;
		if (inventoryPlayer == null) return;
		if (args.length == 0) {
			ChatSender.sendShow("hand",inventoryPlayer.getCurrentItem());
		}
		if (args.length == 1) {
			switch (args[0]) {
				case "helmet":
				case "头盔":
					ChatSender.sendShow("helmet",inventoryPlayer.armorItemInSlot(3));
					break;
				case "chestplate":
				case "胸甲":
					ChatSender.sendShow("chestplate",inventoryPlayer.armorItemInSlot(2));
					break;
				case "leggings":
				case "裤子":
					ChatSender.sendShow("leggings",inventoryPlayer.armorItemInSlot(1));
					break;
				case "boots":
				case "鞋子":
					ChatSender.sendShow("boots",inventoryPlayer.armorItemInSlot(0));
					break;
				default:
					LogUtils.sendErrorChat("无效的参数");
					break;
			}
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
}