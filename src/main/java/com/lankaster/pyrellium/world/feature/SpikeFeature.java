package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class SpikeFeature extends Feature<SpikeFeatureConfig> {
    public SpikeFeature(Codec<SpikeFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<SpikeFeatureConfig> context) {
        WorldGenLevel world = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        SpikeFeatureConfig config = context.config();

        Direction direction = config.direction();

        if (!direction.getAxis().isVertical()) return false;

        int maxHeight = config.height().sample(random);
        int radius = config.radius().sample(random);
        int height;
        BlockStateProvider state = config.state();
        BlockStateProvider tip = config.tip();

        BlockState blockState = state.getState(random, origin);
        BlockState tipState = tip.getState(random, origin);

        for(BlockPos blockPos2 : BlockPos.betweenClosed(origin.offset(-radius, 0, -radius), origin.offset(radius, 0, radius))) {
            int distance = blockPos2.distManhattan(origin);


            if (world.getBlockState(blockPos2.relative(direction)).is(Blocks.AIR) && (distance <= radius)) {
                height = (int) (maxHeight *  ((float) (radius - distance + 1) / (float) (radius + 1)));

                for (int i = 0; i < (height - random.nextInt(2)); i++) {
                    world.setBlock(blockPos2, blockState, 2);
                    blockPos2 = blockPos2.relative(direction);
                    if (random.nextFloat() < config.chance()) {
                        world.setBlock(blockPos2, tipState,2);
                    }

                    if (blockPos2.getY() >= world.getMaxBuildHeight()) break;
                }
            }
        }

        // the game couldn't find a place to put the pillar
        return false;
    }
}