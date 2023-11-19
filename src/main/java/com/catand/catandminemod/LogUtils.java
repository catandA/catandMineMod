package com.catand.catandminemod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class LogUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public synchronized static void sendLog(ChatComponentText chat) {
        if (mc.thePlayer != null)
            mc.thePlayer.addChatMessage(chat);
    }
    public static void sendSuccess(String message) {
        sendLog(new ChatComponentText("§2§lcatandMineMod §8» §a" + message));
    }
    public static void sendWarning(String message) {
        sendLog(new ChatComponentText("§6§lcatandMineMod §8» §e" + message));
    }
    public static void sendError(String message) {
        sendLog(new ChatComponentText("§4§lcatandMineMod §8» §c" + message));
    }
    public static void sendFailsafeMessage(String message) {
        sendLog(new ChatComponentText("§5§lcatandMineMod §8» §d§l" + message));
    }
}
