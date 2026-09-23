package com.lankaster.pyrellium.client;

import com.lankaster.pyrellium.block.entity.ModBlockEntities;
import com.lankaster.pyrellium.client.entity.CrystalArrowRenderer;
import com.lankaster.pyrellium.client.entity.GeodinModel;
import com.lankaster.pyrellium.client.entity.GeodinRenderer;
import com.lankaster.pyrellium.client.particles.ArrowShatterParticle;
import com.lankaster.pyrellium.entity.ModEntities;
import com.lankaster.pyrellium.client.render.HeadStoneBlockEntityRenderer;
import com.lankaster.pyrellium.client.render.ModModelLayers;
import com.lankaster.pyrellium.client.util.BlockOutline;
import com.lankaster.pyrellium.client.util.HatRender;
import com.lankaster.pyrellium.particles.ModParticleTypes;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class PyrelliumClient {
	@SubscribeEvent
	public static void renderBoxOverlay(RenderLevelStageEvent worldRenderContext) {
		if (worldRenderContext.getStage()  == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
			BlockOutline.renderBoxOverlay(worldRenderContext, BlockOutline.raycast(), 0.76f, 0.85f, 0.98f);
			BlockOutline.renderBoxOverlay(worldRenderContext, BlockOutline.saveBlock(), 0.76f, 0.85f, 0.98f);
			BlockOutline.renderBoxOverlay(worldRenderContext, BlockOutline.sharedPos, 0.60f, 0.36f, 0.78f);
		}
	}

	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ModParticleTypes.AMETHYST_SHARD.get(), ArrowShatterParticle.AmethystFactory::new);
		event.registerSpriteSet(ModParticleTypes.OPAL_SHARD.get(), ArrowShatterParticle.OpalFactory::new);
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.HEADSTONE.get(), HeadStoneBlockEntityRenderer::new);
		event.registerEntityRenderer(ModEntities.CRYSTAL_ARROW.get(), CrystalArrowRenderer::new);
		event.registerEntityRenderer(ModEntities.BOMB_FLOWER.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ModEntities.BURNING_BOAT.get(), (context) -> new BoatRenderer(context, false));
		event.registerEntityRenderer(ModEntities.BURNING_CHEST_BOAT.get(), (context) -> new BoatRenderer(context, true));
		event.registerEntityRenderer(ModEntities.GEODIN.get(), GeodinRenderer::new);
	}

	@SubscribeEvent
	public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.HEADSTONE, HeadStoneBlockEntityRenderer::getTexturedModelData);
		event.registerLayerDefinition(ModModelLayers.GEODIN, GeodinModel::getTexturedModelData);
	}

	@SubscribeEvent
	public static void addEntityLayers(EntityRenderersEvent.AddLayers event) {
		if(event.getSkin(PlayerSkin.Model.WIDE) instanceof PlayerRenderer playerRenderer) {
			playerRenderer.addLayer(new HatRender<>(playerRenderer));
		}
		if(event.getSkin(PlayerSkin.Model.SLIM) instanceof PlayerRenderer playerRenderer) {
			playerRenderer.addLayer(new HatRender<>(playerRenderer));
		}
	}
}