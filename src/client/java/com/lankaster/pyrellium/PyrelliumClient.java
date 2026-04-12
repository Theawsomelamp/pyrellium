package com.lankaster.pyrellium;

import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.networking.ModClientNetworking;
import com.lankaster.pyrellium.particles.ModParticles;
import com.lankaster.pyrellium.render.ModBlockEntityRenderer;
import com.lankaster.pyrellium.render.ModModelLayers;
import com.lankaster.pyrellium.util.BlockOutline;
import com.lankaster.pyrellium.util.HatRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.render.BlockRenderLayer;

public class PyrelliumClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		WorldRenderEvents.AFTER_ENTITIES.register(BlockOutline::renderBoxOverlay);

		ArmorRenderer.register(new HatRender(), ModItems.OPAL_TIARA, ModItems.MUSHROOM_CAP);

		ModModelLayers.register();
		ModBlockEntityRenderer.register();

		ModParticles.registerParticle();

		ModClientNetworking.registerPacketReceivers();

		BlockRenderLayerMap.putBlock(ModBlocks.OPAL_CLUSTER, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SMALL_OPAL_BUD, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.MEDIUM_OPAL_BUD, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.LARGE_OPAL_BUD, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.CLEAR_AMETHYST_BLOCK, BlockRenderLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(ModBlocks.CLEAR_OPAL_BLOCK, BlockRenderLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(ModBlocks.DEAD_SPROUTS, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.DEAD_ROOTS, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BONE, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BROWN_WALL_MUSHROOM, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.RED_WALL_MUSHROOM, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BROWN_SHELF_MUSHROOM, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.RED_SHELF_MUSHROOM, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SPORES, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SPORES, BlockRenderLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(ModBlocks.HANGING_SILK, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BOMB_PLANT, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.GHOSTLY_LEAVES, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.QUARTZ_CRYSTAL, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BLACKSTONE_ROCK, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BURNING_TRAPDOOR, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BURNING_SAPLING, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BURNING_SPROUTS, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BURNING_ROOTS, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BURNING_VINES, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.POTTED_BURNING_SAPLING, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.PYROLILY, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.POTTED_PYROLILY, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.FREEZING_ICE, BlockRenderLayer.TRANSLUCENT);
		BlockRenderLayerMap.putBlock(ModBlocks.HEADSTONE, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SHADEROOT_DOOR, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.GHOSTLY_VINES, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.FLOWERING_GHOSTLY_LEAVES, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SHADEROOT_SAPLING, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.POTTED_SHADEROOT_SAPLING, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.WISPBLOOM, BlockRenderLayer.CUTOUT);
	}
}