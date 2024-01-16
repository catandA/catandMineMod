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
			String sender = msgJson.get("sender").getAsString();
			String slot = msgJson.get("slot").getAsString();
			String displayName = msgJson.get("displayName").getAsString();
			String nbt = msgJson.get("nbt").getAsString();
			int amount = msgJson.get("amount").getAsInt();
			if (displayName == null || displayName.isEmpty() || nbt == null || nbt.isEmpty()) {
				String name;
				String lore;
				switch (slot) {
					case "hand":
						name = "的沙包大的拳头";
						lore = "damage+250";
						break;
					case "helmet":
						name = "的空空如也的大脑";
						lore = "intelligence-250";
						break;
					case "chestplate":
						name = "的飞机场";
						lore = "defence+250";
						break;
					case "leggings":
						name = "的大腿";
						lore = "speed-250";
						break;
					case "boots":
						name = "的臭脚";
						lore = "smell-250";
						break;
					default:
						LogUtils.sendErrorChat(sender + "展示了未知物品");
						return;
				}
				String nbtString = "{id:\"minecraft:stone\",Count:1b,tag:{display:{Name:\"" + sender + name + "\",Lore:[\"" + lore + "\"]}},Damage:0s}";
				LogUtils.sendShowChat("[norank] " + sender + "展示了", sender + name, nbtString);
			} else {
				switch (slot) {
					case "hand":
						if (amount == 1)
							LogUtils.sendShowChat("[norank] " + sender + "手拿", displayName, nbt);
						else {
							LogUtils.sendShowChat("[norank] " + sender + "手拿" + amount + "个", displayName, nbt);
						}
						break;
					case "helmet":
						LogUtils.sendShowChat("[norank] " + sender + "头戴", displayName, nbt);
						break;
					case "chestplate":
						LogUtils.sendShowChat("[norank] " + sender + "身披", displayName, nbt);
						break;
					case "leggings":
						LogUtils.sendShowChat("[norank] " + sender + "鸟挂", displayName, nbt);
						break;
					case "boots":
						LogUtils.sendShowChat("[norank] " + sender + "脚踩", displayName, nbt);
						break;
					default:
						LogUtils.sendErrorChat(sender + "展示了未知物品");
						break;
				}
			}
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