package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrystalArrowItem extends ArrowItem {
    public CrystalArrowItem(Item.Properties settings) {
        super(settings);
    }

    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        CrystalArrowEntity arrowEntity =  new CrystalArrowEntity(world, shooter, stack.copyWithCount(1), shotFrom);
        arrowEntity.initFromStack(stack);
        return arrowEntity;
    }

    public Projectile asProjectile(Level world, Position pos, ItemStack stack, Direction direction) {
        CrystalArrowEntity arrowEntity = new CrystalArrowEntity(world, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), (ItemStack)null);
        arrowEntity.initFromStack(stack);
        arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrowEntity;
    }

    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        tooltip.add(Component.translatable("item.pyrellium.crystal_arrow.desc").withStyle(ChatFormatting.GRAY));
    }
}
