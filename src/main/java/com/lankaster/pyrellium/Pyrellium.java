package com.lankaster.pyrellium;

import com.lankaster.pyrellium.config.ConfigHandler;
import com.lankaster.pyrellium.particles.ModParticleTypes;
import net.fabricmc.api.ModInitializer;

import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItemGroups;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.feature.ModFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

public class Pyrellium implements ModInitializer {
	public static final String MOD_ID = "pyrellium";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ConfigHandler.load(Path.of(System.getProperty("user.dir") + File.separator + "config" + File.separator + Pyrellium.MOD_ID + ".json"));

		ModItemGroups.registerItemGroups();

		ModFeatures.registerModFeatures();
		ModParticleTypes.registerParticle();

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();

		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "opal_geode")));
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "cool_lava_lake")));
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "bones")));
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "quartz_crystals_rare")));
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.CRIMSON_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "crimson_stems")));
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "warped_stems")));
	}
}