package com.lankaster.pyrellium.world.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class CrackFeature extends Feature<CrackFeatureConfig> {

    public CrackFeature(Codec<CrackFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CrackFeatureConfig> context) {
        CrackFeatureConfig crackFeatureConfig = context.config();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        BlockPos.MutableBlockPos mutable = origin.mutable();
        WorldGenLevel world = context.level();

        GeodeBlockSettings geodeLayerConfig = crackFeatureConfig.geodeLayerConfig();
        GeodeLayerSettings geodeLayerThicknessConfig = crackFeatureConfig.geodeLayerThicknessConfig();

        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        boolean onXAxis = Objects.equals(direction.getAxis().getName(), "x");
        int length = crackFeatureConfig.length().sample(random);
        int width = crackFeatureConfig.width().sample(random);
        int depth = crackFeatureConfig.depth().sample(random);
        int widthX = onXAxis ? 0 : width;
        int widthZ = onXAxis ? width : 0;

        List<BlockPos> list = Lists.newArrayList();
        List<BlockState> list2 = geodeLayerConfig.innerPlacements;
        Predicate<BlockState> predicate = isReplaceable(crackFeatureConfig.geodeLayerConfig().cannotReplace).and((state) -> !state.is(Blocks.AIR));

        for(int i = 0; i < length; ++i) {
            if (world.getBlockState(mutable).is(Blocks.AIR)) {
                mutable.move(Direction.DOWN);
            } else if (!world.getBlockState(mutable.above()).is(Blocks.AIR)) {
                mutable.move(Direction.UP);
            }

            double relativeSize = ((double) (Math.min(i, length - i) * 2) / length);

            for(BlockPos blockPos : BlockPos.betweenClosed(mutable.immutable().offset(-widthX, -depth, -widthZ), mutable.immutable().offset(widthX, 0, widthZ))) {

                int distanceHorizontal = onXAxis ? Math.abs(mutable.getZ() - blockPos.getZ()) : Math.abs(mutable.getX() - blockPos.getX());
                int distanceVertical = Math.abs(mutable.getY() - blockPos.getY());
                double widthPercentage = (double) distanceHorizontal / (width * relativeSize);
                double depthPercentage = (double) distanceVertical / (depth * relativeSize);
                double relativeDistance = (widthPercentage + depthPercentage);

                if (relativeDistance >= geodeLayerThicknessConfig.outerLayer || relativeSize <= 0.35) {
                    // skip
                } else if (relativeDistance >= geodeLayerThicknessConfig.middleLayer) {
                    this.safeSetBlock(world, blockPos, geodeLayerConfig.outerLayerProvider.getState(random, blockPos), predicate);
                } else if (relativeDistance >= geodeLayerThicknessConfig.innerLayer) {
                    this.safeSetBlock(world, blockPos, geodeLayerConfig.middleLayerProvider.getState(random, blockPos), predicate);
                } else if (relativeDistance >= geodeLayerThicknessConfig.filling) {
                    boolean bl2 = (double)random.nextFloat() < crackFeatureConfig.buddingChance();
                    if (bl2) {
                        this.safeSetBlock(world, blockPos, geodeLayerConfig.alternateInnerLayerProvider.getState(random, blockPos), predicate);
                        list.add(blockPos.immutable());
                    } else {
                        this.safeSetBlock(world, blockPos, geodeLayerConfig.innerLayerProvider.getState(random, blockPos), predicate);
                    }
                } else {
                    this.safeSetBlock(world, blockPos, geodeLayerConfig.fillingProvider.getState(random, blockPos), predicate);
                }
            }

            for(BlockPos blockPos2 : list) {
                if (world.getBlockState(blockPos2).is(BlockTags.GEODE_INVALID_BLOCKS) || world.getBlockState(blockPos2).is(Blocks.AIR)) break;
                BlockState blockState = Util.getRandom(list2, random);

                for(Direction direction2 : Direction.values()) {
                    if (blockState.hasProperty(BlockStateProperties.FACING)) {
                        blockState = blockState.setValue(BlockStateProperties.FACING, direction2);
                    }

                    BlockPos blockPos3 = blockPos2.relative(direction2);
                    BlockState blockState2 = world.getBlockState(blockPos3);
                    if (BuddingAmethystBlock.canClusterGrowAtState(blockState2)) {
                        this.setBlock(world, blockPos3, blockState);
                        break;
                    }
                }
            }

            if (random.nextFloat() < crackFeatureConfig.bendChance()) {
                mutable.move(direction.getClockWise());
            }

            mutable.move(direction);
        }


        return true;
    }
}