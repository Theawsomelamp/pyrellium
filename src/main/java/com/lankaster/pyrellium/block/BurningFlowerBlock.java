package com.lankaster.pyrellium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BurningFlowerBlock extends FlowerBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public BurningFlowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVE, true));
    }

    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (state.get(ACTIVE)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.setOnFireFor(3);
            }
            world.setBlockState(pos, state.cycle(ACTIVE), 2);
            world.scheduleBlockTick(pos, this, 200);
            for (int i = 0; i < 10; ++i) {
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), pos.getY() + 1, entity.getZ(), MathHelper.nextBetween(world.getRandom(), -1.0F, 1.0F) * 0.083333336F, 0.05F, MathHelper.nextBetween(world.getRandom(), -1.0F, 1.0F) * 0.083333336F);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(ACTIVE)) {
            world.setBlockState(pos, state.cycle(ACTIVE), 2);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }
}
