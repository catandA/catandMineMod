package com.catand.catandminemod.functions;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class CustomRank {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void modifyArmorStandName(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
        if (Minecraft.getMinecraft().theWorld != null) {
                Entity entity = event.entity;
                    if (entity.hasCustomName()) {
                        String nameWithRank = replaceName(entity.getCustomNameTag());
                        entity.setCustomNameTag(nameWithRank);
                    }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void modifyChatMessage(ClientChatReceivedEvent event){
        for(String key : RankList.rankMap.keySet()) {
            if (event.message.getFormattedText().contains(key)) {
                event.message = new ChatComponentText(replaceName(event.message.getFormattedText()));
            }
        }
    }
    public String replaceName(String name){
        for(Map.Entry<String,String> entry : RankList.rankMap.entrySet()){
            if (name.contains(entry.getKey()) && !name.contains(entry.getValue())) {
                name = name.replace(entry.getKey(),entry.getValue());
            }
        }
        return name;
    }
}
