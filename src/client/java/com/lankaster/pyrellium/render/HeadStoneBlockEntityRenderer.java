package com.lankaster.pyrellium.render;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.entity.HeadStoneBlockEntity;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.SignText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.OrderedText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;

import java.util.List;
import java.util.Objects;

public class HeadStoneBlockEntityRenderer implements BlockEntityRenderer<HeadStoneBlockEntity> {
    private final SignBlockEntityRenderer.SignModel signModel;
    private static final Identifier TEXTURE = new Identifier(Pyrellium.MOD_ID, "entity/signs/headstone");
    private static final Vec3d TEXT_OFFSET = new Vec3d(0.0F, 0.33333334F, 0.096666667F);
    private final TextRenderer textRenderer;

    public HeadStoneBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.signModel = new SignBlockEntityRenderer.SignModel(context.getLayerModelPart(ModModelLayers.HEADSTONE));
        this.textRenderer = context.getTextRenderer();
    }

    @Override
    public void render(HeadStoneBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState blockState = entity.getCachedState();
        AbstractSignBlock abstractSignBlock = (AbstractSignBlock)blockState.getBlock();
        SignBlockEntityRenderer.SignModel signModel = this.signModel;
        render(entity, matrices, vertexConsumers, light, overlay, signModel, blockState, abstractSignBlock);
    }

    void render(HeadStoneBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Model model, BlockState state, AbstractSignBlock block) {
        matrices.push();
        this.setAngles(matrices, -block.getRotationDegrees(state), state);
        this.renderSign(matrices, vertexConsumers, light, overlay, model);
        this.renderText(entity.getPos(), entity.getFrontText(), matrices, vertexConsumers, light, entity.getTextLineHeight(), entity.getMaxTextWidth(), true);
        this.renderText(entity.getPos(), entity.getBackText(), matrices, vertexConsumers, light, entity.getTextLineHeight(), entity.getMaxTextWidth(), false);
        matrices.pop();
    }

    void setAngles(MatrixStack matrices, float rotationDegrees, BlockState state) {
        matrices.translate(0.5F, 0.75F * 0.6666667F, 0.5F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationDegrees));
        if (!(state.getBlock() instanceof SignBlock)) {
            matrices.translate(0.0F, -0.3125F, -0.40625F);
        }

    }

    void renderSign(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Model model) {
        matrices.push();
        SpriteIdentifier spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, TEXTURE);
        Objects.requireNonNull(model);
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, model::getLayer);
        this.renderSignModel(matrices, light, overlay, model, vertexConsumer);
        matrices.pop();
    }

    void renderSignModel(MatrixStack matrices, int light, int overlay, Model model, VertexConsumer vertexConsumers) {
        SignBlockEntityRenderer.SignModel signModel = (SignBlockEntityRenderer.SignModel)model;
        signModel.root.render(matrices, vertexConsumers, light, overlay);
    }

    void renderText(BlockPos pos, SignText signText, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int lineHeight, int lineWidth, boolean front) {
        matrices.push();
        this.setTextAngles(matrices, front);
        int i = getColor(signText);
        int j = 4 * lineHeight / 2;
        OrderedText[] orderedTexts = signText.getOrderedMessages(MinecraftClient.getInstance().shouldFilterText(), (text) -> {
            List<OrderedText> list = this.textRenderer.wrapLines(text, lineWidth);
            return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
        });
        int k;
        boolean bl;
        int l;
        if (signText.isGlowing()) {
            k = signText.getColor().getSignColor();
            bl = shouldRender(pos, k);
            l = 15728880;
        } else {
            k = i;
            bl = false;
            l = light;
        }

        for(int m = 0; m < 4; ++m) {
            OrderedText orderedText = orderedTexts[m];
            float f = (float)(-this.textRenderer.getWidth(orderedText) / 2);
            if (bl) {
                this.textRenderer.drawWithOutline(orderedText, f, (float)(m * lineHeight - j), k, i, matrices.peek().getPositionMatrix(), vertexConsumers, l);
            } else {
                this.textRenderer.draw(orderedText, f, (float)(m * lineHeight - j), k, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.POLYGON_OFFSET, 0, l);
            }
        }

        matrices.pop();
    }

    private void setTextAngles(MatrixStack matrices, boolean front) {
        if (!front) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        }

        float f = 0.015625F * 0.6666667F;
        matrices.translate(HeadStoneBlockEntityRenderer.TEXT_OFFSET.x, HeadStoneBlockEntityRenderer.TEXT_OFFSET.y, HeadStoneBlockEntityRenderer.TEXT_OFFSET.z);
        matrices.scale(f, -f, f);
    }

    static boolean shouldRender(BlockPos pos, int signColor) {
        if (signColor == DyeColor.BLACK.getSignColor()) {
            return true;
        } else {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
            if (clientPlayerEntity != null && minecraftClient.options.getPerspective().isFirstPerson() && clientPlayerEntity.isUsingSpyglass()) {
                return true;
            } else {
                Entity entity = minecraftClient.getCameraEntity();
                return entity != null && entity.squaredDistanceTo(Vec3d.ofCenter(pos)) < MathHelper.square(16);
            }
        }
    }

    static int getColor(SignText sign) {
        int i = sign.getColor().getSignColor();
        if (i == DyeColor.BLACK.getSignColor() && sign.isGlowing()) {
            return -988212;
        } else {
            int j = (int)((double) ColorHelper.Argb.getRed(i) * 0.4);
            int k = (int)((double) ColorHelper.Argb.getGreen(i) * 0.4);
            int l = (int)((double) ColorHelper.Argb.getBlue(i) * 0.4);
            return ColorHelper.Argb.getArgb(0, j, k, l);
        }
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("sign", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -3.0F, -1.5F, 16.0F, 13.0F, 3.0F), ModelTransform.NONE);
        modelPartData.addChild("top1", ModelPartBuilder.create().uv(0, 16).cuboid(-7.0F, 10.0F, -1.5F, 14.0F, 1.0F, 3.0F), ModelTransform.NONE);
        modelPartData.addChild("top2", ModelPartBuilder.create().uv(0, 20).cuboid(-6.0F, 11.0F, -1.5F, 12.0F, 1.0F, 3.0F), ModelTransform.NONE);
        modelPartData.addChild("top3", ModelPartBuilder.create().uv(0, 24).cuboid(-5.0F, 12.0F, -1.5F, 10.0F, 1.0F, 3.0F), ModelTransform.NONE);
        modelPartData.addChild("stick", ModelPartBuilder.create().uv(0, 14).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 32);
    }
}
