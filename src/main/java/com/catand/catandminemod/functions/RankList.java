package com.catand.catandminemod.functions;

import com.catand.catandminemod.Object.RankUser;
import com.catand.catandminemod.Object.RankUserPet;
import com.catand.catandminemod.Utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RankList {
	public static JsonObject rankJson;
	public static HashMap<String, RankUser> rankMap = new HashMap<>();
	public static final String RANKLIST_URL = "https://gitee.com/catandA/catand-mine-mod-custom-rank/raw/master/CustomRank.json";

	public static void getRankList() {
		getRankListJson();

		rankMap = new HashMap<>();
		for (Map.Entry<String, JsonElement> entry : rankJson.entrySet()) {
			String name = entry.getKey();
			JsonObject rankJsonJsonObject = rankJson.get(name).getAsJsonObject();
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
				rankMap.put(name, new RankUser(rank, nameColor, bracketColor, nick, pet));
			} else
				rankMap.put(name, new RankUser(rank, nameColor, bracketColor, nick, null));
		}
	}

	private static void getRankListJson() {
		String json = HttpUtils.get(RANKLIST_URL);
		Gson gson = new Gson();
		rankJson = gson.fromJson(json, JsonObject.class);
	}
}
