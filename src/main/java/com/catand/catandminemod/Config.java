package com.catand.catandminemod;

import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.DualOption;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import com.catand.catandminemod.functions.CMMChat;
import com.catand.catandminemod.functions.RankList;
import com.catand.catandminemod.functions.UpdateReminder;

public class Config extends cc.polyfrost.oneconfig.config.Config {
	private transient static final String RANK = "Rank";
	private transient static final String CHAT = "Chat";
	private transient static final String GENERAL = "General";
	private transient static final String SKYBLOCK = "Skyblock";
	private transient static final String MINIGAME = "Minigame";
	private transient static final String HUD = "HUD";

	@Switch(
			name = "Display custom rank on pet", category = RANK, subcategory = "Display"
	)
	public boolean displayPet = true;
	@Switch(
			name = "Display custom rank on player", category = RANK, subcategory = "Display"
	)
	public boolean displayPlayer = true;
	@Switch(
			name = "Display custom rank on chat message", category = RANK, subcategory = "Display"
	)
	public boolean displayChatMessage = true;
	@DualOption(
			name = "Display Type", category = RANK, subcategory = "Display", left = "Rank", right = "Nick"
	)
	public boolean rankListDisplayType = false;

	@Button(
			name = "Refresh Rank List", category = RANK, subcategory = "Data fetch", text = "Refresh Rank List"
	)
	public void refreshRankList() {
		new Thread(RankList::getRankList).start();
	}

	@Switch(
			name = "Auto connect to chat server", category = CHAT, subcategory = "Chat"
	)
	public boolean autoConnectToServer = true;
	@Switch(
			name = "Invisible login in chat server", category = CHAT, subcategory = "Chat"
	)
	public boolean invisibleLogin = false;

	@Slider(
			name = "Chat server reconnect interval", category = CHAT, subcategory = "Chat", min = 2, max = 60, description = "The interval of reconnect to chat server"
	)
	public int reconnectInterval = 10;

	@Button(
			name = "Mandatory reconnect to Server", category = CHAT, subcategory = "Chat", text = "Reconnect"
	)
	public void reconnectToServer() {
		new Thread(CMMChat::updateServerAndReconnect).start();
	}

	@DualOption(
			name = "DataAddress", category = GENERAL, subcategory = "Data fetch", left = "Gitee", right = "Github", description = "The address where data fetch from"
	)
	public boolean dataSource = false;
	@Switch(
			name = "Auto check update", category = GENERAL, subcategory = "Update"
	)
	public boolean checkUpdate = true;

	@Button(
			name = "Check update", category = GENERAL, subcategory = "Data fetch", text = "Check update"
	)
	public void checkUpdat() {
		new Thread(UpdateReminder::checkUpdate).start();
	}

	@Switch(
			name = "Pixel Party Solver", category = MINIGAME, subcategory = "Pixel Party"
	)
	public boolean pixelPartySolver = true;
	@Switch(
			name = "Draw a line point to block", category = MINIGAME, subcategory = "Pixel Party"
	)
	public boolean pixelPartySolverLine = true;

	public Config() {
		super(new Mod("catand Mine Mod", ModType.HYPIXEL, "/assets/bps.png"), "/catandminemod/config.json");
		initialize();
		this.addDependency("pixelPartySolverLine", "pixelPartySolver");
		save();
	}
}
