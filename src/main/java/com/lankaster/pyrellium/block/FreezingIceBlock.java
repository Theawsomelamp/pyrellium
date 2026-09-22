package com.lankaster.pyrellium.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class FreezingIceBlock extends TransparentBlock {

    public FreezingIceBlock(Properties settings) {
        super(settings);
    }

    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.setIsInPowderSnow(true);
        }


        super.stepOn(world, pos, state, entity);
    }
}
