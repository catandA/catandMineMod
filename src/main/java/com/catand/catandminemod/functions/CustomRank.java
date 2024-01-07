package com.catand.catandminemod.functions;

import com.catand.catandminemod.Object.RankUser;
import com.catand.catandminemod.Utils.ChatLib;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.catand.catandminemod.CatandMineMod.mc;

public class CustomRank {

	private static final HashMap<String, String> cachedColorName = new HashMap<>();
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void modifyArmorStandName(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
		if (mc.theWorld != null) {
			Entity entity = event.entity;
			if (entity.hasCustomName()) {
				String nameWithRank = replaceName(entity.getCustomNameTag());
				entity.setCustomNameTag(nameWithRank);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void modifyChatMessage(ClientChatReceivedEvent event) {
		if (event.type == 2) return;
		event.message = convert(event.message.createCopy());
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
		String originMessage = message;
		if (cachedColorName.containsKey(message)) return cachedColorName.get(message);

		for (String name : RankList.rankMap.keySet()) {
			RankUser rankUser = RankList.rankMap.get(name);
			String color = rankUser.getNameColor();

			String reg = "(§7|§.\\[(MVP|VIP)] |§.\\[(MVP|VIP)(§.)*\\++(§.)*] |(§.)*\\[(§.)*\\d+(§.)*] )(§.)*" + name;
			message = message.replaceAll(reg, "ᄅ");


			//message = message.replace(name, color + name + "&r");

			String dst = rankUser.getNameColor() + name + "&r";
			if (!rankUser.getRank().isEmpty()) {
				dst = rankUser.getBracketColor() + "[" + rankUser.getRank() + rankUser.getBracketColor() + "]&r" + " " + dst;
			}
			message = message.replace("ᄅ", dst);
		}
		String res = ChatLib.addColor(message);
		addToCache(originMessage, res);
		return res;
	}
	private static List<IChatComponent> compactSiblings(List<IChatComponent> siblings) {
		StringBuilder str = new StringBuilder();
		List<IChatComponent> res = new ArrayList<>();
		for (int i = 0; i < siblings.size(); i++) {
			IChatComponent component = siblings.get(i);
			ClickEvent clickEvent = component.getChatStyle().getChatClickEvent();
			HoverEvent hoverEvent = component.getChatStyle().getChatHoverEvent();
			if ((clickEvent == null || clickEvent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) && hoverEvent == null) {
				str.append(component.getFormattedText());
			} else {
				res.add(new ChatComponentText(str.toString()));
				res.add(component);
				str = new StringBuilder();
			}
		}
		if (!str.toString().equals("")) res.add(new ChatComponentText(str.toString()));
		return res;
	}

	private static void addToCache(String src, String dst) {
		if (cachedColorName.size() > 10000) {
			System.err.println("Color name cache too big! Clearing cache...");
			cachedColorName.clear();
		}
		cachedColorName.put(src, dst);
	}

	public static int getCacheSize() {
		return cachedColorName.size();
	}
}
