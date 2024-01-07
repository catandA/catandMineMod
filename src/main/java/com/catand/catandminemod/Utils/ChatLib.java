package com.catand.catandminemod.Utils;

import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.catand.catandminemod.CatandMineMod.mc;

public class ChatLib {

	public static String removeFormatting(String text) {
		if (text == null) return null;
		return text.replaceAll("[\\u00a7&][0-9a-zA-Z]", "");
	}

	public static void addComponent(IChatComponent component) {
		addComponent(component, true);
	}

	public static void addComponent(IChatComponent component, boolean post) {
		if (mc.thePlayer == null) return;
		try {
			ClientChatReceivedEvent event = new ClientChatReceivedEvent((byte) 0, component);
			if (post && MinecraftForge.EVENT_BUS.post(event)) return;
			mc.thePlayer.addChatMessage(event.message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String addColor(String text) {
		if (text == null) return "";
		Pattern pattern = Pattern.compile("((?<!\\\\))&(?![^0-9a-fklmnor]|$)");
		Matcher matcher = pattern.matcher(text);
		return matcher.replaceAll("§");
	}

	public static String removeColor(String text) {
		if (text == null) return "";
		Pattern pattern = Pattern.compile("((?<!\\\\))§(?![^0-9a-fklmnor]|$)");
		Matcher matcher = pattern.matcher(text);
		return matcher.replaceAll("&");
	}

	// remove color first!
	public static String getPrefix(String text) {
		if (text == null) return "";
		Pattern pattern = Pattern.compile("^(&[0-9a-fklmnor])*");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) return matcher.group(0);
		return "&r";
	}
}
