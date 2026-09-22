package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class WallGrowthBlockFeature extends Feature<SimpleBlockConfiguration> {

    public WallGrowthBlockFeature(Codec<SimpleBlockConfiguration> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration simpleBlockFeatureConfig = context.config();
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        BlockState blockState = simpleBlockFeatureConfig.toPlace().getState(context.random(), blockPos);
        if (blockState.canSurvive(structureWorldAccess, blockPos)) {
            for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(RandomSource.create())){
                BlockPos blockPos1 = blockPos.relative(direction);
                BlockState blockState1 = structureWorldAccess.getBlockState(blockPos1);
                if (blockState1.canBeReplaced()) {
                    structureWorldAccess.setBlock(blockPos1, simpleBlockFeatureConfig.toPlace().getState(context.random(), blockPos1).setValue(FACING, direction.getOpposite()), 3);
                }
                return true;
            }
        }
        return false;
    }
}
