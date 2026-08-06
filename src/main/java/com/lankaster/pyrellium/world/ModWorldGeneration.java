package com.lankaster.pyrellium.world;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.world.tree.BurningTrunkPlacer;
import com.lankaster.pyrellium.world.tree.WeepingFoliagePlacer;
import com.lankaster.pyrellium.world.tree.WillowFoliagePlacer;
import com.lankaster.pyrellium.world.tree.HangingTreeDecorator;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;


public class ModWorldGeneration {

    private static void generateFeatures() {
        if (Config.instance().globalFeatures.thicker_bedrock_ceiling) {
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "thick_bedrock_ceiling")));
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "bedrock_gradient")));
        }

        if (Config.instance().globalFeatures.opal_geodes)
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "opal_geode")));

        if (Config.instance().globalFeatures.basalt_iron_ore)
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "basalt_iron_ore")));

        if (Config.instance().globalFeatures.lava_lake_additions) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "cool_lava_lake")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.GHOSTLY_WOODS), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "cool_lava_lake")));
        }

        if (Config.instance().globalFeatures.soul_sand_valley_bones)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "bones")));

        if (Config.instance().globalFeatures.nether_forest_fallen_logs) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.CRIMSON_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "crimson_stems")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "warped_stems")));
        }

        if (Config.instance().biomes.mushroom_wastes.generate_spores)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.MUSHROOM_WASTES), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "spores")));

        if (Config.instance().biomes.mushroom_wastes.generate_wall_mushrooms)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.MUSHROOM_WASTES), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "wall_mushrooms")));

        if (Config.instance().biomes.monolith_plains.generate_bomb_flowers)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.MONOLITH_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "bomb_plants")));

        if (Config.instance().biomes.monolith_plains.generate_monolith) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.MONOLITH_PLAINS), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "obsidian_monolith")));
        }

        if (Config.instance().globalFeatures.gilded_blackstone_patches) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.BLACKSTONE_SPRINGS), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "gilded_patch")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.BURNING_GROVE), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "gilded_patch")));
        }

        if (Config.instance().biomes.blackstone_springs.generate_blackstone_rocks)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.BLACKSTONE_SPRINGS), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "blackstone_rock_patch")));

        if (Config.instance().biomes.crystal_forest.generate_floor_crystals)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.CRYSTAL_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "crystal_vegetation")));

        if (Config.instance().biomes.infested_valley.generate_floor_decorations)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.INFESTED_VALLEY), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "cobwebs")));

        if (Config.instance().biomes.infested_valley.generate_hanging_silk)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.INFESTED_VALLEY), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "hanging_silk")));

        if (Config.instance().biomes.quartz_caverns.generate_quartz_spikes) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.QUARTZ_CAVERNS), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "spike")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.QUARTZ_CAVERNS), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "spike_down")));
        }

        if (Config.instance().biomes.quartz_caverns.generate_quartz_cracks)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.QUARTZ_CAVERNS), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "quartz_crack")));

        if (Config.instance().biomes.burning_grove.generate_pyrolily)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.BURNING_GROVE), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "pyrolily_patch")));

        if (Config.instance().biomes.ghostly_woods.generate_headstones)
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.GHOSTLY_WOODS), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "headstones")));

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "quartz_crystals_rare")));
    }

    public static void register() {
        generateFeatures();
        BurningTrunkPlacer.registerBurningTrunkPlacer();
        WillowFoliagePlacer.registerWillowFoliagePlacer();
        WeepingFoliagePlacer.registerWeepingFoliagePlacer();
        HangingTreeDecorator.registerHangingTreeDecorator();
    }
}
