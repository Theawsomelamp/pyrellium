package com.lankaster.pyrellium.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OpalSpyglassItem extends SpyglassItem {
    public OpalSpyglassItem(Settings settings){
        super(settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.keybind("key.attack").append(Text.translatable("item.pyrellium.opal_spyglass.desc.left_click")).formatted(Formatting.GRAY));
        tooltip.add(Text.keybind("key.pickItem").append(Text.translatable("item.pyrellium.opal_spyglass.desc.middle_click")).formatted(Formatting.GRAY));
    }
}
