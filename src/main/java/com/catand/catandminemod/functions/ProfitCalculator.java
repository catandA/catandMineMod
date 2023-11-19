package com.catand.catandminemod.functions;

import com.catand.catandminemod.Clock;
import com.catand.catandminemod.Utils;
import com.catand.catandminemod.events.BlockChangeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ProfitCalculator {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private final Clock updateClock = new Clock();
    public static long blocksBroken = 0;
    public static String runtime = "0h 0m 0s";
    public static String blocksPerHour = "0 BPH";
    public static boolean started = false;
    public static long startTime = 0;

    @SubscribeEvent
    public void onBlockChange(BlockChangeEvent event) {
        if (event.old.getBlock() == Blocks.glass && event.update.getBlock() != Blocks.glass) {
            blocksBroken++;
        }
        if (event.old.getBlock() == Blocks.glass_pane && event.update.getBlock() != Blocks.glass_pane) {
            blocksBroken++;
        }
        if (event.old.getBlock() == Blocks.stained_glass && event.update.getBlock() != Blocks.stained_glass) {
            blocksBroken++;
        }
        if (event.old.getBlock() == Blocks.stained_glass_pane && event.update.getBlock() != Blocks.stained_glass_pane) {
            blocksBroken++;
        }
        if (blocksBroken != 0 && !started) {
            started = true;
            startTime = System.currentTimeMillis();
            runtime = (Utils.formatTime(System.currentTimeMillis() - startTime));
        }
    }

    @SubscribeEvent
    public void onTickUpdateProfit(TickEvent.ClientTickEvent event) {
        if (!started) return;
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if (updateClock.passed()) {
            updateClock.reset();
            updateClock.schedule(100);
            runtime = (Utils.formatTime(System.currentTimeMillis() - startTime));
            float bph = Math.round((float) blocksBroken / 2 / (System.currentTimeMillis() - startTime) * 10000f) / 10f * 3600;
            blocksPerHour = (bph + " BPH");
        }
    }
}
