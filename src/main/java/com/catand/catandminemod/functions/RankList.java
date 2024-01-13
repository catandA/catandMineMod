package com.catand.catandminemod.functions;

import com.catand.catandminemod.CatandMineMod;
import com.catand.catandminemod.Object.RankUser;
import com.catand.catandminemod.Object.RankUserPet;
import com.catand.catandminemod.Utils.HttpUtils;
import com.catand.catandminemod.Utils.LogUtils;
import com.catand.catandminemod.Utils.UUIDFetcher;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class RankList {
	public static JsonObject rankJson;
	public static ConcurrentHashMap<String, RankUser> rankMap = new ConcurrentHashMap<>();
	public static final String RANKLIST_GITEE_URL = "https://gitee.com/catandA/catand-mine-mod-custom-rank_v2/raw/master/CustomRank.json";
	public static final String RANKLIST_GITHUB_URL = "https://raw.githubusercontent.com/catandA/catandMineModCustomRank_v2/master/CustomRank.json";
	static final int MAX_RETRIES = 3;

	public static void getRankList() {
		getRankListJson();

		rankMap = new ConcurrentHashMap<>();
		for (Map.Entry<String, JsonElement> entry : rankJson.entrySet()) {
			String uuid = entry.getKey();
			JsonObject rankJsonJsonObject = rankJson.get(uuid).getAsJsonObject();
			CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
				for (int i = 0; i < MAX_RETRIES; i++) {
					try {
						return UUIDFetcher.getInstance().fetchUUID(uuid).get();
					} catch (Exception e) {
						LogUtils.sendError("uuid: " + uuid + "获取失败，重试次数" + i);
						e.printStackTrace();
					}
				}
				return null;
			});
			future.thenAccept(name -> {
				if (name != null) {
					String rank = rankJsonJsonObject.get("rank").getAsString();
					String nameColor = rankJsonJsonObject.get("nameColor").getAsString();
					String bracketColor = rankJsonJsonObject.get("bracketColor").getAsString();
					String nick = rankJsonJsonObject.get("nick").getAsString();
					if (rankJsonJsonObject.has("pet")) {
						JsonObject petJson = rankJsonJsonObject.get("pet").getAsJsonObject();
						ArrayList<RankUserPet> pet = new ArrayList<>();
						for (Map.Entry<String, JsonElement> petEntry : petJson.entrySet()) {
							String petName = petEntry.getKey();
							JsonObject petJsonJsonObject = petJson.get(petName).getAsJsonObject();
							String petDisplayName = petJsonJsonObject.get("displayName").getAsString();
							String petNameColor = petJsonJsonObject.get("nameColor").getAsString();
							String petBracketColor = petJsonJsonObject.get("bracketColor").getAsString();
							pet.add(new RankUserPet(petName, petNameColor, petBracketColor, petDisplayName));
						}
						rankMap.put(uuid, new RankUser(name, rank, nameColor, bracketColor, nick, pet));
					} else
						rankMap.put(uuid, new RankUser(name, rank, nameColor, bracketColor, nick, null));
				} else {
					LogUtils.sendError("uuid: " + uuid + "获取失败");
				}
			});
		}
	}

	private static void getRankListJson() {
		String json = null;
		if (CatandMineMod.config.dataSource) {
			for (int i = 0; i < MAX_RETRIES; i++) {
				try {
					json = HttpUtils.get(RANKLIST_GITHUB_URL);
				} catch (Exception e) {
					LogUtils.sendError("从Github获取CustomRank.json失败，重试次数: " + (i + 1));
					e.printStackTrace();
				}
			}
		} else {
			for (int i = 0; i < MAX_RETRIES; i++) {
				try {
					json = HttpUtils.get(RANKLIST_GITEE_URL);
				} catch (Exception e) {
					LogUtils.sendError("从Gitee获取CustomRank.json失败，重试次数: " + (i + 1));
					e.printStackTrace();
				}
			}
		}
		if (json == null) {
			LogUtils.sendError("CustomRank.json获取了" + MAX_RETRIES + "次都失败, 网不太行啊╮(╯▽╰)╭, 试试换个数据源?");
		}
		Gson gson = new Gson();
		rankJson = gson.fromJson(json, JsonObject.class);
		LogUtils.sendSuccess("CustomRank.json获取成功");
	}
}
