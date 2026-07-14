package com.lankaster.pyrellium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class GeodinBlock extends Block {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F);
    public static final IntProperty AGE = IntProperty.of("age", 0, 4);
    private final Block smallBud;
    private final Block mediumBud;
    private final Block largeBud;
    private final Block cluster;


    public GeodinBlock(Settings settings, Block smallBud, Block mediumBud, Block largeBud, Block cluster) {
        super(settings);
        this.smallBud = smallBud;
        this.mediumBud = mediumBud;
        this.largeBud = largeBud;
        this.cluster = cluster;
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 4;
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i < 4 && random.nextInt(5) == 0) {
            BlockState blockState = state.with(AGE, i + 1);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
        }
    }

    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isIn(ItemTags.PICKAXES)) {
            int age = state.get(AGE);
            if (age >= 1) {
                dropStacks(getBlockFromAge(age).getDefaultState(), world, pos, null, null, player.getStackInHand(hand));
                world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.BLOCKS);
                world.setBlockState(pos, state.with(AGE, 0));
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        int age = state.get(AGE);
        dropStacks(getBlockFromAge(age).getDefaultState(), world, pos, null, null, player.getStackInHand(Hand.MAIN_HAND));
    }

    private Block getBlockFromAge(int age) {
        return switch (age) {
            case 1 -> smallBud;
            case 2 -> mediumBud;
            case 3 -> largeBud;
            case 4 -> cluster;
            default -> Blocks.AIR;
        };
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
