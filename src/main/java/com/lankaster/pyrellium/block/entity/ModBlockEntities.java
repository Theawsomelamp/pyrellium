package com.lankaster.pyrellium.block.entity;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface ModBlockEntities {
    BlockEntityType<HeadStoneBlockEntity> HEADSTONE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "headstone"), BlockEntityType.Builder.create(HeadStoneBlockEntity::new, ModBlocks.HEADSTONE).build());

    static void registerBlockEntities() {
    }
}