package com.catand.catandminemod;

import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.DualOption;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import com.catand.catandminemod.functions.RankList;

public class Config extends cc.polyfrost.oneconfig.config.Config {
	private transient static final String RANK = "Rank";
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
	@DualOption(
			name = "DataAddress", category = RANK, subcategory = "Data fetch", left = "Gitee", right = "Github",description = "The address where data fetch from"
	)
	public boolean dataSource = false;
	@Button(
			name = "Refresh Rank List", category = RANK, subcategory = "Data fetch", text = "Refresh Rank List"
	)
	public void refreshRankList() {
		new Thread(RankList::getRankList).start();
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
