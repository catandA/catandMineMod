package com.catand.catandminemod.functions;

import com.catand.catandminemod.Utils.ChatMsgUtils;
import com.catand.catandminemod.Utils.LogUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChatSender {
	public static void sendAuth() {
		CMMChat.getConnectedClient().send(ChatMsgUtils.sendAuthJson());
		LogUtils.sendChat("正在发送认证信息...");
	}

	public static void sendChat(String message) {
		CMMChat.getConnectedClient().send(ChatMsgUtils.sendChatJson(message));
	}

	public static void sendPlayerList() {
		CMMChat.getConnectedClient().send(ChatMsgUtils.sendPlayerListJson());
	}

	public static void sendShow(String slot, ItemStack itemStack) {
		CMMChat.getConnectedClient().send(ChatMsgUtils.sendShowJson(slot, itemStack));
	}

	public static void sendError(String message, int errorType) {
		CMMChat.getConnectedClient().send(ChatMsgUtils.sendErrorJson(message, errorType));
	}
}
