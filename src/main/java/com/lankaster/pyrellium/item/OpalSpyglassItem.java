package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.config.Config;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

import java.util.List;

public class OpalSpyglassItem extends SpyglassItem {
    public OpalSpyglassItem(Properties settings){
        super(settings);
    }

    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        tooltip.add(Component.keybind("key.attack").append(Component.translatable("item.pyrellium.opal_spyglass.desc.left_click")).withStyle(ChatFormatting.GRAY));
        if (Config.instance().items.opal_spyglass_block_sharing)
            tooltip.add(Component.keybind("key.pickItem").append(Component.translatable("item.pyrellium.opal_spyglass.desc.middle_click")).withStyle(ChatFormatting.GRAY));
    }
}
