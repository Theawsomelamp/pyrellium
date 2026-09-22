package com.lankaster.pyrellium.block;

import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

public class BuddingBlock extends BuddingAmethystBlock {
    private static final Direction[] DIRECTIONS = Direction.values();
    private final Block smallBud;
    private final Block mediumBud;
    private final Block largeBud;
    private final Block cluster;

    public BuddingBlock(BlockBehaviour.Properties settings, Block smallBud, Block mediumBud, Block largeBud, Block cluster) {
        super(settings);
        this.smallBud = smallBud;
        this.mediumBud = mediumBud;
        this.largeBud = largeBud;
        this.cluster = cluster;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction.getNormal());
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (canClusterGrowAtState(blockState)) {
                block = smallBud;
            } else if (blockState.is(smallBud) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = mediumBud;
            } else if (blockState.is(mediumBud) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = largeBud;
            } else if (blockState.is(largeBud) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = cluster;
            }

            if (block != null) {
                BlockState blockState2 = (BlockState)((BlockState)block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction)).setValue(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getType() == Fluids.WATER);
                world.setBlockAndUpdate(blockPos, blockState2);
            }

        }
    }
}
