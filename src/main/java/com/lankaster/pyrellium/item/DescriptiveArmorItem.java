package com.lankaster.pyrellium.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class DescriptiveArmorItem extends ArmorItem {
    public DescriptiveArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings){
        super(material, type, settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        String[] txt = Text.translatable(this.getOrCreateTranslationKey() + ".desc").getString().split("\\n");
        for (String t : txt)
            tooltip.add(Text.literal(t).formatted(Formatting.GRAY));
    }
}
