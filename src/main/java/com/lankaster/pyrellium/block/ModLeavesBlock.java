package com.lankaster.pyrellium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

import java.util.OptionalInt;

public class ModLeavesBlock extends LeavesBlock {
    public final int maxDistance;
    public static final IntProperty TRUE_DISTANCE = IntProperty.of("true_distance", 1, 24);

    public ModLeavesBlock(Settings settings, int maxDistance){
        super(settings);
        this.maxDistance = maxDistance;
        this.setDefaultState(this.stateManager.getDefaultState().with(TRUE_DISTANCE, maxDistance));
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
    }

    protected BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
        int i = maxDistance;
        int j = 7;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(Direction direction : Direction.values()) {
            mutable.set(pos, direction);
            i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
            if (i == 1) {
                break;
            } else if (i < maxDistance) {
                j = (Math.min(i, 6));
            }
        }

        return state.with(DISTANCE, j).with(TRUE_DISTANCE, i);
    }

    private int getDistanceFromLog(BlockState state) {
        return getOptionalDistanceFromLog(state).orElse(maxDistance);
    }

    public static OptionalInt getOptionalDistanceFromLog(BlockState state) {
        if (state.isIn(BlockTags.LOGS)) {
            return OptionalInt.of(0);
        } else if (state.contains(TRUE_DISTANCE)) {
            return OptionalInt.of(state.get(TRUE_DISTANCE));
        } else {
            return state.contains(DISTANCE) ? OptionalInt.of(state.get(DISTANCE)) : OptionalInt.empty();
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockState blockState = this.getDefaultState().with(PERSISTENT, true).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return updateDistanceFromLogs(blockState, ctx.getWorld(), ctx.getBlockPos());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(TRUE_DISTANCE);
    }
}
