package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CrystalArrowItem extends ArrowItem {
    public CrystalArrowItem(Settings settings) {
        super(settings);
    }

    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        CrystalArrowEntity arrow = new CrystalArrowEntity(world, shooter);
        arrow.initFromStack(stack);
        return arrow;
    }
}
