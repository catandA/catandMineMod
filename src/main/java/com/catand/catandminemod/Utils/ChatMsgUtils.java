package com.catand.catandminemod.Utils;

import com.catand.catandminemod.CatandMineMod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.StringEscapeUtils;

import static com.catand.catandminemod.CatandMineMod.mc;

public class ChatMsgUtils {
	public static String sendErrorJson(String message, int errorType) {
		return "{\"type\":\"error\",\"message\":\"" + message + "\",\"errorType\":\"" + errorType + "\"}";
	}

	public static String sendChatJson(String message) {
		return "{\"type\":\"chat\",\"message\":\"" + message + "\"}";
	}

	public static String sendPlayerListJson() {
		return "{\"type\":\"player_list\"}";
	}

	public static String sendShowJson(String slot, ItemStack itemStack) {
		if (itemStack == null)
			return "{\"type\":\"show\",\"slot\":\"" + slot + "\",\"displayName\":\"\",\"nbt\":\"\",\"amount\":\"0\"}";
		NBTTagCompound nbt = new NBTTagCompound();
		itemStack.writeToNBT(nbt);
		String nbtString = nbt.toString();
		return "{\"type\":\"show\",\"slot\":\"" + slot + "\",\"displayName\":\"" + itemStack.getDisplayName() + "\",\"nbt\":\"" + StringEscapeUtils.escapeJson(nbtString) + "\",\"amount\":\"" + itemStack.stackSize + "\"}";
	}

	public static String sendAuthJson() {
		return "{\"type\":\"auth\",\"uuid\":\"" + mc.thePlayer.getUniqueID() + "\",\"name\":\"" + mc.thePlayer.getName() + "\",\"version\":\"" + CatandMineMod.VERSION + "\",\"clientType\":\"forge-" + mc.getVersion() + "\",\"invisible\":" + CatandMineMod.config.invisibleLogin + "}";
	}
}
