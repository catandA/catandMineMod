package com.catand.catandminemod.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class LogUtils {
	private static final Minecraft mc = Minecraft.getMinecraft();

	public synchronized static void sendLog(ChatComponentText chat) {
		if (mc.thePlayer != null)
			mc.thePlayer.addChatMessage(chat);
	}

	public static void sendSuccess(String message) {
		String[] lines = message.split("\\n");
		for (String line : lines) {
			sendLog(new ChatComponentText("§2§lCMM §8» §a" + line));
		}
	}

	public static void sendWarning(String message) {
		String[] lines = message.split("\\n");
		for (String line : lines) {
			sendLog(new ChatComponentText("§6§lCMM §8» §e" + line));
		}
	}

	public static void sendError(String message) {
		String[] lines = message.split("\\n");
		for (String line : lines) {
			sendLog(new ChatComponentText("§4§lCMM §8» §c" + line));
		}
	}

	public static void sendFailsafeMessage(String message) {
		sendLog(new ChatComponentText("§5§lCMM §8» §d§l" + message));
	}

	public static void sendLink(String message, String url, String description) {
		ChatComponentText chat = new ChatComponentText("§f§lCMM §8» §f" + message);
		ChatStyle style = new ChatStyle();
		style.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
		style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(description)));
		chat.setChatStyle(style);
		sendLog(chat);
	}
}
