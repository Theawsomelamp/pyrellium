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

    static void registerBlockEntities(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}