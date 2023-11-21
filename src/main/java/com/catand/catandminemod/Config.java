package com.catand.catandminemod;

import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import com.catand.catandminemod.HUD.GemstoneTimerHUD;
import com.catand.catandminemod.Utils.LogUtils;
import com.catand.catandminemod.functions.GemstoneTimer;

public class Config extends cc.polyfrost.oneconfig.config.Config {
    private transient static final String SKYBLOCK = "Skyblock";
    private transient static final String MINIGAME = "Minigame";
    private transient static final String HUD = "HUD";
    @Switch(
            name = "Gemstone Timer", category = SKYBLOCK, subcategory = "Mining"
    )
    public boolean gemstoneTimer = true;
    @Button(
            name = "Reset Gemstone Timer", category = SKYBLOCK, subcategory = "Mining",
            description = "Reset The Gemstone Timer",
            text = "Reset Gemstone Timer"
    )
    Runnable _resetGemstoneTimer = () -> {
        GemstoneTimer.reset();
        LogUtils.sendSuccess("宝石计时器已重置");
    };
    @Switch(
            name = "Pixel Party Solver", category = MINIGAME, subcategory = "Pixel Party"
    )
    public boolean pixelPartySolver = true;
    @Switch(
            name = "Draw a line point to block", category = MINIGAME, subcategory = "Pixel Party"
    )
    public boolean pixelPartySolverLine = true;
    @HUD(
            name = "Gemstone Timer HUD", category = HUD, subcategory = " "
    )
    public GemstoneTimerHUD timerHUD = new GemstoneTimerHUD();

    public Config() {
        super(new Mod("catand Mine Mod", ModType.HYPIXEL, "/assets/bps.png"), "/catandminemod/config.json");
        initialize();
        this.addDependency("pixelPartySolverLine", "pixelPartySolver");
        save();
    }
}
