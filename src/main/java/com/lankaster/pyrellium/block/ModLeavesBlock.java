package com.lankaster.pyrellium.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

import java.util.OptionalInt;

public class ModLeavesBlock extends LeavesBlock {
    public final int maxDistance;
    public static final IntegerProperty TRUE_DISTANCE = IntegerProperty.create("true_distance", 1, 24);

    public ModLeavesBlock(Properties settings, int maxDistance){
        super(settings);
        this.maxDistance = maxDistance;
        this.registerDefaultState(this.stateDefinition.any().setValue(TRUE_DISTANCE, maxDistance));
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        world.setBlock(pos, updateDistanceFromLogs(state, world, pos), 3);
    }

    protected BlockState updateDistanceFromLogs(BlockState state, LevelAccessor world, BlockPos pos) {
        int i = maxDistance;
        int j = 7;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.values()) {
            mutable.setWithOffset(pos, direction);
            i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
            if (i == 1) {
                break;
            } else if (i < maxDistance) {
                j = (Math.min(i, 6));
            }
        }

        return state.setValue(DISTANCE, j).setValue(TRUE_DISTANCE, i);
    }

    private int getDistanceFromLog(BlockState state) {
        return getOptionalDistanceFromLog(state).orElse(maxDistance);
    }

    public static OptionalInt getOptionalDistanceFromLog(BlockState state) {
        if (state.is(BlockTags.LOGS)) {
            return OptionalInt.of(0);
        } else if (state.hasProperty(TRUE_DISTANCE)) {
            return OptionalInt.of(state.getValue(TRUE_DISTANCE));
        } else {
            return state.hasProperty(DISTANCE) ? OptionalInt.of(state.getValue(DISTANCE)) : OptionalInt.empty();
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        BlockState blockState = this.defaultBlockState().setValue(PERSISTENT, true).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        return updateDistanceFromLogs(blockState, ctx.getLevel(), ctx.getClickedPos());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TRUE_DISTANCE);
    }
}