package com.catand.catandminemod.mixin;

import com.catand.catandminemod.functions.CustomRank;
import com.catand.catandminemod.functions.RankList;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = NetworkPlayerInfo.class)
public abstract class MixinNetworkPlayerInfo {
	private static CustomRank customRank = new CustomRank();
	@Shadow
	private IChatComponent displayName;

	@Inject(method = "setDisplayName", at = @At("RETURN"))
	public void setDisplayName(IChatComponent displayNameIn, CallbackInfo ci) {
		for(String key : RankList.rankMap.keySet()) {
			if (displayNameIn.getFormattedText().contains(key)) {
				displayNameIn = new ChatComponentText(customRank.replaceName(displayNameIn.getFormattedText()));
			}
		}
		this.displayName = displayNameIn;
	}

	@Inject(method = "<init>(Lnet/minecraft/network/play/server/S38PacketPlayerListItem$AddPlayerData;)V", at = @At("RETURN"))
	public void NetworkPlayerInfo(S38PacketPlayerListItem.AddPlayerData p_i46295_1_, CallbackInfo ci) {
		for (String key : RankList.rankMap.keySet()) {
			if (displayName.getFormattedText().contains(key)) {
				this.displayName = new ChatComponentText(customRank.replaceName(displayName.getFormattedText()));
			}
		}
	}
}
