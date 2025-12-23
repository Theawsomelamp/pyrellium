package com.lankaster.pyrellium.block.entity;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface ModBlockEntities {
    BlockEntityType<ModSignBlockEntity> PYRELLIUM_SIGN = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Pyrellium.MOD_ID, "signs"), FabricBlockEntityTypeBuilder.create(ModSignBlockEntity::new).addBlocks(ModBlocks.BURNING_SIGN, ModBlocks.BURNING_WALL_SIGN, ModBlocks.SHADEROOT_SIGN, ModBlocks.SHADEROOT_WALL_SIGN).build());
    BlockEntityType<ModHangingSignBlockEntity> PYRELLIUM_HANGING_SIGN = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Pyrellium.MOD_ID, "hanging_signs"), FabricBlockEntityTypeBuilder.create(ModHangingSignBlockEntity::new).addBlocks(ModBlocks.BURNING_HANGING_SIGN, ModBlocks.BURNING_WALL_HANGING_SIGN, ModBlocks.SHADEROOT_HANGING_SIGN, ModBlocks.SHADEROOT_WALL_HANGING_SIGN).build());
    BlockEntityType<HeadStoneBlockEntity> HEADSTONE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Pyrellium.MOD_ID, "headstone"), FabricBlockEntityTypeBuilder.create(HeadStoneBlockEntity::new).addBlocks(ModBlocks.HEADSTONE).build());

    static void registerBlockEntities() {
    }
}
