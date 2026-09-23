package com.lankaster.pyrellium;

import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.entity.ModEntities;
import com.lankaster.pyrellium.world.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

@EventBusSubscriber(modid = Pyrellium.MOD_ID)
public class PyrelliumDataGenerator {
    public static final ResourceKey<BiomeModifier> BIOME_MODIFIERS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "biome_modifiers"));
    public static final ResourceKey<BiomeModifier> SPAWN_MODIFIERS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "spawn_modifiers"));

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        new RegistrySetBuilder().add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
            HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);
            HolderGetter<PlacedFeature> placedFeatures = bootstrap.lookup(Registries.PLACED_FEATURE);

            if (Config.instance().globalFeatures.thicker_bedrock_ceiling) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_NETHER),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("thick_bedrock_ceiling"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_NETHER),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("bedrock_gradient"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
            }

            if (Config.instance().globalFeatures.opal_geodes) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_NETHER),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("opal_geode"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
            }

            if (Config.instance().globalFeatures.basalt_iron_ore) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_NETHER),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("basalt_iron_ore"))),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION
                ));
            }

            if (Config.instance().globalFeatures.lava_lake_additions) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("cool_lava_lake"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.GHOSTLY_WOODS)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("cool_lava_lake"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
            }

            if (Config.instance().globalFeatures.soul_sand_valley_bones) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("bones"))),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION
                ));
            }

            if (Config.instance().globalFeatures.nether_forest_fallen_logs) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.CRIMSON_FOREST)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("crimson_stems"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.WARPED_FOREST)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("warped_stems"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
            }

            if (Config.instance().globalFeatures.gilded_blackstone_patches) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.BLACKSTONE_SPRINGS)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("gilded_patch"))),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION
                ));

                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.BURNING_GROVE)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("gilded_patch"))),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION
                ));
            }

            if (Config.instance().biomes.mushroom_wastes.generate_spores) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.MUSHROOM_WASTES)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("spores"))),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));
            }

            if (Config.instance().biomes.mushroom_wastes.generate_wall_mushrooms) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.MUSHROOM_WASTES)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("wall_mushrooms"))),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));
            }

            if (Config.instance().biomes.monolith_plains.generate_bomb_flowers) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.MONOLITH_PLAINS)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("bomb_plants"))),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));
            }

            if (Config.instance().biomes.monolith_plains.generate_monolith) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.MONOLITH_PLAINS)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("obsidian_monolith"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
            }

            if (Config.instance().biomes.blackstone_springs.generate_blackstone_rocks) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.BLACKSTONE_SPRINGS)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("blackstone_rock_patch"))),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION
                ));
            }

            if (Config.instance().biomes.crystal_forest.generate_floor_crystals) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.CRYSTAL_FOREST)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("crystal_vegetation"))),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));
            }

            if (Config.instance().biomes.infested_valley.generate_floor_decorations) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.INFESTED_VALLEY)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("cobwebs"))),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION
                ));
            }

            if (Config.instance().biomes.infested_valley.generate_hanging_silk) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.INFESTED_VALLEY)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("hanging_silk"))),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION
                ));
            }

            if (Config.instance().biomes.quartz_caverns.generate_quartz_spikes) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.QUARTZ_CAVERNS)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("spike"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.QUARTZ_CAVERNS)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("spike_down"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
            }

            if (Config.instance().biomes.quartz_caverns.generate_quartz_cracks) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.QUARTZ_CAVERNS)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("quartz_crack"))),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
            }

            if (Config.instance().biomes.burning_grove.generate_pyrolily) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.BURNING_GROVE)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("pyrolily_patch"))),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));
            }

            if (Config.instance().biomes.ghostly_woods.generate_headstones) {
                bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(ModBiomes.GHOSTLY_WOODS)),
                        HolderSet.direct(placedFeatures.getOrThrow(makeKey("headstones"))),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                ));
            }

            bootstrap.register(BIOME_MODIFIERS, new BiomeModifiers.AddFeaturesBiomeModifier(
                    HolderSet.direct(biomes.getOrThrow(Biomes.NETHER_WASTES)),
                    HolderSet.direct(placedFeatures.getOrThrow(makeKey("quartz_crystals_rare"))),
                    GenerationStep.Decoration.UNDERGROUND_DECORATION
            ));
        });

        new RegistrySetBuilder().add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
            HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);

            if (Config.instance().entities.geodin.spawn_weight > 0) {
                bootstrap.register(SPAWN_MODIFIERS, new BiomeModifiers.AddSpawnsBiomeModifier(
                                HolderSet.direct(biomes.getOrThrow(ModBiomes.CRYSTAL_FOREST)),
                                List.of(
                                        new MobSpawnSettings.SpawnerData(ModEntities.GEODIN.get(), Config.instance().entities.geodin.spawn_weight, 2, 4)
                                )
                        )
                );
                bootstrap.register(SPAWN_MODIFIERS, new BiomeModifiers.AddSpawnsBiomeModifier(
                                HolderSet.direct(biomes.getOrThrow(ModBiomes.QUARTZ_CAVERNS)),
                                List.of(
                                        new MobSpawnSettings.SpawnerData(ModEntities.GEODIN.get(), Config.instance().entities.geodin.spawn_weight, 2, 4)
                                )
                        )
                );
            }
        });
    }

    public static ResourceKey<PlacedFeature> makeKey(String path) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, path));
    }
}
