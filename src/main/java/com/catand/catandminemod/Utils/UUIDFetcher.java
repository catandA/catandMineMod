package com.catand.catandminemod.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UUIDFetcher {
	private static final int THREAD_COUNT = 10; // 限制线程数量
	private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
	private static UUIDFetcher uuidFetcher;

	private UUIDFetcher() {
	}

	public static synchronized UUIDFetcher getInstance() {
		if (uuidFetcher == null) {
			uuidFetcher = new UUIDFetcher();
		}
		return uuidFetcher;
	}

	public Future<String> fetchUUID(String uuid) {
		return executorService.submit(() -> getNameFromUUID(uuid));
	}

	private String getNameFromUUID(String uuid) throws Exception {
		String json = HttpUtils.get("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		if (jsonObject == null) return null;
		if (!jsonObject.has("name")) return null;
		return jsonObject.get("name").getAsString();
	}
}