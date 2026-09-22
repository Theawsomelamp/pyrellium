package com.lankaster.pyrellium.block.entity;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public interface ModBlockEntities {
    BlockEntityType<HeadStoneBlockEntity> HEADSTONE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "headstone"), BlockEntityType.Builder.of(HeadStoneBlockEntity::new, ModBlocks.HEADSTONE).build());
    BlockEntityType<GeodinBlockEntity> GEODIN = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "geodin"), BlockEntityType.Builder.of(GeodinBlockEntity::new, ModBlocks.SLEEPING_AMETHYST_GEODIN, ModBlocks.SLEEPING_OPAL_GEODIN, ModBlocks.SLEEPING_QUARTZ_GEODIN).build());

    static void registerBlockEntities() {
    }
}