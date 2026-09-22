package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class SquareFeature extends Feature<SquareFeatureConfig> {
    public SquareFeature(Codec<SquareFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean place(FeaturePlaceContext<SquareFeatureConfig> context) {
        WorldGenLevel world = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        SquareFeatureConfig config = context.config();

        BlockStateProvider state = config.state();
        BlockState blockState = state.getState(random, origin);

        for(BlockPos blockPos2 : BlockPos.betweenClosed(origin.offset(0, 0, 0), origin.offset(config.width(), 0, config.width()))) {
            for (int i = 0; i < (config.height()); i++) {
                world.setBlock(blockPos2, blockState, 2);
                blockPos2 = blockPos2.above();

                if (blockPos2.getY() >= world.getMaxBuildHeight()) break;
            }
        }

        return false;
    }
}