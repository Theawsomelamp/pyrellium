package com.lankaster.pyrellium;

import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.entity.CrystalArrowRenderer;
import com.lankaster.pyrellium.entity.ModEntities;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.networking.ModClientNetworking;
import com.lankaster.pyrellium.particles.ModParticles;
import com.lankaster.pyrellium.render.HeadStoneBlockEntityRenderer;
import com.lankaster.pyrellium.render.ModBlockEntityRenderer;
import com.lankaster.pyrellium.render.ModModelLayers;
import com.lankaster.pyrellium.util.BlockOutline;
import com.lankaster.pyrellium.util.HatRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class PyrelliumClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		WorldRenderEvents.START.register(BlockOutline::renderBoxOverlay);

		ArmorRenderer.register(new HatRender(), ModItems.OPAL_TIARA);
		ArmorRenderer.register(new HatRender(), ModItems.MUSHROOM_CAP);

		EntityRendererRegistry.register(ModEntities.CRYSTAL_ARROW, CrystalArrowRenderer::new);
		EntityRendererRegistry.register(ModEntities.BOMB_FLOWER, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.BURNING_BOAT, (context) -> new BoatEntityRenderer(context, false));
		EntityRendererRegistry.register(ModEntities.BURNING_CHEST_BOAT, (context) -> new BoatEntityRenderer(context, true));

		ModBlockEntityRenderer.register();
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.HEADSTONE, HeadStoneBlockEntityRenderer::getTexturedModelData);

		ModParticles.registerParticle();

		ModClientNetworking.registerPacketReceivers();

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OPAL_CLUSTER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_OPAL_BUD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_OPAL_BUD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_OPAL_BUD, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLEAR_AMETHYST_BLOCK, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLEAR_OPAL_BLOCK, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEAD_SPROUTS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEAD_ROOTS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BONE, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BROWN_WALL_MUSHROOM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_WALL_MUSHROOM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BROWN_SHELF_MUSHROOM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_SHELF_MUSHROOM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPORES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPORES, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HANGING_SILK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BOMB_PLANT, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GHOSTLY_LEAVES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.QUARTZ_CRYSTAL, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACKSTONE_ROCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BURNING_TRAPDOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BURNING_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BURNING_SPROUTS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BURNING_ROOTS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BURNING_VINES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BURNING_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PYROLILY, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_PYROLILY, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FREEZING_ICE, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HEADSTONE, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADEROOT_DOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GHOSTLY_VINES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLOWERING_GHOSTLY_LEAVES, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADEROOT_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SHADEROOT_SAPLING, RenderLayer.getCutout());
	}
}