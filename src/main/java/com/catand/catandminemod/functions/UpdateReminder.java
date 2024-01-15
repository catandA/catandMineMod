package com.catand.catandminemod.functions;

import com.catand.catandminemod.CatandMineMod;
import com.catand.catandminemod.Utils.HttpUtils;
import com.catand.catandminemod.Utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.catand.catandminemod.CatandMineMod.mc;

public class UpdateReminder {
	private static JsonObject versionJson;
	private static final String VERSION_GITEE_URL = "https://gitee.com/catandA/catand-mine-mod-custom-rank_v2/raw/master/Version.json";
	private static final String VERSION_GITHUB_URL = "https://raw.githubusercontent.com/catandA/catandMineModCustomRank_v2/master/Version.json";

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.thePlayer == null) return;
		MinecraftForge.EVENT_BUS.unregister(this);
		if (CatandMineMod.config.checkUpdate) {
			new Thread(UpdateReminder::checkUpdate).start();
		}
	}

	public static void checkUpdate() {
		getVersionJson();

		if (versionJson == null) return;
		String version = versionJson.get("version").getAsString();
		boolean haveUrl;
		if (versionJson.has("updateUrl")) {
			haveUrl = !versionJson.get("updateUrl").getAsString().isEmpty();
		} else {
			haveUrl = false;
		}
		String updateUrl = "";
		if (haveUrl) {
			updateUrl = versionJson.get("updateUrl").getAsString();
		}
		String changeLog = versionJson.get("changeLog").getAsString();
		int[] versionNumbers = splitVersion(version);
		int[] modVersionNumbers = splitVersion(CatandMineMod.MODVERSION);
		if (versionNumbers[0] > modVersionNumbers[0]) {
			LogUtils.sendWarning("catandMineMod有新版本: " + version + ", 当前版本: " + CatandMineMod.MODVERSION);
			LogUtils.sendError("看起来你落后一个大版本了 :/");
			LogUtils.sendError("旧版的rank列表不再更新敖");
			LogUtils.sendWarning("更新日志: " + System.lineSeparator() + changeLog);
			if (haveUrl) {
				LogUtils.sendLink("下载地址: " + updateUrl, updateUrl, "点击下载新版本");
			}
		} else if (versionNumbers[0] == modVersionNumbers[0]) {
			if (versionNumbers[1] > modVersionNumbers[1]) {
				LogUtils.sendWarning("catandMineMod有新版本: " + version);
				LogUtils.sendWarning("更新日志: " + System.lineSeparator() + changeLog);
				if (haveUrl) {
					LogUtils.sendLink("下载地址: " + updateUrl, updateUrl, "点击下载新版本");
				}
			} else if (versionNumbers[1] == modVersionNumbers[1]) {
				if (versionNumbers[2] > modVersionNumbers[2]) {
					LogUtils.sendWarning("catandMineMod有新版本: " + version);
					LogUtils.sendWarning("更新日志: " + System.lineSeparator() + changeLog);
					if (haveUrl) {
						LogUtils.sendLink("下载地址: " + updateUrl, updateUrl, "点击下载新版本");
					}
				} else if (versionNumbers[2] == modVersionNumbers[2]) {
					LogUtils.sendSuccess("你的catandMineMod是最新版: " + version + ", 恭喜! :)");
				}
			}
		}
	}

	private static void getVersionJson() {
		String json = null;
		if (CatandMineMod.config.dataSource) {
			try {
				json = HttpUtils.get(VERSION_GITHUB_URL);
			} catch (Exception e) {
				LogUtils.sendError("从Github检查版本更新失败 :(");
				e.printStackTrace();
			}
		} else {
			try {
				json = HttpUtils.get(VERSION_GITEE_URL);
			} catch (Exception e) {
				LogUtils.sendError("从Gitee检查版本更新失败 :(");
				e.printStackTrace();
			}
		}
		if (json == null) return;
		Gson gson = new Gson();
		versionJson = gson.fromJson(json, JsonObject.class);
	}

	public static int[] splitVersion(String version) {
		String[] parts = version.split("\\.");
		int[] numbers = new int[parts.length];
		for (int i = 0; i < parts.length; i++) {
			numbers[i] = Integer.parseInt(parts[i]);
		}
		return numbers;
	}
}
