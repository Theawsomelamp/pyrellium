package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrystalArrowItem extends ArrowItem {
    public CrystalArrowItem(Settings settings) {
        super(settings);
    }

    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        CrystalArrowEntity arrow = new CrystalArrowEntity(world, shooter);
        arrow.initFromStack(stack);
        return arrow;
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.translatable("item.pyrellium.crystal_arrow.desc").formatted(Formatting.GRAY));
    }
}
