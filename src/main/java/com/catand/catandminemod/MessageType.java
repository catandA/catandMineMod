package com.catand.catandminemod;

import com.catand.catandminemod.Utils.LogUtils;
import com.catand.catandminemod.functions.CMMChat;
import com.catand.catandminemod.functions.ChatSender;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.java_websocket.client.WebSocketClient;

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
			String sender = msgJson.get("sender").getAsString();
			String message = msgJson.get("message").getAsString();
			if (message == null) {
				ChatSender.sendError("消息内容为空", 0);
			} else {
				LogUtils.sendChat("[norank] " + sender + ": " + message);
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
			JsonArray playersArray = msgJson.getAsJsonArray("players");
			StringBuilder onlinePlayers = new StringBuilder("在线玩家(" + msgJson.get("amount").getAsInt() + "): ");

			for (int i = 0; i < playersArray.size(); i++) {
				onlinePlayers.append("[norank] ");
				onlinePlayers.append(playersArray.get(i).getAsJsonObject().get("name").getAsString()).append(", ");
			}
			LogUtils.sendChat(onlinePlayers.toString());
		}
	},
	//玩家登录
	JOIN {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {
			String name = msgJson.get("name").getAsString();
			LogUtils.sendWarningChat("[norank] " + name + " 已上线");
		}
	},
	//玩家离开
	LEAVE {
		@Override
		public void handleMessage(WebSocketClient client, JsonObject msgJson) {
			String name = msgJson.get("name").getAsString();
			LogUtils.sendWarningChat("[norank] " + name + " 已下线");
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

	public abstract void handleMessage(WebSocketClient client, JsonObject msgJson);
}