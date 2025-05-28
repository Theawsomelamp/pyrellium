package com.lankaster.pyrellium.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HangingSilkBlock extends HangingVinesBlock {

    public HangingSilkBlock(Settings settings) {
        super(settings);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.slowMovement(state, new Vec3d(0.25F, 0.05F, 0.25F));
    }
}
