package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class ModEntities {
    private static final RegistryKey<EntityType<?>> CRYSTAL_ARROW_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "crystal_arrow"));
    private static final RegistryKey<EntityType<?>> BOMB_FLOWER_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "bomb_flower"));
    private static final RegistryKey<EntityType<?>> BURNING_BOAT_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "burning_boat"));
    private static final RegistryKey<EntityType<?>> BURNING_CHEST_BOAT_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "burning_chest_boat"));
    private static final RegistryKey<EntityType<?>> SHADEROOT_BOAT_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "shaderoot_boat"));
    private static final RegistryKey<EntityType<?>> SHADEROOT_CHEST_BOAT_KEY = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "shaderoot_chest_boat"));


    private static EntityType.EntityFactory<BoatEntity> getBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new BoatEntity(type, world, itemSupplier);
    }

    private static EntityType.EntityFactory<ChestBoatEntity> getChestBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new ChestBoatEntity(type, world, itemSupplier);
    }

    public static final EntityType<CrystalArrowEntity> CRYSTAL_ARROW = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "crystal_arrow"),
            EntityType.Builder.<CrystalArrowEntity>create(CrystalArrowEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).build(CRYSTAL_ARROW_KEY));
    public static final EntityType<BombFlowerEntity> BOMB_FLOWER = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "bomb_flower"),
            EntityType.Builder.<BombFlowerEntity>create(BombFlowerEntity::new, SpawnGroup.MISC).dimensions(0.25F, 0.25F).build(BOMB_FLOWER_KEY));
    public static final EntityType<BoatEntity> BURNING_BOAT = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "burning_boat"),
            EntityType.Builder.create(getBoatFactory(() -> ModBlocks.BURNING_BOAT), SpawnGroup.MISC).dimensions(1.375F, 0.5625F).build(BURNING_BOAT_KEY));
    public static final EntityType<ChestBoatEntity> BURNING_CHEST_BOAT = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "burning_chest_boat"),
            EntityType.Builder.create(getChestBoatFactory(() -> ModBlocks.BURNING_CHEST_BOAT), SpawnGroup.MISC).dimensions(1.375F, 0.5625F).build(BURNING_CHEST_BOAT_KEY));
    public static final EntityType<BoatEntity> SHADEROOT_BOAT = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "shaderoot_boat"),
            EntityType.Builder.create(getBoatFactory(() -> ModBlocks.SHADEROOT_BOAT), SpawnGroup.MISC).dimensions(1.375F, 0.5625F).build(SHADEROOT_BOAT_KEY));
    public static final EntityType<ChestBoatEntity> SHADEROOT_CHEST_BOAT = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "shaderoot_chest_boat"),
            EntityType.Builder.create(getChestBoatFactory(() -> ModBlocks.SHADEROOT_CHEST_BOAT), SpawnGroup.MISC).dimensions(1.375F, 0.5625F).build(SHADEROOT_CHEST_BOAT_KEY));

    public static void registerEntities() {

    }
}
