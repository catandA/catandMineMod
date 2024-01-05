package com.catand.catandminemod.functions;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.EnumChatFormatting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RankList {
	public static JsonObject rankJson;
	public static HashMap<String, String> rankMap = new HashMap<>();
	public static final String RANKLIST_URL = "https://gitee.com/catandA/catand-mine-mod-custom-rank/raw/master/CustomRank.json";

	public static void getRankList() {
		getRankListJson();

		rankMap = new HashMap<>();
		for (Map.Entry<String, JsonElement> entry : rankJson.entrySet()) {
			JsonObject rankJsonJsonObject = rankJson.get(entry.getKey()).getAsJsonObject();
			String name = rankJsonJsonObject.get("name").getAsString();
			String rank = rankJsonJsonObject.get("rank").getAsString();
			rankMap.put(name, rank + " " + name + EnumChatFormatting.WHITE);
		}
	}

	private static void getRankListJson() {
		try {
			URL url = new URL(RANKLIST_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			int responsecode = conn.getResponseCode();
			if (responsecode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String inputLine;
				StringBuilder content = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();
				conn.disconnect();
				Gson gson = new Gson();
				rankJson = gson.fromJson(content.toString(), JsonObject.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
