package com.lankaster.pyrellium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class ChainFenceBlock extends PaneBlock {
    public static final BooleanProperty TOP = BooleanProperty.of("top");

    public ChainFenceBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(TOP, true));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            tickView.scheduleBlockTick(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random).with(TOP, world.getBlockState(pos.up()).isAir());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TOP, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}