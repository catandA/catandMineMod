package com.catand.catandminemod;

import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import com.catand.catandminemod.HUD.ProfitCalculatorHUD;

public class Config extends cc.polyfrost.oneconfig.config.Config {
    private transient static final String HUD = "HUD";
    @HUD(
            name = "Profit Calculator HUD", category = HUD, subcategory = " "
    )
    public ProfitCalculatorHUD profitHUD = new ProfitCalculatorHUD();
    public Config() {
        super(new Mod("catand Mine Mod", ModType.HYPIXEL, "/assets/bps.png"), "/catandminemod/config.json");
        initialize();
        save();
    }
}
