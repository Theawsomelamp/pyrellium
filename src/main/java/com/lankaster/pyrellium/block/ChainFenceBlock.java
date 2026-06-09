package com.lankaster.pyrellium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class ChainFenceBlock extends PaneBlock {
    public static final BooleanProperty TOP = BooleanProperty.of("top");

    public ChainFenceBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(TOP, true));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(TOP, world.getBlockState(pos.up()).isAir());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TOP, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}
