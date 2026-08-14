package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.render.ModModelLayers;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

public class GeodinRenderer extends MobEntityRenderer<GeodinEntity, GeodinEntityRenderState, GeodinModel> {
    private static final float pi = (float) Math.PI;

    public GeodinRenderer(EntityRendererFactory.Context context) {
        super(context, new GeodinModel(context.getPart(ModModelLayers.GEODIN)), 0.6f);
        this.addFeature(new CrystalRenderer(this));
    }

    @Override
    public GeodinEntityRenderState createRenderState() {
        return new GeodinEntityRenderState();
    }

    public void updateRenderState(GeodinEntity geodinEntity, GeodinEntityRenderState geodinEntityRenderState, float f) {
        super.updateRenderState(geodinEntity, geodinEntityRenderState, f);
        geodinEntityRenderState.variant = geodinEntity.getVariant();
        geodinEntityRenderState.crystalAge = geodinEntity.getCrystalAge();
    }

    @Override
    public Identifier getTexture(GeodinEntityRenderState state) {
        return state.variant.texture;
    }

    public static class CrystalRenderer extends FeatureRenderer<GeodinEntityRenderState, GeodinModel> {

        public CrystalRenderer(FeatureRendererContext<GeodinEntityRenderState, GeodinModel> context) {
            super(context);
        }

        @Override
        public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, GeodinEntityRenderState state, float limbAngle, float limbDistance) {
            BlockState blockState = state.variant.getBlockFromAge(state.crystalAge).getDefaultState();

            matrices.push();
            matrices.translate(-0.5F, 0.6666667F, 0.5F);
            matrices.multiply(new Quaternionf().rotateXYZ(pi, 0.0F, 0.0F));
            queue.submitBlock(matrices, blockState, light, OverlayTexture.DEFAULT_UV, state.outlineColor);
            matrices.pop();
        }
    }
}