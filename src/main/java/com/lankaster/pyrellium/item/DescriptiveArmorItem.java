package com.lankaster.pyrellium.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

import java.util.List;

public class DescriptiveArmorItem extends ArmorItem {
    public DescriptiveArmorItem(Holder<ArmorMaterial> material, Type type, Properties settings){
        super(material, type, settings);
    }

    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        String[] txt = Component.translatable(this.getOrCreateDescriptionId() + ".desc").getString().split("\\n");
        for (String t : txt)
            tooltip.add(Component.literal(t).withStyle(ChatFormatting.GRAY));
    }
}
