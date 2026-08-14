package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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

        Direction direction = config.direction();

        if (!direction.getAxis().isVertical()) return false;

        int maxHeight = config.height().get(random);
        int radius = config.radius().get(random);
        int height;
        BlockStateProvider state = config.state();
        BlockStateProvider tip = config.tip();

        BlockState blockState = state.get(random, origin);
        BlockState tipState = tip.get(random, origin);

        for(BlockPos blockPos2 : BlockPos.iterate(origin.add(-radius, 0, -radius), origin.add(radius, 0, radius))) {
            int distance = blockPos2.getManhattanDistance(origin);


            if (world.getBlockState(blockPos2.offset(direction)).isOf(Blocks.AIR) && (distance <= radius)) {
                height = (int) (maxHeight *  ((float) (radius - distance + 1) / (float) (radius + 1)));

                for (int i = 0; i < (height - random.nextInt(2)); i++) {
                    world.setBlockState(blockPos2, blockState, 2);
                    blockPos2 = blockPos2.offset(direction);
                    if (random.nextFloat() < config.chance()) {
                        world.setBlockState(blockPos2, tipState,2);
                    }

                    if (blockPos2.getY() >= world.getTopY()) break;
                }
            }
        }

        // the game couldn't find a place to put the pillar
        return false;
    }
}