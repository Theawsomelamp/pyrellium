package com.lankaster.pyrellium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BasaltVentBlock extends Block {
    public BasaltVentBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (random.nextInt(2) == 0) {
            world.addParticle(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    true,
                    pos.getX() + 0.5 + random.nextDouble() / 3.0 * (random.nextBoolean() ? 1 : -1),
                    pos.getY() + random.nextDouble() + random.nextDouble(),
                    pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (random.nextBoolean() ? 1 : -1),
                    0.0,
                    0.07,
                    0.0
            );
        }
    }
}
