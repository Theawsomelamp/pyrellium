package com.lankaster.pyrellium.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WallMushroomBlock extends HorizontalFacingBlock implements Fertilizable {
    public static final MapCodec<WallMushroomBlock> CODEC = createCodec(WallMushroomBlock::new);
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(5.0F, 4.0F, 0.0F, 11.0F, 13.0F, 7.0F), Direction.SOUTH, Block.createCuboidShape(5.0F, 4.0F, 9.0F, 11.0F, 13.0F, 16.0F), Direction.WEST, Block.createCuboidShape(0.0F, 4.0F, 5.0F, 7.0F, 13.0F, 11.0F), Direction.EAST, Block.createCuboidShape(9.0F, 4.0F, 5.0F, 16.0F, 13.0F, 11.0F)));

    public WallMushroomBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getBoundingShape(state);
    }

    public static VoxelShape getBoundingShape(BlockState state) {
        return BOUNDING_SHAPES.get(state.get(FACING));
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAt(world, pos, state.get(FACING).getOpposite());
    }

    public static boolean canPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction.getOpposite());
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        for(Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                return null;
            } else {
                blockState = this.getDefaultState().with(FACING, direction);
            }

            if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                return blockState;
            }
        }

        return null;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return state.get(FACING) == direction && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int j = 1;

        Direction direction = state.get(FACING);
        for (int i = 0; i < j; i++) {
            BlockPos blockPos = pos.offset(direction.rotateYClockwise(), random.nextBetween(-1, 1));
            BlockPos blockPos2 = blockPos.offset(Direction.UP, random.nextBetween(-1, 1));
            BlockPos posBack = blockPos2.offset(direction);
            BlockState blockState = world.getBlockState(blockPos2);
            BlockState backState = world.getBlockState(posBack);
            if (blockState.isAir() && backState.isSideSolidFullSquare(world, posBack, direction)) {
                world.setBlockState(blockPos2, getDefaultState().with(FACING, direction));
            } else {
                if (j <= 8) j++;
            }

        }
    }
}
