package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<CrystalArrowEntity> CRYSTAL_ARROW = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "crystal_arrow"),
            EntityType.Builder.<CrystalArrowEntity>create(CrystalArrowEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).build());
    public static final EntityType<BombFlowerEntity> BOMB_FLOWER = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "bomb_flower"),
            EntityType.Builder.<BombFlowerEntity>create(BombFlowerEntity::new, SpawnGroup.MISC).dimensions(0.25F, 0.25F).build());
    public static final EntityType<ModBoatEntity> BURNING_BOAT = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "burning_boat"),
            EntityType.Builder.<ModBoatEntity>create(ModBoatEntity::new, SpawnGroup.MISC).dimensions(1.375F, 0.5625F).build());
    public static final EntityType<ModChestBoatEntity> BURNING_CHEST_BOAT = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Pyrellium.MOD_ID, "burning_chest_boat"),
            EntityType.Builder.<ModChestBoatEntity>create(ModChestBoatEntity::new, SpawnGroup.MISC).dimensions(1.375F, 0.5625F).build());

    public static void registerEntities() {

    }
}
