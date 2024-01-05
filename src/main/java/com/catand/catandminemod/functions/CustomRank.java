package com.catand.catandminemod.functions;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class CustomRank {
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void modifyArmorStandName(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
		if (Minecraft.getMinecraft().theWorld != null) {
			Entity entity = event.entity;
			if (entity.hasCustomName()) {
				String nameWithRank = replaceName(entity.getCustomNameTag());
				entity.setCustomNameTag(nameWithRank);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void modifyChatMessage(ClientChatReceivedEvent event) {
		IChatComponent originalComponent = event.message;
		String name = originalComponent.getUnformattedText();
		for (Map.Entry<String, String> entry : RankList.rankMap.entrySet()) {
			if (name.contains(entry.getKey()) && !name.contains(entry.getValue())) {
				ChatStyle clickCommandStyle = findClickCommandStyle(originalComponent);
				name = name.replace(entry.getKey(), entry.getValue());
				ChatComponentText newComponent = new ChatComponentText(name);
				newComponent.setChatStyle(clickCommandStyle);
				event.message = newComponent;
				return;
			}
		}
	}

	public String replaceName(String name) {
		for (Map.Entry<String, String> entry : RankList.rankMap.entrySet()) {
			if (name.contains(entry.getKey()) && !name.contains(entry.getValue())) {
				name = name.replace(entry.getKey(), entry.getValue());
			}
		}
		return name;
	}

	// 递归检查一个组件及其所有子组件的点击指令
	public ChatStyle findClickCommandStyle(IChatComponent component) {
		ChatStyle style = component.getChatStyle();
		if (style != null && style.getChatClickEvent() != null && style.getChatClickEvent().getAction() == ClickEvent.Action.RUN_COMMAND) {
			return style;
		}

		for (IChatComponent sibling : component.getSiblings()) {
			ChatStyle siblingStyle = findClickCommandStyle(sibling);
			if (siblingStyle != null) {
				return siblingStyle;
			}
		}

		return null;
	}
}
