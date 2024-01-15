package com.catand.catandminemod;

import com.catand.catandminemod.Utils.ChatMsgUtils;
import com.catand.catandminemod.Utils.LogUtils;
import com.catand.catandminemod.functions.CMMChat;
import com.catand.catandminemod.functions.ChatSender;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class CMMWebSocketClient extends WebSocketClient {
	Gson gson = new Gson();

	public CMMWebSocketClient(URI serverUri) {
		super(serverUri);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		LogUtils.sendChat("连接成功");
		CMMChat.isConnecting = false;
		new Thread(() -> {
			try {
				Thread.sleep(1000); // 等待1秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ChatSender.sendAuth();
		}).start();
	}

	@Override
	public void onMessage(String message) {
		JsonObject msgJson = gson.fromJson(message, JsonObject.class);
		String type = msgJson.get("type").getAsString();
		if (type == null) {
			this.send(ChatMsgUtils.sendErrorJson("消息类型错误"));
		}
		try {
			MessageType messageType = MessageType.valueOf(type.toUpperCase());
			messageType.handleMessage(this, msgJson);
		} catch (IllegalArgumentException e) {
			this.send(ChatMsgUtils.sendErrorJson("无效的消息类型"));
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		LogUtils.sendErrorChat("连接被" + (remote ? "远程端" : "用户端") + "关闭，代码：" + code + "，原因：" + reason);
		CMMChat.disconnect();
	}

	@Override
	public void onError(Exception ex) {
		CMMChat.isConnecting = false;
		LogUtils.sendErrorChat("发生错误：" + ex.getMessage());
		ex.printStackTrace();
	}
}