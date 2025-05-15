package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.config.ConfigHandler;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class BombPlantBlock extends PlantBlock implements Fertilizable {
    public static final MapCodec<BombPlantBlock> CODEC = createCodec(BombPlantBlock::new);
    public static final IntProperty AGE = IntProperty.of("age", 0, 3);

    public BombPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(AGE, 0));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOpaqueFullCube(world, pos);
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i < 3 && random.nextInt(5) == 0) {
            BlockState blockState = state.with(AGE, i + 1);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (state.get(AGE) > 0) {
            world.createExplosion(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, ConfigHandler.getConfig().blocksConfig().explodeStrength(), false, World.ExplosionSourceType.NONE);
            world.setBlockState(pos, state.with(AGE, 0));
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if ((!EnchantmentHelper.hasAnyEnchantmentsIn(player.getStackInHand(Hand.MAIN_HAND), EnchantmentTags.PREVENTS_DECORATED_POT_SHATTERING) && !player.getStackInHand(Hand.MAIN_HAND).isOf(Items.BONE_MEAL)) && state.get(AGE) > 0) {
            world.createExplosion(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, ConfigHandler.getConfig().blocksConfig().explodeStrength(), false, World.ExplosionSourceType.NONE);
            world.setBlockState(pos, state.with(AGE, 0));
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!EnchantmentHelper.hasAnyEnchantmentsIn(player.getStackInHand(Hand.MAIN_HAND), EnchantmentTags.PREVENTS_DECORATED_POT_SHATTERING) && state.get(AGE) > 0) {
            world.createExplosion(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, ConfigHandler.getConfig().blocksConfig().explodeStrength(), false, World.ExplosionSourceType.NONE);
            world.setBlockState(pos, state.with(AGE, 0));
        }
        super.onBreak(world, pos, state, player);
        return state;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(AGE) < 3;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(3, state.get(AGE) + 1);
        world.setBlockState(pos, state.with(AGE, i), 2);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
