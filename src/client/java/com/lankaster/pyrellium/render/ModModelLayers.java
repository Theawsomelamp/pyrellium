package com.lankaster.pyrellium.render;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.entity.CrystalArrowRenderer;
import com.lankaster.pyrellium.entity.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer HEADSTONE =
            new EntityModelLayer(Identifier.of(Pyrellium.MOD_ID, "sign/headstone"), "main");
    public static final EntityModelLayer BURNING_BOAT =
            new EntityModelLayer(Identifier.of(Pyrellium.MOD_ID, "boat/burning"), "main");
    public static final EntityModelLayer BURNING_CHEST_BOAT =
            new EntityModelLayer(Identifier.of(Pyrellium.MOD_ID, "chest_boat/burning"), "main");
    public static final EntityModelLayer SHADEROOT_BOAT =
            new EntityModelLayer(Identifier.of(Pyrellium.MOD_ID, "boat/shaderoot"), "main");
    public static final EntityModelLayer SHADEROOT_CHEST_BOAT =
            new EntityModelLayer(Identifier.of(Pyrellium.MOD_ID, "chest_boat/shaderoot"), "main");

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(HEADSTONE, HeadStoneBlockEntityRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BURNING_BOAT, BoatEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BURNING_CHEST_BOAT, BoatEntityModel::getChestTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SHADEROOT_BOAT, BoatEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SHADEROOT_CHEST_BOAT, BoatEntityModel::getChestTexturedModelData);

        EntityRendererFactories.register(ModEntities.CRYSTAL_ARROW, CrystalArrowRenderer::new);
        EntityRendererFactories.register(ModEntities.BOMB_FLOWER, FlyingItemEntityRenderer::new);
        EntityRendererFactories.register(ModEntities.BURNING_BOAT, (context) -> new BoatEntityRenderer(context, BURNING_BOAT));
        EntityRendererFactories.register(ModEntities.BURNING_CHEST_BOAT, (context) -> new BoatEntityRenderer(context, BURNING_CHEST_BOAT));
        EntityRendererFactories.register(ModEntities.SHADEROOT_BOAT, (context) -> new BoatEntityRenderer(context, SHADEROOT_BOAT));
        EntityRendererFactories.register(ModEntities.SHADEROOT_CHEST_BOAT, (context) -> new BoatEntityRenderer(context, SHADEROOT_CHEST_BOAT));
    }
}