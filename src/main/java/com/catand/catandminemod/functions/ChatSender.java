package com.catand.catandminemod.functions;

import com.catand.catandminemod.Utils.ChatMsgUtils;
import com.catand.catandminemod.Utils.LogUtils;
import org.java_websocket.client.WebSocketClient;

public class ChatSender {
	public static void sendAuth() {
		CMMChat.getConnectedClient().send(ChatMsgUtils.sendAuthJson());
		LogUtils.sendChat("正在发送认证信息...");
	}
	public static void sendChat(String message) {
		CMMChat.getConnectedClient().send(ChatMsgUtils.sendChatJson(message));
	}
}
