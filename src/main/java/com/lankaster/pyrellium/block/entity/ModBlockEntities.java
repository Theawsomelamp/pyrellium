package com.lankaster.pyrellium.block.entity;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModBlockEntities {
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Pyrellium.MOD_ID);

    Supplier<BlockEntityType<HeadStoneBlockEntity>> HEADSTONE = BLOCK_ENTITIES.register("headstone", () -> BlockEntityType.Builder.of(HeadStoneBlockEntity::new, ModBlocks.HEADSTONE.get()).build(null));
    Supplier<BlockEntityType<GeodinBlockEntity>> GEODIN = BLOCK_ENTITIES.register("geodin", () -> BlockEntityType.Builder.of(GeodinBlockEntity::new, ModBlocks.SLEEPING_AMETHYST_GEODIN.get(), ModBlocks.SLEEPING_OPAL_GEODIN.get(), ModBlocks.SLEEPING_QUARTZ_GEODIN.get()).build(null));

    @SubscribeEvent
    static void setupBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, ModBlocks.BURNING_SIGN.get(), ModBlocks.BURNING_WALL_SIGN.get(), ModBlocks.SHADEROOT_SIGN.get(), ModBlocks.SHADEROOT_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, ModBlocks.BURNING_HANGING_SIGN.get(), ModBlocks.BURNING_WALL_HANGING_SIGN.get(), ModBlocks.SHADEROOT_HANGING_SIGN.get(), ModBlocks.SHADEROOT_WALL_HANGING_SIGN.get());
    }

    static void registerBlockEntities(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}