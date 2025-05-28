package com.lankaster.pyrellium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class HangingVinesBlock extends Block implements Fertilizable {
    public static final BooleanProperty TIP = BooleanProperty.of("tip");

    public HangingVinesBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(TIP, true));
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return this.canPlaceAt(world, pos);
    }

    private boolean canPlaceAt(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.offset(Direction.UP);
        BlockState blockState = world.getBlockState(blockPos);
        return MultifaceGrowthBlock.canGrowOn(world, Direction.UP, blockPos, blockState) || blockState.isOf(ModBlocks.HANGING_SILK);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }

        return state.with(TIP, !world.getBlockState(pos.down()).isOf(this));
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return this.canGrowInto(world.getBlockState(this.getTipPos(world, pos).down()));
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    public BlockPos getTipPos(BlockView world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        BlockState blockState;
        do {
            mutable.move(Direction.DOWN);
            blockState = world.getBlockState(mutable);
        } while(blockState.isOf(this));

        return mutable.offset(Direction.UP).toImmutable();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = this.getTipPos(world, pos).down();
        if (this.canGrowInto(world.getBlockState(blockPos))) {
            world.setBlockState(blockPos, state.with(TIP, true));
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TIP);
    }
}
