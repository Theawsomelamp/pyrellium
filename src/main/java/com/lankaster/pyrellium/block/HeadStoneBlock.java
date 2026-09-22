package com.lankaster.pyrellium.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.lankaster.pyrellium.block.entity.HeadStoneBlockEntity;
import com.lankaster.pyrellium.block.entity.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class HeadStoneBlock extends SignBlock {
    public static final MapCodec<HeadStoneBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(WoodType.CODEC.fieldOf("wood_type").forGetter(HeadStoneBlock::getWoodTypeButMoreStupid), propertiesCodec()).apply(instance, HeadStoneBlock::new));
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0.0F, 0.0F, 13.0F, 16.0F, 16.0F, 16.0F), Direction.SOUTH, Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 3.0F), Direction.WEST, Block.box(13.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F), Direction.EAST, Block.box(0.0F, 0.0F, 0.0F, 3.0F, 16.0F, 16.0F)));
    public static final DirectionProperty FACING;

    protected HeadStoneBlock(WoodType type, Properties settings) {
        super(type, settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<HeadStoneBlock> codec() {
        return null;
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getBoundingShape(state);
    }

    public static VoxelShape getBoundingShape(BlockState state) {
        return BOUNDING_SHAPES.get(state.getValue(FACING));
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public float getYRotationDegrees(BlockState state) {
        return RotationSegment.convertToDegrees(RotationSegment.convertToSegment(state.getValue(FACING)));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        for(Direction direction : ctx.getNearestLookingDirections()) {
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockState = this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
            } else {
                blockState = this.defaultBlockState().setValue(FACING, direction.getOpposite());
            }

            if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
                return blockState;
            }
        }

        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HeadStoneBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.HEADSTONE, HeadStoneBlockEntity::tick);
    }

    public WoodType getWoodTypeButMoreStupid() {
        return type();
    }

    public WoodType type() {
        return super.type();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }
}