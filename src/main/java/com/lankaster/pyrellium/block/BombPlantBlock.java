package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;

public class BombPlantBlock extends BushBlock implements BonemealableBlock {
    public static final MapCodec<BombPlantBlock> CODEC = simpleCodec(BombPlantBlock::new);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    public BombPlantBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()).setValue(AGE, 0));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.isSolidRender(world, pos);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int i = state.getValue(AGE);
        if (i < 3 && random.nextInt(5) == 0) {
            BlockState blockState = state.setValue(AGE, i + 1);
            world.setBlock(pos, blockState, 2);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "bomb_plant_safe")))) return;
        if (state.getValue(AGE) > 0) {
            world.explode(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, Config.instance().blocks.bomb_plant_explosion_strength, false, Level.ExplosionInteraction.NONE);
            world.setBlockAndUpdate(pos, state.setValue(AGE, 0));
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if ((!EnchantmentHelper.hasTag(player.getItemInHand(InteractionHand.MAIN_HAND), EnchantmentTags.PREVENTS_DECORATED_POT_SHATTERING) && !player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.SHEARS) && !player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.BONE_MEAL)) && state.getValue(AGE) > 0) {
            world.explode(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, Config.instance().blocks.bomb_plant_explosion_strength, false, Level.ExplosionInteraction.NONE);
            world.setBlockAndUpdate(pos, state.setValue(AGE, 0));
        } else if (state.getValue(AGE) == 3) {
            popResource(world, pos, new ItemStack(ModItems.BOMB_FLOWER.get(), 1));
            world.setBlockAndUpdate(pos, state.setValue(AGE, 0));
        }
        return super.useWithoutItem(state, world, pos, player, hit);
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!EnchantmentHelper.hasTag(player.getItemInHand(InteractionHand.MAIN_HAND), EnchantmentTags.PREVENTS_DECORATED_POT_SHATTERING) && !player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.SHEARS) && state.getValue(AGE) > 0) {
            world.explode(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, Config.instance().blocks.bomb_plant_explosion_strength, false, Level.ExplosionInteraction.NONE);
            world.setBlockAndUpdate(pos, state.setValue(AGE, 0));
        }
        super.playerWillDestroy(world, pos, state, player);
        return state;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return state.getValue(AGE) < 3;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        int i = Math.min(3, state.getValue(AGE) + 1);
        world.setBlock(pos, state.setValue(AGE, i), 2);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
