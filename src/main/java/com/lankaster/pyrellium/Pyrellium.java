package com.lankaster.pyrellium;

import com.lankaster.pyrellium.config.ConfigHandler;
import com.lankaster.pyrellium.entity.ModEntities;
import com.lankaster.pyrellium.networking.OpalPayload;
import com.lankaster.pyrellium.particles.ModParticleTypes;
import com.lankaster.pyrellium.world.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItemGroups;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.world.feature.ModFeatures;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pyrellium implements ModInitializer {
	public static final String MOD_ID = "pyrellium";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ConfigHandler.load(FabricLoader.getInstance().getConfigDir().resolve("pyrellium.json"));

		PayloadTypeRegistry.playS2C().register(OpalPayload.ID, OpalPayload.CODEC);

		ModItemGroups.registerItemGroups();

		ModFeatures.registerModFeatures();
		ModParticleTypes.registerParticle();

		ModEntities.registerEntities();

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();

		ModWorldGeneration.register();
	}
}