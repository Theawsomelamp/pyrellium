package com.lankaster.pyrellium.world;

import com.google.gson.JsonElement;
import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.ConfigCodec;
import com.lankaster.pyrellium.config.ConfigHandler;
import com.lankaster.pyrellium.world.tree.BurningTrunkPlacer;
import com.lankaster.pyrellium.world.tree.WillowFoliagePlacer;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.NetherBiomes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.GenerationStep;

import java.util.ArrayList;
import java.util.List;

public class ModWorldGeneration {

    private static final List<Pair<Identifier, JsonElement>> BIOMES_NOISE = new ArrayList<>();

    public static void add(Identifier biome, JsonElement noise) {
        BIOMES_NOISE.add(new Pair<>(biome, noise));
    }

    public static List<Pair<Identifier, JsonElement>> getNoiseBiomes() {
        return BIOMES_NOISE;
    }

    private static void generateFeatures() {
        if (ConfigHandler.getConfig().globalFeatureConfig().doThickCeiling()) {
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "thick_bedrock_ceiling")));
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "bedrock_gradient")));
        }

        if (ConfigHandler.getConfig().globalFeatureConfig().doOpalGeodes())
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "opal_geode")));

        if (ConfigHandler.getConfig().globalFeatureConfig().doCoolLavaLake()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "cool_lava_lake")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.GHOSTLY_WOODS), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "cool_lava_lake")));
        }

        if (ConfigHandler.getConfig().globalFeatureConfig().doBones())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "bones")));

        if (ConfigHandler.getConfig().globalFeatureConfig().doFallenLogs()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.CRIMSON_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "crimson_stems")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "warped_stems")));
        }

        if (ConfigHandler.getConfig().biomeConfig().mushroomWastesConfig().doSpores())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.MUSHROOM_WASTES), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "spores")));

        if (ConfigHandler.getConfig().biomeConfig().mushroomWastesConfig().doWallMushrooms())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.MUSHROOM_WASTES), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "wall_mushrooms")));

        if (ConfigHandler.getConfig().biomeConfig().monolithPlainsConfig().doBombFlowers())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.MONOLITH_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "bomb_plants")));

        if (ConfigHandler.getConfig().biomeConfig().monolithPlainsConfig().doMonolith()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.MONOLITH_PLAINS), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "obsidian_monolith")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.MONOLITH_PLAINS), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "monolith_floor")));
        }

        if (ConfigHandler.getConfig().globalFeatureConfig().doGildedBlackstone()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.BLACKSTONE_SPRINGS), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "gilded_patch")));
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.BURNING_GROVE), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "gilded_patch")));
        }

        if (ConfigHandler.getConfig().biomeConfig().blackstoneSpringsConfig().doBlackstoneRocks())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.BLACKSTONE_SPRINGS), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "blackstone_rock_patch")));

        if (ConfigHandler.getConfig().biomeConfig().crystalForestConfig().doFloorCrystals())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.CRYSTAL_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "crystal_vegetation")));

        if (ConfigHandler.getConfig().biomeConfig().infestedValleyConfig().doFloorSilk())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.INFESTED_VALLEY), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "cobwebs")));

        if (ConfigHandler.getConfig().biomeConfig().infestedValleyConfig().doHangingSilk())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.INFESTED_VALLEY), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "hanging_silk")));

        if (ConfigHandler.getConfig().biomeConfig().quartzCavernsConfig().doSpikes())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.QUARTZ_CAVERNS), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "spike")));

        if (ConfigHandler.getConfig().biomeConfig().burningGroveConfig().doPyrolily())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.BURNING_GROVE), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "pyrolily_patch")));

        if (ConfigHandler.getConfig().biomeConfig().ghostlyWoodsConfig().doHeadstones())
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.GHOSTLY_WOODS), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "headstones")));

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "quartz_crystals_rare")));
    }

    private static void generateBiomes() {
        ConfigCodec.BiomeNoiseConfig biomeNoise;

        if (ConfigHandler.getConfig().biomeConfig().blackstoneSpringsConfig().enabled()) {
            biomeNoise = ConfigHandler.getConfig().biomeConfig().blackstoneSpringsConfig().biomeNoiseConfig();
            add(ModBiomes.BLACKSTONE_SPRINGS.getValue(), ConfigHandler.noiseToJson(biomeNoise));
            NetherBiomes.addNetherBiome(ModBiomes.BLACKSTONE_SPRINGS, MultiNoiseUtil.createNoiseHypercube(biomeNoise.temperature(), biomeNoise.humidity(), biomeNoise.continentalness(), biomeNoise.erosion(), biomeNoise.depth(), biomeNoise.weirdness(), biomeNoise.offset()));
        }

        if (ConfigHandler.getConfig().biomeConfig().burningGroveConfig().enabled()) {
            biomeNoise = ConfigHandler.getConfig().biomeConfig().burningGroveConfig().biomeNoiseConfig();
            add(ModBiomes.BURNING_GROVE.getValue(), ConfigHandler.noiseToJson(biomeNoise));
            NetherBiomes.addNetherBiome(ModBiomes.BURNING_GROVE, MultiNoiseUtil.createNoiseHypercube(biomeNoise.temperature(), biomeNoise.humidity(), biomeNoise.continentalness(), biomeNoise.erosion(), biomeNoise.depth(), biomeNoise.weirdness(), biomeNoise.offset()));
        }

        if (ConfigHandler.getConfig().biomeConfig().crystalForestConfig().enabled()) {
            biomeNoise = ConfigHandler.getConfig().biomeConfig().crystalForestConfig().biomeNoiseConfig();
            add(ModBiomes.CRYSTAL_FOREST.getValue(), ConfigHandler.noiseToJson(biomeNoise));
            NetherBiomes.addNetherBiome(ModBiomes.CRYSTAL_FOREST, MultiNoiseUtil.createNoiseHypercube(biomeNoise.temperature(), biomeNoise.humidity(), biomeNoise.continentalness(), biomeNoise.erosion(), biomeNoise.depth(), biomeNoise.weirdness(), biomeNoise.offset()));
        }

        if (ConfigHandler.getConfig().biomeConfig().frostburnValleyConfig().enabled()) {
            biomeNoise = ConfigHandler.getConfig().biomeConfig().frostburnValleyConfig().biomeNoiseConfig();
            add(ModBiomes.FROSTBURN_VALLEY.getValue(), ConfigHandler.noiseToJson(biomeNoise));
            NetherBiomes.addNetherBiome(ModBiomes.FROSTBURN_VALLEY, MultiNoiseUtil.createNoiseHypercube(biomeNoise.temperature(), biomeNoise.humidity(), biomeNoise.continentalness(), biomeNoise.erosion(), biomeNoise.depth(), biomeNoise.weirdness(), biomeNoise.offset()));
        }

        if (ConfigHandler.getConfig().biomeConfig().ghostlyWoodsConfig().enabled()) {
            biomeNoise = ConfigHandler.getConfig().biomeConfig().ghostlyWoodsConfig().biomeNoiseConfig();
            add(ModBiomes.GHOSTLY_WOODS.getValue(), ConfigHandler.noiseToJson(biomeNoise));
            NetherBiomes.addNetherBiome(ModBiomes.GHOSTLY_WOODS, MultiNoiseUtil.createNoiseHypercube(biomeNoise.temperature(), biomeNoise.humidity(), biomeNoise.continentalness(), biomeNoise.erosion(), biomeNoise.depth(), biomeNoise.weirdness(), biomeNoise.offset()));
        }

        if (ConfigHandler.getConfig().biomeConfig().infestedValleyConfig().enabled()) {
            biomeNoise = ConfigHandler.getConfig().biomeConfig().infestedValleyConfig().biomeNoiseConfig();
            add(ModBiomes.INFESTED_VALLEY.getValue(), ConfigHandler.noiseToJson(biomeNoise));
            NetherBiomes.addNetherBiome(ModBiomes.INFESTED_VALLEY, MultiNoiseUtil.createNoiseHypercube(biomeNoise.temperature(), biomeNoise.humidity(), biomeNoise.continentalness(), biomeNoise.erosion(), biomeNoise.depth(), biomeNoise.weirdness(), biomeNoise.offset()));
        }

        if (ConfigHandler.getConfig().biomeConfig().monolithPlainsConfig().enabled()) {
            biomeNoise = ConfigHandler.getConfig().biomeConfig().monolithPlainsConfig().biomeNoiseConfig();
            add(ModBiomes.MONOLITH_PLAINS.getValue(), ConfigHandler.noiseToJson(biomeNoise));
            NetherBiomes.addNetherBiome(ModBiomes.MONOLITH_PLAINS, MultiNoiseUtil.createNoiseHypercube(biomeNoise.temperature(), biomeNoise.humidity(), biomeNoise.continentalness(), biomeNoise.erosion(), biomeNoise.depth(), biomeNoise.weirdness(), biomeNoise.offset()));
        }

        if (ConfigHandler.getConfig().biomeConfig().mushroomWastesConfig().enabled()) {
            biomeNoise = ConfigHandler.getConfig().biomeConfig().mushroomWastesConfig().biomeNoiseConfig();
            add(ModBiomes.MUSHROOM_WASTES.getValue(), ConfigHandler.noiseToJson(biomeNoise));
            NetherBiomes.addNetherBiome(ModBiomes.MUSHROOM_WASTES, MultiNoiseUtil.createNoiseHypercube(biomeNoise.temperature(), biomeNoise.humidity(), biomeNoise.continentalness(), biomeNoise.erosion(), biomeNoise.depth(), biomeNoise.weirdness(), biomeNoise.offset()));
        }

        if (ConfigHandler.getConfig().biomeConfig().quartzCavernsConfig().enabled()) {
            biomeNoise = ConfigHandler.getConfig().biomeConfig().quartzCavernsConfig().biomeNoiseConfig();
            add(ModBiomes.QUARTZ_CAVERNS.getValue(), ConfigHandler.noiseToJson(biomeNoise));
            NetherBiomes.addNetherBiome(ModBiomes.QUARTZ_CAVERNS, MultiNoiseUtil.createNoiseHypercube(biomeNoise.temperature(), biomeNoise.humidity(), biomeNoise.continentalness(), biomeNoise.erosion(), biomeNoise.depth(), biomeNoise.weirdness(), biomeNoise.offset()));
        }
    }

    public static void register() {
        generateFeatures();
        generateBiomes();
        BurningTrunkPlacer.registerBurningTrunkPlacer();
        WillowFoliagePlacer.registerWillowFoliagePlacer();
    }
}