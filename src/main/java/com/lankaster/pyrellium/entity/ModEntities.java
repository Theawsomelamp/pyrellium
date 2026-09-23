package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Pyrellium.MOD_ID);
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, Pyrellium.MOD_ID);

    public static final Supplier<EntityType<CrystalArrowEntity>> CRYSTAL_ARROW = ENTITY_TYPES.register("crystal_arrow", () ->
            EntityType.Builder.<CrystalArrowEntity>of(CrystalArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build("crystal_arrow"));
    public static final Supplier<EntityType<BombFlowerEntity>> BOMB_FLOWER = ENTITY_TYPES.register("bomb_flower", () ->
            EntityType.Builder.<BombFlowerEntity>of(BombFlowerEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build("bomb_flower"));
    public static final Supplier<EntityType<ModBoatEntity>> BURNING_BOAT = ENTITY_TYPES.register("burning_boat", () ->
            EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build("burning_boat"));
    public static final Supplier<EntityType<ModChestBoatEntity>> BURNING_CHEST_BOAT = ENTITY_TYPES.register("burning_chest_boat", () ->
            EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build("burning_chest_boat"));
    public static final Supplier<EntityType<GeodinEntity>> GEODIN = ENTITY_TYPES.register("geodin", () ->
            EntityType.Builder.<GeodinEntity>of(GeodinEntity::new, MobCategory.CREATURE).sized(0.95F, 1.15F).build("geodin"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GEODIN.get(), GeodinEntity.createGeodinAttributes().build());
    }

    public static void registerEntities(IEventBus eventBus) {
        ENTITY_DATA_SERIALIZERS.register("geodin_variant_identifier", () -> GeodinEntity.GEODIN_VARIANT_IDENTIFIER);
        ENTITY_DATA_SERIALIZERS.register(eventBus);
        ENTITY_TYPES.register(eventBus);
    }
}
