package com.lankaster.pyrellium.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.lankaster.pyrellium.block.entity.HeadStoneBlockEntity;
import com.lankaster.pyrellium.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class HeadStoneBlock extends AbstractSignBlock {
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(0.0F, 0.0F, 13.0F, 16.0F, 16.0F, 16.0F), Direction.SOUTH, Block.createCuboidShape(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 3.0F), Direction.WEST, Block.createCuboidShape(13.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F), Direction.EAST, Block.createCuboidShape(0.0F, 0.0F, 0.0F, 3.0F, 16.0F, 16.0F)));
    public static final DirectionProperty FACING;

    protected HeadStoneBlock(Settings settings, WoodType type) {
        super(settings, type);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getBoundingShape(state);
    }

    public static VoxelShape getBoundingShape(BlockState state) {
        return BOUNDING_SHAPES.get(state.get(FACING));
    }

    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public float getRotationDegrees(BlockState state) {
        return RotationPropertyHelper.toDegrees(RotationPropertyHelper.fromDirection(state.get(FACING)));
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        for(Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockState = this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
            } else {
                blockState = this.getDefaultState().with(FACING, direction.getOpposite());
            }

            if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                return blockState;
            }
        }

        return null;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HeadStoneBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.HEADSTONE, HeadStoneBlockEntity::tick);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
    }
}
