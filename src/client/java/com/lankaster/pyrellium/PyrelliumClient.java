package com.lankaster.pyrellium;

import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.entity.CrystalArrowRenderer;
import com.lankaster.pyrellium.entity.GeodinModel;
import com.lankaster.pyrellium.entity.GeodinRenderer;
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class PyrelliumClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		WorldRenderEvents.AFTER_ENTITIES.register(BlockOutline::renderBoxOverlay);

		ArmorRenderer.register(new HatRender(), ModItems.OPAL_TIARA);
		ArmorRenderer.register(new HatRender(), ModItems.MUSHROOM_CAP);

		EntityRendererRegistry.register(ModEntities.CRYSTAL_ARROW, CrystalArrowRenderer::new);
		EntityRendererRegistry.register(ModEntities.BOMB_FLOWER, ThrownItemRenderer::new);
		EntityRendererRegistry.register(ModEntities.BURNING_BOAT, (context) -> new BoatRenderer(context, false));
		EntityRendererRegistry.register(ModEntities.BURNING_CHEST_BOAT, (context) -> new BoatRenderer(context, true));
		EntityRendererRegistry.register(ModEntities.GEODIN, GeodinRenderer::new);

		ModBlockEntityRenderer.register();
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.HEADSTONE, HeadStoneBlockEntityRenderer::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.GEODIN, GeodinModel::getTexturedModelData);

		ModParticles.registerParticle();

		ModClientNetworking.registerPacketReceivers();

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OPAL_CLUSTER, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_OPAL_BUD, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_OPAL_BUD, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_OPAL_BUD, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLEAR_AMETHYST_BLOCK, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLEAR_OPAL_BLOCK, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEAD_SPROUTS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEAD_ROOTS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BONE, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BROWN_WALL_MUSHROOM, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_WALL_MUSHROOM, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BROWN_SHELF_MUSHROOM, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_SHELF_MUSHROOM, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPORES, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPORES, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HANGING_SILK, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BOMB_PLANT, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GHOSTLY_LEAVES, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.QUARTZ_CRYSTAL, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACKSTONE_ROCK, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BURNING_TRAPDOOR, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BURNING_SAPLING, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BURNING_SPROUTS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BURNING_ROOTS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BURNING_VINES, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BURNING_SAPLING, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PYROLILY, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_PYROLILY, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FREEZING_ICE, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HEADSTONE, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADEROOT_DOOR, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GHOSTLY_VINES, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLOWERING_GHOSTLY_LEAVES, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHADEROOT_SAPLING, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SHADEROOT_SAPLING, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WISPBLOOM, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHAIN_FENCE, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLEEPING_AMETHYST_GEODIN, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLEEPING_OPAL_GEODIN, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_QUARTZ_BUD, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_QUARTZ_BUD, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_QUARTZ_BUD, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLEEPING_QUARTZ_GEODIN, RenderType.cutout());
	}
}