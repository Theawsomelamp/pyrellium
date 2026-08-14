package com.lankaster.pyrellium.world.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BuddingAmethystBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GeodeLayerConfig;
import net.minecraft.world.gen.feature.GeodeLayerThicknessConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class CrackFeature extends Feature<CrackFeatureConfig> {

    public CrackFeature(Codec<CrackFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<CrackFeatureConfig> context) {
        CrackFeatureConfig crackFeatureConfig = context.getConfig();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();
        BlockPos.Mutable mutable = origin.mutableCopy();
        StructureWorldAccess world = context.getWorld();

        GeodeLayerConfig geodeLayerConfig = crackFeatureConfig.geodeLayerConfig();
        GeodeLayerThicknessConfig geodeLayerThicknessConfig = crackFeatureConfig.geodeLayerThicknessConfig();

        Direction direction = Direction.Type.HORIZONTAL.random(random);
        boolean onXAxis = Objects.equals(direction.getAxis().asString(), "x");
        int length = crackFeatureConfig.length().get(random);
        int width = crackFeatureConfig.width().get(random);
        int depth = crackFeatureConfig.depth().get(random);
        int widthX = onXAxis ? 0 : width;
        int widthZ = onXAxis ? width : 0;

        List<BlockPos> list = Lists.newArrayList();
        List<BlockState> list2 = geodeLayerConfig.innerBlocks;
        Predicate<BlockState> predicate = notInBlockTagPredicate(crackFeatureConfig.geodeLayerConfig().cannotReplace).and((state) -> !state.isOf(Blocks.AIR));

        for(int i = 0; i < length; ++i) {
            if (world.getBlockState(mutable).isOf(Blocks.AIR)) {
                mutable.move(Direction.DOWN);
            } else if (!world.getBlockState(mutable.up()).isOf(Blocks.AIR)) {
                mutable.move(Direction.UP);
            }

            double relativeSize = ((double) (Math.min(i, length - i) * 2) / length);

            for(BlockPos blockPos : BlockPos.iterate(mutable.toImmutable().add(-widthX, -depth, -widthZ), mutable.toImmutable().add(widthX, 0, widthZ))) {

                int distanceHorizontal = onXAxis ? Math.abs(mutable.getZ() - blockPos.getZ()) : Math.abs(mutable.getX() - blockPos.getX());
                int distanceVertical = Math.abs(mutable.getY() - blockPos.getY());
                double widthPercentage = (double) distanceHorizontal / (width * relativeSize);
                double depthPercentage = (double) distanceVertical / (depth * relativeSize);
                double relativeDistance = (widthPercentage + depthPercentage);

                if (relativeDistance >= geodeLayerThicknessConfig.outerLayer || relativeSize <= 0.35) {
                    // skip
                } else if (relativeDistance >= geodeLayerThicknessConfig.middleLayer) {
                    this.setBlockStateIf(world, blockPos, geodeLayerConfig.outerLayerProvider.get(random, blockPos), predicate);
                } else if (relativeDistance >= geodeLayerThicknessConfig.innerLayer) {
                    this.setBlockStateIf(world, blockPos, geodeLayerConfig.middleLayerProvider.get(random, blockPos), predicate);
                } else if (relativeDistance >= geodeLayerThicknessConfig.filling) {
                    boolean bl2 = (double)random.nextFloat() < crackFeatureConfig.buddingChance();
                    if (bl2) {
                        this.setBlockStateIf(world, blockPos, geodeLayerConfig.alternateInnerLayerProvider.get(random, blockPos), predicate);
                        list.add(blockPos.toImmutable());
                    } else {
                        this.setBlockStateIf(world, blockPos, geodeLayerConfig.innerLayerProvider.get(random, blockPos), predicate);
                    }
                } else {
                    this.setBlockStateIf(world, blockPos, geodeLayerConfig.fillingProvider.get(random, blockPos), predicate);
                }
            }

            for(BlockPos blockPos2 : list) {
                if (world.getBlockState(blockPos2).isIn(BlockTags.GEODE_INVALID_BLOCKS) || world.getBlockState(blockPos2).isOf(Blocks.AIR)) break;
                BlockState blockState = Util.getRandom(list2, random);

                for(Direction direction2 : Direction.values()) {
                    if (blockState.contains(Properties.FACING)) {
                        blockState = blockState.with(Properties.FACING, direction2);
                    }

                    BlockPos blockPos3 = blockPos2.offset(direction2);
                    BlockState blockState2 = world.getBlockState(blockPos3);
                    if (BuddingAmethystBlock.canGrowIn(blockState2)) {
                        this.setBlockState(world, blockPos3, blockState);
                        break;
                    }
                }
            }

            if (random.nextFloat() < crackFeatureConfig.bendChance()) {
                mutable.move(direction.rotateYClockwise());
            }

            mutable.move(direction);
        }


        return true;
    }
}