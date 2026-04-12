package com.lankaster.pyrellium.render;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.AbstractSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.SignBlockEntityRenderState;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;

public class HeadStoneBlockEntityRenderer extends AbstractSignBlockEntityRenderer {
    private final Model.SinglePartModel signModel;
    private static final Identifier TEXTURE = Identifier.of(Pyrellium.MOD_ID, "entity/signs/headstone");
    private static final Vec3d TEXT_OFFSET = new Vec3d(0.0F, 0.33333334F, 0.096666667F);

    public HeadStoneBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
        this.signModel = new Model.SinglePartModel(context.getLayerModelPart(ModModelLayers.HEADSTONE), RenderLayers::entityCutoutNoCull);
    }

    @Override
    protected Model.SinglePartModel getModel(BlockState state, WoodType woodType) {
        return signModel;
    }

    @Override
    protected SpriteIdentifier getTextureId(WoodType woodType) {
        return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, TEXTURE);
    }

    protected float getSignScale() {
        return 1.0F;
    }

    protected float getTextScale() {
        return 0.6666667F;
    }

    @Override
    protected Vec3d getTextOffset() {
        return TEXT_OFFSET;
    }

    @Override
    protected void applyTransforms(MatrixStack matrices, float blockRotationDegrees, BlockState state) {
        setAngles(matrices, blockRotationDegrees, state);
    }

    @Override
    public SignBlockEntityRenderState createRenderState() {
        return new SignBlockEntityRenderState();
    }

    void setAngles(MatrixStack matrices, float rotationDegrees, BlockState state) {
        matrices.translate(0.5F, 0.75F * 0.6666667F, 0.5F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationDegrees));
        if (!(state.getBlock() instanceof SignBlock)) {
            matrices.translate(0.0F, -0.3125F, -0.40625F);
        }

    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        return TexturedModelData.of(modelData, 64, 32);
    }
}