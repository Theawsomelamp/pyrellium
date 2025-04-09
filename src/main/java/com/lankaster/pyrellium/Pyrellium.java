package com.lankaster.pyrellium;

import com.lankaster.pyrellium.config.ConfigHandler;
import com.lankaster.pyrellium.particles.ModParticleTypes;
import net.fabricmc.api.ModInitializer;

import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItemGroups;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.feature.ModFeatures;
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
	}
}