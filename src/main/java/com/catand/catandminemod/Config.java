package com.catand.catandminemod;

import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import com.catand.catandminemod.functions.RankList;

public class Config extends cc.polyfrost.oneconfig.config.Config {
    private transient static final String RANK = "Rank";
    private transient static final String SKYBLOCK = "Skyblock";
    private transient static final String MINIGAME = "Minigame";
    private transient static final String HUD = "HUD";
    @Button(
            name = "Refresh Rank List", category = RANK, subcategory = "Rank List", text = "Refresh Rank List"
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
