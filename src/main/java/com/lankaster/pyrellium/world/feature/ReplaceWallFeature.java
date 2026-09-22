package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.Nullable;

public class ReplaceWallFeature extends Feature<ReplaceWallFeatureConfig> {
    public ReplaceWallFeature(Codec<ReplaceWallFeatureConfig> configCodec){
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ReplaceWallFeatureConfig> context){
        ReplaceWallFeatureConfig replaceWallFeatureConfig = context.config();
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        Block block = replaceWallFeatureConfig.target().getState(random, origin).getBlock();
        BlockPos blockPos = moveDownToTarget(structureWorldAccess, context.origin().mutable().clamp(Direction.Axis.Y, structureWorldAccess.getMinBuildHeight() + 1, structureWorldAccess.getMaxBuildHeight() - 1), block);
        if (blockPos == null) {
            return false;
        } else {
            int i = replaceWallFeatureConfig.radius().sample(random);
            int j = replaceWallFeatureConfig.radius().sample(random);
            int k = replaceWallFeatureConfig.radius().sample(random);
            int l = Math.max(i, Math.max(j, k));
            boolean bl = false;

            for(BlockPos blockPos2 : BlockPos.withinManhattan(blockPos, i, j, k)) {
                if (blockPos2.distManhattan(blockPos) > l) {
                    break;
                }

                BlockState blockState = structureWorldAccess.getBlockState(blockPos2);
                if (blockState.is(block)) {
                    for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(RandomSource.create())) {
                        BlockPos blockPos3 = blockPos2.relative(direction);
                        if (structureWorldAccess.getBlockState(blockPos3).canBeReplaced()) {
                            this.setBlock(structureWorldAccess, blockPos2, replaceWallFeatureConfig.provider().getState(random, blockPos2));
                            bl = true;
                        }
                    }
                }
            }

            return bl;
        }
    }

    @Nullable
    private static BlockPos moveDownToTarget(LevelAccessor world, BlockPos.MutableBlockPos mutablePos, Block target) {
        while(mutablePos.getY() > world.getMinBuildHeight() + 1) {
            BlockState blockState = world.getBlockState(mutablePos);
            if (blockState.is(target)) {
                return mutablePos;
            }

            mutablePos.move(Direction.DOWN);
        }

        return null;
    }
}