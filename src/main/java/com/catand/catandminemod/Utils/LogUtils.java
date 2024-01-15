package com.catand.catandminemod.Utils;

import com.catand.catandminemod.functions.CustomRank;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

import static com.catand.catandminemod.CatandMineMod.mc;

public class LogUtils {

	public synchronized static void sendLog(ChatComponentText chat) {
		if (mc.thePlayer != null)
			mc.thePlayer.addChatMessage(chat);
	}

	public static void sendSuccess(String message) {
		message = message.replace("\\n", System.lineSeparator());
		String[] lines = message.split(System.lineSeparator());
		for (String line : lines) {
			sendLog(new ChatComponentText("§2§lCMM §8» §a" + line));
		}
	}

	public static void sendWarning(String message) {
		message = message.replace("\\n", System.lineSeparator());
		String[] lines = message.split(System.lineSeparator());
		for (String line : lines) {
			sendLog(new ChatComponentText("§6§lCMM §8» §e" + line));
		}
	}

	public static void sendError(String message) {
		message = message.replace("\\n", System.lineSeparator());
		String[] lines = message.split(System.lineSeparator());
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

	public static void sendChat(String message) {
		sendLog((ChatComponentText) CustomRank.convert(new ChatComponentText("§f§lCMMChat §8» §f" + message)));
	}

	public static void sendSuccessChat(String message) {
		message = message.replace("\\n", System.lineSeparator());
		String[] lines = message.split(System.lineSeparator());
		for (String line : lines) {
			sendLog(new ChatComponentText("§2§lCMMChat §8» §a" + line));
		}
	}

	public static void sendWarningChat(String message) {
		message = message.replace("\\n", System.lineSeparator());
		String[] lines = message.split(System.lineSeparator());
		for (String line : lines) {
			sendLog(new ChatComponentText("§6§lCMMChat §8» §e" + line));
		}
	}

	public static void sendErrorChat(String message) {
		message = message.replace("\\n", System.lineSeparator());
		String[] lines = message.split(System.lineSeparator());
		for (String line : lines) {
			sendLog(new ChatComponentText("§4§lCMMChat §8» §c" + line));
		}
	}
}
