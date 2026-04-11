package com.lankaster.pyrellium.block.entity;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface ModBlockEntities {
    BlockEntityType<HeadStoneBlockEntity> HEADSTONE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "headstone"), FabricBlockEntityTypeBuilder.create(HeadStoneBlockEntity::new, ModBlocks.HEADSTONE).build());

    static void registerBlockEntities() {
    }
}