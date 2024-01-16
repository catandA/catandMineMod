package com.catand.catandminemod.functions;

import com.catand.catandminemod.CMMWebSocketClient;
import com.catand.catandminemod.CatandMineMod;
import com.catand.catandminemod.Utils.Clock;
import com.catand.catandminemod.Utils.HttpUtils;
import com.catand.catandminemod.Utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.java_websocket.client.WebSocketClient;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import static com.catand.catandminemod.CatandMineMod.mc;

public class CMMChat {
	private static JsonObject serverJson;
	private static final String SERVER_GITEE_URL = "https://gitee.com/catandA/catand-mine-mod-custom-rank_v2/raw/master/Server.json";
	private static final String SERVER_GITHUB_URL = "https://raw.githubusercontent.com/catandA/catandMineModCustomRank_v2/master/Server.json";
	private static PrintWriter out;
	private static BufferedReader in;
	public static boolean isConnecting = false;
	private static WebSocketClient client = null;
	private static Thread socketThread = null;
	private static int socketId = 0;
	private static String serverUrl = "";
	public static boolean isAuthed = false;
	private Clock clock = new Clock();

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.thePlayer == null) return;
		if (CatandMineMod.config.autoConnectToServer && !getClient().isOpen() && !isConnecting && clock.passed()) {
			LogUtils.sendChat("正在连接服务器...");
			isConnecting = true;
			getConnectedClient();
			clock.schedule((long) CatandMineMod.config.reconnectInterval * 1000);
		}
	}

	public static void updateServerAndReconnect() {
		updateServerURL();
		reconnect();
	}

	public static void reconnect() {
		disconnect();
		connect();
	}

	public static void connect() {
		getClient().connect();
		isConnecting = true;
	}

	public static void disconnect() {
		if (client != null) {
			client.close();
			client = null;
		}
		isConnecting = false;
		isAuthed = false;
	}

	public static void updateServerURL() {
		getServerJson();

		if (serverJson == null) return;
		String serverAddress = serverJson.get("ServerAddress").getAsString();
		String serverPort = serverJson.get("ServerPort").getAsString();
		String serverEndPoint = serverJson.get("ServerEndPoint").getAsString();
		serverUrl = "ws://" + serverAddress + ":" + serverPort + serverEndPoint;
	}

	private static void getServerJson() {
		String json = null;
		if (CatandMineMod.config.dataSource) {
			try {
				json = HttpUtils.get(SERVER_GITHUB_URL);
			} catch (Exception e) {
				LogUtils.sendError("从Github获取服务器地址失败 :(");
				e.printStackTrace();
			}
		} else {
			try {
				json = HttpUtils.get(SERVER_GITEE_URL);
			} catch (Exception e) {
				LogUtils.sendError("从Gitee获取服务器地址失败 :(");
				e.printStackTrace();
			}
		}
		if (json == null) return;
		Gson gson = new Gson();
		serverJson = gson.fromJson(json, JsonObject.class);
	}

	public static WebSocketClient getClient() {
		if (client == null || client.isClosed()) {
			try {
				if (serverUrl.isEmpty()) {
					updateServerURL();
				}
				client = new CMMWebSocketClient(new URI(serverUrl));
			} catch (URISyntaxException e) {
				LogUtils.sendError("服务器地址错误 :(");
				e.printStackTrace();
			}
		}
		return client;
	}

	public static WebSocketClient getConnectedClient() {
		if (!getClient().isOpen()) {
			getClient().connect();
		}
		return client;
	}
}