package com.catand.catandminemod;

import com.catand.catandminemod.command.CMMChatCommand;
import com.catand.catandminemod.command.CMMChatPlayerListCommand;
import com.catand.catandminemod.command.CMMChatShowCommand;
import com.catand.catandminemod.functions.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


@Mod(modid = CatandMineMod.MODID, name = CatandMineMod.NAME, version = CatandMineMod.VERSION)
public class CatandMineMod {
	public static final String MODID = "catandminemod";
	public static final String NAME = "catand Mine Mod";
	// Version gets automatically set. If you wish to change it, change it in the build.gradle.kts file
	public static final String VERSION = "2.3.2";

	// the actual mod version from gradle properties, should match with VERSION
	public static String MODVERSION = VERSION;
	public static final Minecraft mc = Minecraft.getMinecraft();

	public static Config config;
	public static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		config = new Config();
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new PixelPartySolver());
		MinecraftForge.EVENT_BUS.register(new CustomRank());
		MinecraftForge.EVENT_BUS.register(new UpdateReminder());
		MinecraftForge.EVENT_BUS.register(new CMMChat());
		ClientCommandHandler.instance.registerCommand(new CMMChatCommand());
		ClientCommandHandler.instance.registerCommand(new CMMChatPlayerListCommand());
		ClientCommandHandler.instance.registerCommand(new CMMChatShowCommand());
		new Thread(RankList::getRankList).start();
		new Thread(CMMChat::updateServerURL).start();
	}
}
