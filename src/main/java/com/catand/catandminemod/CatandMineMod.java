package com.catand.catandminemod;

import com.catand.catandminemod.functions.PixelPartySolver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


@Mod(modid = CatandMineMod.MODID, name = CatandMineMod.NAME, version = CatandMineMod.VERSION)
public class CatandMineMod {
    public static final String MODID = "catandminemod";
    public static final String NAME = "catand Mine Mod";
    // Version gets automatically set. If you wish to change it, change it in the build.gradle.kts file
    public static final String VERSION = "%%VERSION%%";

    // the actual mod version from gradle properties, should match with VERSION
    public static String MODVERSION = VERSION;
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static Config config;
    public static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        config = new Config();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PixelPartySolver());
    }
}
