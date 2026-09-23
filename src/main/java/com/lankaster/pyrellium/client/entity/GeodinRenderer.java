package com.lankaster.pyrellium.client.entity;

import com.lankaster.pyrellium.entity.GeodinEntity;
import com.lankaster.pyrellium.client.render.ModModelLayers;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.BakedModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

public class GeodinRenderer extends MobRenderer<GeodinEntity, GeodinModel<GeodinEntity>> {
    private static final float pi = (float) Math.PI;

    public GeodinRenderer(EntityRendererProvider.Context context) {
        super(context, new GeodinModel<>(context.bakeLayer(ModModelLayers.GEODIN)), 0.6f);
        this.addLayer(new CrystalRenderer(this, context.getBlockRenderDispatcher().getModelRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(GeodinEntity entity) {
        return entity.getVariant().texture;
    }

    public static class CrystalRenderer extends RenderLayer<GeodinEntity, GeodinModel<GeodinEntity>> {
        private final ModelBlockRenderer blockRenderer;

        public CrystalRenderer(RenderLayerParent<GeodinEntity, GeodinModel<GeodinEntity>> context, ModelBlockRenderer blockRenderer) {
            super(context);
            this.blockRenderer = blockRenderer;
        }

        @Override
        public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, GeodinEntity entity,
                           float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
            Block block = entity.getBlockFromAge(entity.getCrystalAge());
            BakedModel blockModel = Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(block.defaultBlockState());

            matrices.pushPose();
            matrices.translate(-0.5F, 0.6666667F, 0.5F);
            matrices.mulPose(new Quaternionf().rotateXYZ(pi, 0.0F, 0.0F));
            this.blockRenderer.renderModel(matrices.last(), vertexConsumers.getBuffer(RenderType.cutout()), block.defaultBlockState(), blockModel, 1.0F, 1.0F, 1.0F, light, 0);
            matrices.popPose();
        }
    }
}