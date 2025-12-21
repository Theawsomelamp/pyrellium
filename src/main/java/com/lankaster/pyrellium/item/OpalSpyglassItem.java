package com.lankaster.pyrellium.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class OpalSpyglassItem extends SpyglassItem {
    public OpalSpyglassItem(Settings settings){
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        tooltip.add(Text.keybind("key.attack").append(Text.translatable("item.pyrellium.opal_spyglass.desc.left_click")).formatted(Formatting.GRAY));
        tooltip.add(Text.keybind("key.pickItem").append(Text.translatable("item.pyrellium.opal_spyglass.desc.middle_click")).formatted(Formatting.GRAY));
    }
}
