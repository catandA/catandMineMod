package com.catand.catandminemod.Utils;

import com.catand.catandminemod.CatandMineMod;
import com.catand.catandminemod.Object.RankUser;
import com.catand.catandminemod.functions.RankList;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.List;

import static com.catand.catandminemod.CatandMineMod.mc;
import static com.catand.catandminemod.functions.CustomRank.compactSiblings;

public class LogUtils {

	public synchronized static void sendLog(ChatComponentText chat) {
		if (mc.thePlayer != null)
			mc.thePlayer.addChatMessage(chat);
	}

	public synchronized static void sendConvertedLog(ChatComponentText chat) {
		if (mc.thePlayer != null) {
			chat = (ChatComponentText) convert(chat);
			mc.thePlayer.addChatMessage(chat);
		}
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
		message = message.replace("\\n", System.lineSeparator());
		String[] lines = message.split(System.lineSeparator());
		for (String line : lines) {
			sendConvertedLog(new ChatComponentText("§f§lCMMChat §8» §f" + line));
		}
	}

	public static void sendSuccessChat(String message) {
		message = message.replace("\\n", System.lineSeparator());
		String[] lines = message.split(System.lineSeparator());
		for (String line : lines) {
			sendConvertedLog(new ChatComponentText("§2§lCMMChat §8» §a" + line));
		}
	}

	public static void sendWarningChat(String message) {
		message = message.replace("\\n", System.lineSeparator());
		String[] lines = message.split(System.lineSeparator());
		for (String line : lines) {
			sendConvertedLog(new ChatComponentText("§6§lCMMChat §8» §e" + line));
		}
	}

	public static void sendErrorChat(String message) {
		message = message.replace("\\n", System.lineSeparator());
		String[] lines = message.split(System.lineSeparator());
		for (String line : lines) {
			sendConvertedLog(new ChatComponentText("§4§lCMMChat §8» §c" + line));
		}
	}

	public static void sendShowChat(String message, String displayName, String nbt) {
		ChatComponentText chat = new ChatComponentText("§f§lCMMChat §8» §f" + message);

		IChatComponent itemComponent = new ChatComponentText("[" + displayName + "]");
		ChatStyle itemStyle = new ChatStyle();
		itemStyle.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(nbt)));
		itemComponent.setChatStyle(itemStyle);

		chat.appendSibling(itemComponent);
		sendConvertedLog(chat);
	}

	public static IChatComponent convert(IChatComponent message) {
		List<IChatComponent> siblings = message.getSiblings();
		ChatStyle style = message.getChatStyle();
		if (siblings.isEmpty()) {
			if (!(message instanceof ChatComponentTranslation)) {
				IChatComponent component = new ChatComponentText(replaceName(message.getFormattedText()));
				component.setChatStyle(style);
				return component;
			} else {
				System.out.println(message.getFormattedText());
				return message;
			}
		} else {
			int index = message.getFormattedText().indexOf(siblings.get(0).getFormattedText());
			IChatComponent component = new ChatComponentText(message.getFormattedText().substring(0, index));
			component.setChatStyle(style);
			siblings.add(0, component);

			siblings = compactSiblings(siblings);

			IChatComponent res = null;
			for (IChatComponent component1 : siblings) {
				if (res == null) res = convert(component1);
				else res.appendSibling(convert(component1));
			}
			return res;
		}
	}

	public static String replaceName(String message) {
		if (message == null) return null;
		if (RankList.rankMap == null) return message;

		for (String uuid : RankList.rankMap.keySet()) {
			if (uuid == null) continue;
			RankUser rankUser = RankList.rankMap.get(uuid);
			if (rankUser == null) continue;
			String name = rankUser.getName();
			String nameColor = rankUser.getNameColor();
			String bracketColor = rankUser.getBracketColor();

			String reg = "(\\[norank] )(§.)*" + name;
			message = message.replaceAll(reg, "ᄅ");

			String dst = nameColor + name + "&r";
			if (CatandMineMod.config.rankListDisplayType) {
				if (!rankUser.getNick().isEmpty()) {
					dst = bracketColor + "[" + rankUser.getNick() + bracketColor + "]&r" + " " + dst;
				}
			} else {
				if (!rankUser.getRank().isEmpty()) {
					dst = bracketColor + "[" + rankUser.getRank() + bracketColor + "]&r" + " " + dst;
				}
			}
			message = message.replace("ᄅ", dst);
		}
		return ChatLib.addColor(message);
	}
}
