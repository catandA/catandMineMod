package com.catand.catandminemod.Utils;

import com.catand.catandminemod.CatandMineMod;

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

	public static String sendAuthJson() {
		return "{\"type\":\"auth\",\"uuid\":\"" + mc.thePlayer.getUniqueID() + "\",\"name\":\"" + mc.thePlayer.getName() + "\",\"version\":\"" + CatandMineMod.VERSION + "\",\"clientType\":\"forge-" + mc.getVersion() + "\",\"invisible\":" + CatandMineMod.config.invisibleLogin + "}";
	}
}
