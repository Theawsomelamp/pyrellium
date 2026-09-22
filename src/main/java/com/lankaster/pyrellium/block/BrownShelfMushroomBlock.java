package com.lankaster.pyrellium.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

import java.util.Map;

public class BrownShelfMushroomBlock extends WallMushroomBlock{
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0.0F, 7.5F, 0.0F, 16.0F, 8.5F, 8.0F), Direction.SOUTH, Block.box(0.0F, 7.5F, 8.0F, 16.0F, 8.50F, 16.0F), Direction.WEST, Block.box(0.0F, 7.5F, 0.0F, 8.0F, 8.5F, 16.0F), Direction.EAST, Block.box(8.0F, 7.5F, 0.0F, 16.0F, 8.5F, 16.0F)));


    public BrownShelfMushroomBlock(Properties settings) {
        super(settings);
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getBoundingShape(state);
    }

    public static VoxelShape getBoundingShape(BlockState state) {
        return BOUNDING_SHAPES.get(state.getValue(FACING));
    }
}
