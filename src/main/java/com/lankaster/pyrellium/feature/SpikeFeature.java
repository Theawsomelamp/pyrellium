package com.lankaster.pyrellium.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class SpikeFeature extends Feature<SpikeFeatureConfig> {
    public SpikeFeature(Codec<SpikeFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<SpikeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        SpikeFeatureConfig config = context.getConfig();

        int number = config.height().get(random);
        int height;
        BlockStateProvider state = config.state();

        BlockState blockState = state.get(random, origin);

        for(BlockPos blockPos2 : BlockPos.iterate(origin.add(-2, 0, -2), origin.add(2, 0, 2))) {
            int m = blockPos2.getX() - origin.getX();
            int n = blockPos2.getZ() - origin.getZ();

            if (world.getBlockState(blockPos2.up()).isOf(Blocks.AIR) && (m * m + n * n <= 2 * 2)) {
                if (m * m + n * n >= 2) {
                    height = number / 3;
                } else if (m * m + n * n == 1) {
                    height = number * 2 / 3;
                } else {
                    height = number;
                }

                for (int i = 0; i < (height - random.nextInt(2)); i++) {
                    world.setBlockState(blockPos2, blockState, 2);
                    blockPos2 = blockPos2.up();

                    if (blockPos2.getY() >= world.getTopY()) break;
                }
            }
        }

        // the game couldn't find a place to put the pillar
        return false;
    }
}

