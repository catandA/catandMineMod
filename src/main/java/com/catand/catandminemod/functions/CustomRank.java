package com.catand.catandminemod.functions;

import com.catand.catandminemod.CatandMineMod;
import com.catand.catandminemod.Object.RankUser;
import com.catand.catandminemod.Object.RankUserPet;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.catand.catandminemod.CatandMineMod.mc;

public class CustomRank {
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void modifyArmorStandName(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
		if (mc.theWorld != null) {
			Entity entity = event.entity;
			if (entity.hasCustomName()) {
				if (entity.getCustomNameTag().contains("'s")) {
					String nameWithRank = replacePetName(entity.getCustomNameTag());
					entity.setCustomNameTag(nameWithRank);
				} else {
					String nameWithRank = replaceName(entity.getCustomNameTag());
					entity.setCustomNameTag(nameWithRank);
				}
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

		for (String name : RankList.rankMap.keySet()) {
			if (name == null) continue;
			RankUser rankUser = RankList.rankMap.get(name);
			if (rankUser == null) continue;
			String nameColor = rankUser.getNameColor();
			String bracketColor = rankUser.getBracketColor();

			String reg = "(§7|§.\\[(MVP|VIP)] |§.\\[(MVP|VIP)(§.)*\\++(§.)*] |(§.)*\\[(§.)*\\d+(§.)*] )(§.)*" + name;
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

	public static String replacePetName(String message) {
		if (message == null || RankList.rankMap == null) return message;

		String unformatted = ChatLib.removeFormatting(message);
		for (String name : RankList.rankMap.keySet()) {
			if (name == null) continue;
			RankUser rankUser = RankList.rankMap.get(name);
			if (rankUser == null) continue;
			String userNameColor = rankUser.getNameColor();
			if (unformatted.matches("\\[Lv[0-9]+] " + name + "'s.*")) {
				String res = message.replace(name, userNameColor + name +
						ChatLib.getPrefix(ChatLib.removeColor(message.replaceAll(".*\\[.*] ", ""))));

				Matcher matcher = Pattern.compile("\\[(§.)*Lv").matcher(res);
				StringBuffer sb = new StringBuffer();
				while (matcher.find()) {
					matcher.appendReplacement(sb, matcher.group() + ".");
				}
				matcher.appendTail(sb);
				res = sb.toString();
				res = ChatLib.addColor(res);
				message = res;
				if (rankUser.getPet() != null) {
					for (RankUserPet pet : rankUser.getPet()) {
						String petName = pet.getName();
						String petDisplayName = pet.getDisplayName();
						String petNameColor = pet.getNameColor();
						String petBracketColor = pet.getBracketColor();
						String petReg = "('s )(§.)*" + petName;
						res = res.replaceAll(petReg, "ᄅ");
						String petDst = "的 " + petBracketColor + "[" + petNameColor + petDisplayName + petBracketColor + "]&r";
						res = res.replace("ᄅ", petDst);
						message = res;
					}
				}
			}
		}
		return ChatLib.addColor(message);
	}

	private static List<IChatComponent> compactSiblings(List<IChatComponent> siblings) {
		StringBuilder str = new StringBuilder();
		List<IChatComponent> res = new ArrayList<>();
		for (IChatComponent component : siblings) {
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
		if (!str.toString().isEmpty()) res.add(new ChatComponentText(str.toString()));
		return res;
	}
}
