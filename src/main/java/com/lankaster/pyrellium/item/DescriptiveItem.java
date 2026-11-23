package com.lankaster.pyrellium.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DescriptiveItem extends Item {
    public DescriptiveItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        String[] txt = Text.translatable(this.getTranslationKey() + ".desc").getString().split("\\n");
        for (String t : txt)
            tooltip.add(Text.translatable(t).formatted(Formatting.GRAY));
    }
}
