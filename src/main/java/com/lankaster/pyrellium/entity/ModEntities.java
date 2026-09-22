package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.Pyrellium;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModEntities {
    public static final EntityType<CrystalArrowEntity> CRYSTAL_ARROW = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "crystal_arrow"),
            EntityType.Builder.<CrystalArrowEntity>of(CrystalArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build());
    public static final EntityType<BombFlowerEntity> BOMB_FLOWER = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "bomb_flower"),
            EntityType.Builder.<BombFlowerEntity>of(BombFlowerEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build());
    public static final EntityType<ModBoatEntity> BURNING_BOAT = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "burning_boat"),
            EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build());
    public static final EntityType<ModChestBoatEntity> BURNING_CHEST_BOAT = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "burning_chest_boat"),
            EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build());
    public static final EntityType<GeodinEntity> GEODIN = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "geodin"),
            EntityType.Builder.<GeodinEntity>of(GeodinEntity::new, MobCategory.CREATURE).sized(0.95F, 1.15F).build());

    public static void registerEntities() {
        FabricDefaultAttributeRegistry.register(ModEntities.GEODIN, GeodinEntity.createGeodinAttributes());

        EntityDataSerializers.registerSerializer(GeodinEntity.GEODIN_VARIANT_IDENTIFIER);
    }
}
