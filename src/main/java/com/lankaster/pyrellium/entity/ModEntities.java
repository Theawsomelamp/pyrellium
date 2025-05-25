package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.Pyrellium;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<CrystalArrowEntity> CRYSTAL_ARROW = Registry.register(Registries.ENTITY_TYPE, new Identifier(Pyrellium.MOD_ID, "crystal_arrow"),
            FabricEntityTypeBuilder.<CrystalArrowEntity>create(SpawnGroup.MISC, CrystalArrowEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());
    public static final EntityType<BombFlowerEntity> BOMB_FLOWER = Registry.register(Registries.ENTITY_TYPE, new Identifier(Pyrellium.MOD_ID, "bomb_flower"),
            FabricEntityTypeBuilder.<BombFlowerEntity>create(SpawnGroup.MISC, BombFlowerEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).build());
    public static final EntityType<ModBoatEntity> BURNING_BOAT = Registry.register(Registries.ENTITY_TYPE, new Identifier(Pyrellium.MOD_ID, "burning_boat"),
            FabricEntityTypeBuilder.<ModBoatEntity>create(SpawnGroup.MISC, ModBoatEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).build());
    public static final EntityType<ModChestBoatEntity> BURNING_CHEST_BOAT = Registry.register(Registries.ENTITY_TYPE, new Identifier(Pyrellium.MOD_ID, "burning_chest_boat"),
            FabricEntityTypeBuilder.<ModChestBoatEntity>create(SpawnGroup.MISC, ModChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).build());

    public static void registerEntities() {

    }
}
