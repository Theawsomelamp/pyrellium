package com.lankaster.pyrellium.block;

import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

public class HangingSilkBlock extends HangingVinesBlock {

    public HangingSilkBlock(Properties settings) {
        super(settings);
    }

    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        entity.makeStuckInBlock(state, new Vec3(0.25F, 0.05F, 0.25F));
    }
}
