package com.lankaster.pyrellium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpikedBoneBlock extends WallMountedBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected SpikedBoneBlock(Settings settings) {
        super(settings);
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.get(FACE) == WallMountLocation.FLOOR) {
            entity.handleFallDamage(fallDistance + 2.0F, 2.0F, world.getDamageSources().stalagmite());
        } else {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        }

    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACE)) {
            case FLOOR -> Block.createCuboidShape(5.0F, 0.0F, 5.0F, 11.0F, 11.0F, 11.0F);
            case WALL -> switch (state.get(FACING)) {
                case WEST -> Block.createCuboidShape(5.0F, 5.0F, 5.0F, 16.0F, 11.0F, 11.0F);
                case EAST -> Block.createCuboidShape(0.0F, 5.0F, 5.0F, 11.0F, 11.0F, 11.0F);
                case SOUTH -> Block.createCuboidShape(5.0F, 5.0F, 0.0F, 11.0F, 11.0F, 11.0F);
                case NORTH, UP, DOWN -> Block.createCuboidShape(5.0F, 5.0F, 5.0F, 11.0F, 11.0F, 16.0F);
            };
            case CEILING -> Block.createCuboidShape(5.0F, 5.0F, 5.0F, 11.0F, 16.0F, 11.0F);
        };
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return super.getPlacementState(ctx) == null ? null : super.getPlacementState(ctx).with(WATERLOGGED, fluidState.isOf(Fluids.WATER));
    }

    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        builder.add(FACE);
        builder.add(FACING);
    }
}
