package com.lankaster.pyrellium.item;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public class DescriptiveItem extends Item {
    public DescriptiveItem(Settings settings){
        super(settings);
    }

    @SuppressWarnings("deprecation")
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);

        String[] txt = Text.translatable(this.getTranslationKey() + ".desc").getString().split("\\n");
        for (String t : txt)
            textConsumer.accept(Text.literal(t).formatted(Formatting.GRAY));
    }
}
