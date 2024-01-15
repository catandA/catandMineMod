package com.catand.catandminemod;

import com.catand.catandminemod.Utils.LogUtils;
import com.catand.catandminemod.functions.CMMChat;
import com.catand.catandminemod.functions.ChatSender;
import com.google.gson.JsonObject;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import org.java_websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MessageType {
	//身份验证
	AUTH {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {
			CMMChat.isAuthed = true;
			String message = msgJson.get("message").getAsString();
			LogUtils.sendSuccessChat("身份验证成功");
			LogUtils.sendSuccessChat(message);
		}
	},
	//聊天消息
	CHAT {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {
			String message = msgJson.get("message").getAsString();
			new ClientChatReceivedEvent((byte) 0, new ChatComponentText("§f§lCMMChat §8» §f" + message));
			if (message == null) {
				ChatSender.sendError("消息内容为空", 0);
			} else {
				LogUtils.sendChat(message);
			}
		}
	},
	//展示物品
	SHOW {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {

		}
	},
	//广播
	BROADCAST {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {

		}
	},
	//命令
	COMMAND {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {

		}
	},
	//调试
	DEBUG {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {

		}
	},
	//在线列表
	PLAYER_LIST {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {

		}
	},
	//错误
	ERROR {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {
			String message = msgJson.get("message").getAsString();
			if (message == null) {
				ChatSender.sendError("错误信息为空", 0);
			} else {
				LogUtils.sendErrorChat(message);
			}

		}
	},
	//链接
	LINK {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {

		}
	};

	private static final Logger logger = LoggerFactory.getLogger(MessageType.class);

	public abstract void handleMessage(WebSocketClient client, JsonObject msgJson);
}