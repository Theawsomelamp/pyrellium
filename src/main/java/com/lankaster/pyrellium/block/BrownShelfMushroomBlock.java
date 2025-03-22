package com.lankaster.pyrellium.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Map;

public class BrownShelfMushroomBlock extends WallMushroomBlock{
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(0.0F, 7.5F, 0.0F, 16.0F, 8.5F, 8.0F), Direction.SOUTH, Block.createCuboidShape(0.0F, 7.5F, 8.0F, 16.0F, 8.50F, 16.0F), Direction.WEST, Block.createCuboidShape(0.0F, 7.5F, 0.0F, 8.0F, 8.5F, 16.0F), Direction.EAST, Block.createCuboidShape(8.0F, 7.5F, 0.0F, 16.0F, 8.5F, 16.0F)));


    public BrownShelfMushroomBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getBoundingShape(state);
    }

    public static VoxelShape getBoundingShape(BlockState state) {
        return BOUNDING_SHAPES.get(state.get(FACING));
    }
}
