package com.lankaster.pyrellium;

import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.util.BlockOutline;
import com.lankaster.pyrellium.util.HatRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.RenderLayer;

public class PyrelliumClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		WorldRenderEvents.START.register(BlockOutline::renderBoxOverlay);

		ArmorRenderer.register(new HatRender(), ModItems.OPAL_TIARA);

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
	}
}