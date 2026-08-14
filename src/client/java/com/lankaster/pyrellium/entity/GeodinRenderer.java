package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.render.ModModelLayers;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

public class GeodinRenderer extends MobEntityRenderer<GeodinEntity, GeodinModel<GeodinEntity>> {
    private static final float pi = (float) Math.PI;

    public GeodinRenderer(EntityRendererFactory.Context context) {
        super(context, new GeodinModel<>(context.getPart(ModModelLayers.GEODIN)), 0.6f);
        this.addFeature(new CrystalRenderer(this, context.getBlockRenderManager().getModelRenderer()));
    }

    @Override
    public Identifier getTexture(GeodinEntity entity) {
        return entity.getVariant().texture;
    }

    public static class CrystalRenderer extends FeatureRenderer<GeodinEntity, GeodinModel<GeodinEntity>> {
        private final BlockModelRenderer blockRenderer;

        public CrystalRenderer(FeatureRendererContext<GeodinEntity, GeodinModel<GeodinEntity>> context, BlockModelRenderer blockRenderer) {
            super(context);
            this.blockRenderer = blockRenderer;
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, GeodinEntity entity,
                           float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
            Block block = entity.getBlockFromAge(entity.getCrystalAge());
            BakedModel blockModel = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(block.getDefaultState());

            matrices.push();
            matrices.translate(-0.5F, 0.6666667F, 0.5F);
            matrices.multiply(new Quaternionf().rotateXYZ(pi, 0.0F, 0.0F));
            this.blockRenderer.render(matrices.peek(), vertexConsumers.getBuffer(RenderLayer.getCutout()), block.getDefaultState(), blockModel, 1.0F, 1.0F, 1.0F, light, 0);
            matrices.pop();
        }
    }
}