package com.lankaster.pyrellium.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WallMushroomBlock extends HorizontalDirectionalBlock implements BonemealableBlock {
    public static final MapCodec<WallMushroomBlock> CODEC = simpleCodec(WallMushroomBlock::new);
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(5.0F, 4.0F, 0.0F, 11.0F, 13.0F, 7.0F), Direction.SOUTH, Block.box(5.0F, 4.0F, 9.0F, 11.0F, 13.0F, 16.0F), Direction.WEST, Block.box(0.0F, 4.0F, 5.0F, 7.0F, 13.0F, 11.0F), Direction.EAST, Block.box(9.0F, 4.0F, 5.0F, 16.0F, 13.0F, 11.0F)));

    public WallMushroomBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getBoundingShape(state);
    }

    public static VoxelShape getBoundingShape(BlockState state) {
        return BOUNDING_SHAPES.get(state.getValue(FACING));
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canPlaceAt(world, pos, state.getValue(FACING).getOpposite());
    }

    public static boolean canPlaceAt(LevelReader world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.relative(direction.getOpposite());
        return world.getBlockState(blockPos).isFaceSturdy(world, blockPos, direction);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        for(Direction direction : ctx.getNearestLookingDirections()) {
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                return null;
            } else {
                blockState = this.defaultBlockState().setValue(FACING, direction);
            }

            if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
                return blockState;
            }
        }

        return null;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return state.getValue(FACING) == direction && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        int j = 1;

        Direction direction = state.getValue(FACING);
        for (int i = 0; i < j; i++) {
            BlockPos blockPos = pos.relative(direction.getClockWise(), random.nextIntBetweenInclusive(-1, 1));
            BlockPos blockPos2 = blockPos.relative(Direction.UP, random.nextIntBetweenInclusive(-1, 1));
            BlockPos posBack = blockPos2.relative(direction);
            BlockState blockState = world.getBlockState(blockPos2);
            BlockState backState = world.getBlockState(posBack);
            if (blockState.isAir() && backState.isFaceSturdy(world, posBack, direction)) {
                world.setBlockAndUpdate(blockPos2, defaultBlockState().setValue(FACING, direction));
            } else {
                if (j <= 8) j++;
            }

        }
    }
}
