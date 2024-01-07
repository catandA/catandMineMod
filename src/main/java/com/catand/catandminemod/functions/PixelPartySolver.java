package com.catand.catandminemod.functions;

import com.catand.catandminemod.CatandMineMod;
import com.catand.catandminemod.Utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.catand.catandminemod.CatandMineMod.mc;

public class PixelPartySolver {
	static Block currentColorBlock = Blocks.air;
	static List<BlockPos> positions;
	static BlockPos closestPos;
	static int NowX, NowY, NowZ = 0;
	static int StartX = -32;
	static int StartY = 0;
	static int StartZ = -32;
	static int EndX = 31;
	static int EndY = 1;
	static int EndZ = 31;
	EntityPlayer player;
	static Block currentBlock;
	static int Tick = 0;
	int RefreshTick = 2;

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.theWorld == null || mc.thePlayer == null) {
			return;
		}
		//遍历快捷栏
		ItemStack stack = mc.thePlayer.inventory.mainInventory[8];
		if (stack != null) {
			currentColorBlock = Block.getBlockFromItem(stack.getItem());
		} else {
			currentColorBlock = Blocks.air;
			return;
		}
		if (!CatandMineMod.config.pixelPartySolver || !(currentColorBlock instanceof BlockColored)) {
			return;
		}
		if (Tick == RefreshTick) {
			positions = new ArrayList<BlockPos>();
			closestPos = null;
			this.player = mc.thePlayer;
			NowX = StartX;
			NowY = StartY;
			NowZ = StartZ;
			while (NowY != EndY) {
				if (NowX == EndX) {
					if (NowZ == EndZ) {
						NowZ = StartZ;
						NowX = StartX;
						NowY = NowY + 1;
					} else {
						NowX = StartX;
						NowZ = NowZ + 1;
					}
				} else {
					NowX = NowX + 1;
				}
				currentBlock = mc.theWorld.getBlockState(new BlockPos(NowX, NowY, NowZ)).getBlock();
				if (currentBlock == currentColorBlock && mc.theWorld.getBlockState(new BlockPos(NowX, NowY, NowZ)).getValue(BlockColored.COLOR) == EnumDyeColor.byMetadata(stack.getMetadata())) {
					positions.add(new BlockPos(NowX, NowY, NowZ));
					if (closestPos == null || Utils.getDistance(mc.thePlayer.getPosition(), new BlockPos(NowX, NowY, NowZ)) < Utils.getDistance(mc.thePlayer.getPosition(), closestPos)) {
						closestPos = new BlockPos(NowX, NowY, NowZ);
					}
				}
			}
			NowX = StartX;
			NowY = StartY;
			NowZ = StartZ;
			Tick = 0;
		} else {
			Tick = Tick + 1;
		}
	}

	@SubscribeEvent
	public void onRender(RenderWorldLastEvent event) {
		if (CatandMineMod.config.pixelPartySolver && positions != null && currentColorBlock instanceof BlockColored) {
			for (BlockPos position : positions) {
				if (mc.theWorld != null) {
					Utils.BoxWithTopESP(position, new Color(255, 255, 255, 255), false);
					if (CatandMineMod.config.pixelPartySolverLine) {
						Utils.renderTrace(closestPos, new Color(255, 0, 0, 255), 2);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		reset();
	}

	public static void reset() {
		currentColorBlock = Blocks.air;
		currentBlock = Blocks.air;
		positions = null;
		closestPos = null;
		NowX = 0;
		NowY = 0;
		NowZ = 0;
	}
}