package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.TransparentBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FreezingIceBlock extends TransparentBlock {

    public FreezingIceBlock(Settings settings) {
        super(settings);
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (Config.instance().blocks.black_ice_freezing) {
            if (entity instanceof LivingEntity livingEntity && !EnchantmentHelper.hasFrostWalker(livingEntity)) {
                livingEntity.setInPowderSnow(true);
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }
}
