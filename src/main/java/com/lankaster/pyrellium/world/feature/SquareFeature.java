package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class SquareFeature extends Feature<SquareFeatureConfig> {
    public SquareFeature(Codec<SquareFeatureConfig> configCodec) {
        super(configCodec);
    }

    public boolean generate(FeatureContext<SquareFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        SquareFeatureConfig config = context.getConfig();

        BlockStateProvider state = config.state();
        BlockState blockState = state.get(random, origin);

        for(BlockPos blockPos2 : BlockPos.iterate(origin.add(0, 0, 0), origin.add(config.width(), 0, config.width()))) {
            for (int i = 0; i < (config.height()); i++) {
                world.setBlockState(blockPos2, blockState, 2);
                blockPos2 = blockPos2.up();

                if (blockPos2.getY() >= world.getTopY()) break;
            }
        }

        return false;
    }
}