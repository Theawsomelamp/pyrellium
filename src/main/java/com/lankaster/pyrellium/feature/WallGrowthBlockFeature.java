package com.lankaster.pyrellium.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import static net.minecraft.block.HorizontalFacingBlock.FACING;

public class WallGrowthBlockFeature extends Feature<SimpleBlockFeatureConfig> {

    public WallGrowthBlockFeature(Codec<SimpleBlockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {
        SimpleBlockFeatureConfig simpleBlockFeatureConfig = context.getConfig();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        BlockState blockState = simpleBlockFeatureConfig.toPlace().get(context.getRandom(), blockPos);
        if (blockState.canPlaceAt(structureWorldAccess, blockPos)) {
            for (Direction direction : Direction.Type.HORIZONTAL.getShuffled(Random.create())){
                BlockPos blockPos1 = blockPos.offset(direction);
                BlockState blockState1 = structureWorldAccess.getBlockState(blockPos1);
                if (blockState1.isReplaceable()) {
                    structureWorldAccess.setBlockState(blockPos1, simpleBlockFeatureConfig.toPlace().get(context.getRandom(), blockPos1).with(FACING, direction.getOpposite()), 3);
                }
                return true;
            }
        }
        return false;
    }
}
