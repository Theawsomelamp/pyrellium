package com.lankaster.pyrellium.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

public class HangingVinesBlock extends Block implements BonemealableBlock {
    public static final BooleanProperty TIP = BooleanProperty.create("tip");

    public HangingVinesBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()).setValue(TIP, true));
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return this.canPlaceAt(world, pos);
    }

    private boolean canPlaceAt(BlockGetter world, BlockPos pos) {
        BlockPos blockPos = pos.relative(Direction.UP);
        BlockState blockState = world.getBlockState(blockPos);
        return MultifaceBlock.canAttachTo(world, Direction.UP, blockPos, blockState) || blockState.is(this.asBlock());
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }

        return state.setValue(TIP, !world.getBlockState(pos.below()).is(this));
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!this.canPlaceAt(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return this.canGrowInto(world.getBlockState(this.getTipPos(world, pos).below()));
    }

    private boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    public BlockPos getTipPos(BlockGetter world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();

        BlockState blockState;
        do {
            mutable.move(Direction.DOWN);
            blockState = world.getBlockState(mutable);
        } while(blockState.is(this));

        return mutable.relative(Direction.UP).immutable();
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockPos = this.getTipPos(world, pos).below();
        if (this.canGrowInto(world.getBlockState(blockPos))) {
            world.setBlockAndUpdate(blockPos, state.setValue(TIP, true));
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP);
    }
}
