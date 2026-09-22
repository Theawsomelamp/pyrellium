package com.lankaster.pyrellium.block;

import com.google.gson.JsonSyntaxException;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class SporesBlock extends Block {
    public static final BooleanProperty DISPERSED = BooleanProperty.create("dispersed");

    public SporesBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISPERSED, false));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.is(Items.GLASS_BOTTLE)) {
            itemStack.shrink(1);
            if (itemStack.isEmpty()) {
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModBlocks.SPORES));
            } else if (!player.getInventory().add(new ItemStack(ModBlocks.SPORES))) {
                player.drop(new ItemStack(ModBlocks.SPORES), false);
            }
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            world.playSound(null, pos, SoundEvents.FROGSPAWN_BREAK, SoundSource.BLOCKS);
        }
        return super.useWithoutItem(state, world, pos, player, hit);
    }

    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!state.getValue(DISPERSED)) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MUSHROOM_CAP)) {
                    Optional<MobEffect> effect = BuiltInRegistries.MOB_EFFECT.getOptional(ResourceLocation.tryParse(Config.instance().blocks.spores_effect));
                    if(effect.isEmpty()) {
                        throw new JsonSyntaxException("Error reading status effect: could not find status effect with id: " + Config.instance().blocks.spores_effect);
                    }
                    livingEntity.addEffect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect.get()), Config.instance().blocks.spores_effect_time));
                }
            }
            world.setBlock(pos, state.cycle(DISPERSED), 2);
            world.scheduleTick(pos, this, 200);
            for (int i = 0; i < 20; ++i) {
                world.addParticle(ParticleTypes.WARPED_SPORE, entity.getX(), pos.getY() + 1, entity.getZ(), Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083333336F, 0.05F, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083333336F);
            }
        }
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(DISPERSED)) {
            world.setBlock(pos, state.cycle(DISPERSED), 2);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISPERSED);
    }
}
