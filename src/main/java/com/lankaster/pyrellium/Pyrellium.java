package com.lankaster.pyrellium;

import com.lankaster.pyrellium.block.entity.ModBlockEntities;
import com.lankaster.pyrellium.config.ConfigHandler;
import com.lankaster.pyrellium.data.PyrelliumReloadListener;
import com.lankaster.pyrellium.entity.ModEntities;
import com.lankaster.pyrellium.particles.ModParticleTypes;
import com.lankaster.pyrellium.data.PyrelliumCustomData;
import com.lankaster.pyrellium.world.ModWorldGeneration;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItemGroups;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.world.feature.ModFeatures;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Pyrellium.MOD_ID)
public class Pyrellium {
	public static final String MOD_ID = "pyrellium";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public Pyrellium(IEventBus modEventBus) {
		ConfigHandler.load(FMLPaths.CONFIGDIR.get().resolve("pyrellium.json"));
		NeoForge.EVENT_BUS.addListener(PyrelliumReloadListener::registerServerDatapack);
		PyrelliumCustomData.register();

		ModItemGroups.registerItemGroups(modEventBus);

		ModFeatures.registerModFeatures(modEventBus);
		ModParticleTypes.registerParticle(modEventBus);

		ModEntities.registerEntities(modEventBus);

		ModBlocks.registerModBlocks(modEventBus);
		ModBlockEntities.registerBlockEntities(modEventBus);
		ModItems.registerModItems(modEventBus);

		ModWorldGeneration.register(modEventBus);
	}
}