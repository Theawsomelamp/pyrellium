package com.lankaster.pyrellium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SporesBlock extends Block {
    public static final BooleanProperty DISPERSED = BooleanProperty.of("dispersed");

    public SporesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(DISPERSED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);
        if (itemStack.isOf(Items.GLASS_BOTTLE)) {
            itemStack.decrement(1);
            if (itemStack.isEmpty()) {
                player.setStackInHand(Hand.MAIN_HAND, new ItemStack(ModBlocks.SPORES));
            } else if (!player.getInventory().insertStack(new ItemStack(ModBlocks.SPORES))) {
                player.dropItem(new ItemStack(ModBlocks.SPORES), false);
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_FROGSPAWN_BREAK, SoundCategory.BLOCKS);
        }
        return super.onUse(state, world, pos, player, hit);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!state.get(DISPERSED)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100));
            }
            world.setBlockState(pos, state.cycle(DISPERSED), 2);
            world.scheduleBlockTick(pos, this, 200);
            for (int i = 0; i < 20; ++i) {
                world.addParticle(ParticleTypes.WARPED_SPORE, entity.getX(), pos.getY() + 1, entity.getZ(), MathHelper.nextBetween(world.getRandom(), -1.0F, 1.0F) * 0.083333336F, 0.05F, MathHelper.nextBetween(world.getRandom(), -1.0F, 1.0F) * 0.083333336F);
            }
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(DISPERSED)) {
            world.setBlockState(pos, state.cycle(DISPERSED), 2);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DISPERSED);
    }
}
