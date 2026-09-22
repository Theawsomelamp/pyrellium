package com.lankaster.pyrellium.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class BurningFlowerBlock extends FlowerBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public BurningFlowerBlock(Holder<MobEffect> suspiciousStewEffect, int effectDuration, Properties settings) {
        super(suspiciousStewEffect, effectDuration, settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, true));
    }

    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (state.getValue(ACTIVE)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.igniteForSeconds(3);
            }
            world.setBlock(pos, state.cycle(ACTIVE), 2);
            world.scheduleTick(pos, this, 200);
            for (int i = 0; i < 10; ++i) {
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), pos.getY() + 1, entity.getZ(), Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083333336F, 0.05F, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083333336F);
            }
        }
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.getValue(ACTIVE)) {
            world.setBlock(pos, state.cycle(ACTIVE), 2);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }
}