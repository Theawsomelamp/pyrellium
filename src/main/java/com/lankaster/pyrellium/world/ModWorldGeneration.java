package com.lankaster.pyrellium.world;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.ConfigHandler;
import com.lankaster.pyrellium.world.tree.BurningTrunkPlacer;
import com.lankaster.pyrellium.world.tree.WillowFoliagePlacer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.NetherBiomes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.GenerationStep;

public class ModWorldGeneration {

    private static void generateFeatures() {
        if (ConfigHandler.getConfig().globalFeatureConfig().doThickCeiling()) {
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "thick_bedrock_ceiling")));
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "bedrock_gradient")));
        }

        if (ConfigHandler.getConfig().globalFeatureConfig().doOpalGeodes())
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "opal_geode")));

        if (ConfigHandler.getConfig().globalFeatureConfig().doCoolLavaLake()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "cool_lava_lake")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "ghostly_woods"))), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "cool_lava_lake")));
        }

        if (ConfigHandler.getConfig().biomeFeatureConfig().doBones())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "bones")));

        if (ConfigHandler.getConfig().globalFeatureConfig().doFallenLogs()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.CRIMSON_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "crimson_stems")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "warped_stems")));
        }

        if (ConfigHandler.getConfig().biomeFeatureConfig().doSpores())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "mushroom_wastes"))), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "spores")));

        if (ConfigHandler.getConfig().biomeFeatureConfig().doWallMushrooms())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "mushroom_wastes"))), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "wall_mushrooms")));

        if (ConfigHandler.getConfig().biomeFeatureConfig().doBombFlowers())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "monolith_plains"))), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "bomb_plants")));

        if (ConfigHandler.getConfig().biomeFeatureConfig().doMonolith()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "monolith_plains"))), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "obsidian_monolith")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "monolith_plains"))), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "monolith_floor")));
        }

        if (ConfigHandler.getConfig().globalFeatureConfig().doGildedBlackstone()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "blackstone_springs"))), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "gilded_patch")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "burning_grove"))), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "gilded_patch")));
        }

        if (ConfigHandler.getConfig().biomeFeatureConfig().doBlackstoneRocks())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "blackstone_springs"))), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "blackstone_rock_patch")));

        if (ConfigHandler.getConfig().biomeFeatureConfig().doFloorCrystals())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "crystal_forest"))), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "crystal_vegetation")));

        if (ConfigHandler.getConfig().biomeFeatureConfig().doFloorSilk())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "infested_valley"))), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "cobwebs")));

        if (ConfigHandler.getConfig().biomeFeatureConfig().doHangingSilk())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "infested_valley"))), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "hanging_silk")));

        if (ConfigHandler.getConfig().biomeFeatureConfig().doSpikes())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "quartz_caverns"))), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "spike")));

        if (ConfigHandler.getConfig().biomeFeatureConfig().doPyrolily())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "burning_grove"))), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "pyrolily_patch")));

        if (ConfigHandler.getConfig().biomeFeatureConfig().doHeadstones())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "ghostly_woods"))), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "headstones")));

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "quartz_crystals_rare")));
    }

    private static void generateBiomes() {
        if (ConfigHandler.getConfig().biomeConfig().doBlackstoneSprings())
            NetherBiomes.addNetherBiome(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "blackstone_springs")), MultiNoiseUtil.createNoiseHypercube(-0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.15f));

        if (ConfigHandler.getConfig().biomeConfig().doBurningDesert())
            NetherBiomes.addNetherBiome(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "burning_grove")), MultiNoiseUtil.createNoiseHypercube(-0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        if (ConfigHandler.getConfig().biomeConfig().doCrystalForest())
            NetherBiomes.addNetherBiome(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "crystal_forest")), MultiNoiseUtil.createNoiseHypercube(0.0f, 0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.325f));

        if (ConfigHandler.getConfig().biomeConfig().doFrostburnValley())
            NetherBiomes.addNetherBiome(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "frostburn_valley")), MultiNoiseUtil.createNoiseHypercube(0.0f, -0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        if (ConfigHandler.getConfig().biomeConfig().doGhostlyWoods())
            NetherBiomes.addNetherBiome(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "ghostly_woods")), MultiNoiseUtil.createNoiseHypercube(0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        if (ConfigHandler.getConfig().biomeConfig().doInfestedValley())
            NetherBiomes.addNetherBiome(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "infested_valley")), MultiNoiseUtil.createNoiseHypercube(0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.325f));

        if (ConfigHandler.getConfig().biomeConfig().doMonolithPlains())
            NetherBiomes.addNetherBiome(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "monolith_plains")), MultiNoiseUtil.createNoiseHypercube(0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        if (ConfigHandler.getConfig().biomeConfig().doMushroomWastes())
            NetherBiomes.addNetherBiome(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "mushroom_wastes")), MultiNoiseUtil.createNoiseHypercube(-0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f));

        if (ConfigHandler.getConfig().biomeConfig().doQuartzCaverns())
            NetherBiomes.addNetherBiome(RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, "quartz_caverns")), MultiNoiseUtil.createNoiseHypercube(0.25f, 0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.25f));
    }

    public static void register() {
        generateFeatures();
        generateBiomes();
        BurningTrunkPlacer.registerBurningTrunkPlacer();
        WillowFoliagePlacer.registerWillowFoliagePlacer();
    }
}