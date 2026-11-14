package com.lankaster.pyrellium.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.Nullable;

public class ReplaceWallFeature extends Feature<ReplaceWallFeatureConfig> {
    public ReplaceWallFeature(Codec<ReplaceWallFeatureConfig> configCodec){
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<ReplaceWallFeatureConfig> context){
        ReplaceWallFeatureConfig replaceWallFeatureConfig = context.getConfig();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        BlockPos blockPos = moveDownToTarget(replaceWallFeatureConfig, structureWorldAccess, context.getOrigin().mutableCopy().clamp(Direction.Axis.Y, structureWorldAccess.getBottomY() + 1, structureWorldAccess.getTopY() - 1));
        if (blockPos == null) {
            return false;
        } else {
            int i = replaceWallFeatureConfig.radius().get(random);
            int j = replaceWallFeatureConfig.radius().get(random);
            int k = replaceWallFeatureConfig.radius().get(random);
            int l = Math.max(i, Math.max(j, k));
            boolean bl = false;

            for(BlockPos blockPos2 : BlockPos.iterateOutwards(blockPos, i, j, k)) {
                if (blockPos2.getManhattanDistance(blockPos) > l) {
                    break;
                }

                if (replaceWallFeatureConfig.target().test(structureWorldAccess, blockPos2)) {
                    for (Direction direction : Direction.values()) {
                        BlockPos blockPos3 = blockPos2.offset(direction);
                        if (structureWorldAccess.getBlockState(blockPos3).isReplaceable()) {
                            this.setBlockState(structureWorldAccess, blockPos2, replaceWallFeatureConfig.provider().get(random, blockPos2));
                            bl = true;
                        }
                    }
                }
            }

            return bl;
        }
    }

    @Nullable
    private static BlockPos moveDownToTarget(ReplaceWallFeatureConfig config, StructureWorldAccess world, BlockPos.Mutable mutablePos) {
        while(mutablePos.getY() > world.getBottomY() + 1) {
            if (config.target().test(world, mutablePos)) {
                return mutablePos;
            }

            mutablePos.move(Direction.DOWN);
        }

        return null;
    }
}
