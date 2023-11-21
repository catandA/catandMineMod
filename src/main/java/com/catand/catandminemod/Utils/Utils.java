package com.catand.catandminemod.Utils;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Utils {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public boolean hasPixelPartyScoreboard = false;
    private static final Set<String> PIXELPARTY_IN_ALL_LANGUAGES = Sets.newHashSet("PIXEL PARTY");

    public static String formatTime(long millis) {
        return String.format("%dh %dm %ds",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public static void BoxWithTopESP(BlockPos pos, Color c, boolean Blend) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        block.setBlockBoundsBasedOnState(Minecraft.getMinecraft().theWorld, pos);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        if (Blend) {
            GlStateManager.enableBlend();
        }
        GlStateManager.color((float) c.getRed() / 255.0F, (float) c.getGreen() / 255.0F, (float) c.getBlue() / 255.0F, (float) c.getAlpha() / 255.0F);
        AxisAlignedBB box = block.getSelectedBoundingBox(Minecraft.getMinecraft().theWorld, pos).offset(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ).expand(0.0010000000474974513D, 0.0010000000474974513D, 0.0010000000474974513D);
        BoxWithoutTopESPRender(box);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
    }

    public static void BoxWithoutTopESPRender(AxisAlignedBB box) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        tessellator.draw();
    }

    public static void renderTrace(BlockPos from, Color c, float width) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        EntityPlayer player = mc.thePlayer;
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.color((float) c.getRed() / 255.0F, (float) c.getGreen() / 255.0F, (float) c.getBlue() / 255.0F, (float) c.getAlpha() / 255.0F);
        GL11.glLineWidth(width);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
        worldrenderer.pos(from.getX() - renderManager.viewerPosX + 0.5f, from.getY() - renderManager.viewerPosY + 1f, from.getZ() - renderManager.viewerPosZ + 0.5f).endVertex();
        worldrenderer.pos(player.posX - renderManager.viewerPosX, player.posY - renderManager.viewerPosY + player.eyeHeight, player.posZ - renderManager.viewerPosZ).endVertex();
        tessellator.draw();
        GL11.glLineWidth(1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
    }

    public static double getDistance(BlockPos pos1, BlockPos pos2) {
        return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2) + Math.pow(pos1.getZ() - pos2.getZ(), 2));
    }

    public void updatePixelPartyScoreboard() {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc != null && mc.theWorld != null && mc.thePlayer != null) {
            if (mc.isSingleplayer() || mc.thePlayer.getClientBrand() == null ||
                    !mc.thePlayer.getClientBrand().toLowerCase().contains("hypixel")) {
                hasPixelPartyScoreboard = false;
                return;
            }

            Scoreboard scoreboard = mc.theWorld.getScoreboard();
            ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
            if (sidebarObjective != null) {
                String objectiveName = sidebarObjective.getDisplayName().replaceAll("(?i)\\u00A7.", "");
                for (String pixelParty : PIXELPARTY_IN_ALL_LANGUAGES) {
                    if (objectiveName.startsWith(pixelParty)) {
                        hasPixelPartyScoreboard = true;
                        return;
                    }
                }
            }
            hasPixelPartyScoreboard = false;
        }
    }
}