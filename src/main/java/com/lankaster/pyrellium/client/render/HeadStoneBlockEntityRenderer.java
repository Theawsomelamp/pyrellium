package com.lankaster.pyrellium.client.render;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.entity.HeadStoneBlockEntity;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.Sheets;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.Material;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.DyeColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class HeadStoneBlockEntityRenderer implements BlockEntityRenderer<HeadStoneBlockEntity> {
    private final SignRenderer.SignModel signModel;
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "entity/signs/headstone");
    private static final Vec3 TEXT_OFFSET = new Vec3(0.0F, 0.33333334F, 0.096666667F);
    private final Font textRenderer;

    public HeadStoneBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.signModel = new SignRenderer.SignModel(context.bakeLayer(ModModelLayers.HEADSTONE));
        this.textRenderer = context.getFont();
    }

    @Override
    public void render(HeadStoneBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        BlockState blockState = entity.getBlockState();
        SignBlock abstractSignBlock = (SignBlock)blockState.getBlock();
        SignRenderer.SignModel signModel = this.signModel;
        render(entity, matrices, vertexConsumers, light, overlay, signModel, blockState, abstractSignBlock);
    }

    void render(HeadStoneBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, Model model, BlockState state, SignBlock block) {
        matrices.pushPose();
        this.setAngles(matrices, -block.getYRotationDegrees(state), state);
        this.renderSign(matrices, vertexConsumers, light, overlay, model);
        this.renderText(entity.getBlockPos(), entity.getFrontText(), matrices, vertexConsumers, light, entity.getTextLineHeight(), entity.getMaxTextLineWidth(), true);
        this.renderText(entity.getBlockPos(), entity.getBackText(), matrices, vertexConsumers, light, entity.getTextLineHeight(), entity.getMaxTextLineWidth(), false);
        matrices.popPose();
    }

    void setAngles(PoseStack matrices, float rotationDegrees, BlockState state) {
        matrices.translate(0.5F, 0.75F * 0.6666667F, 0.5F);
        matrices.mulPose(Axis.YP.rotationDegrees(rotationDegrees));
        if (!(state.getBlock() instanceof StandingSignBlock)) {
            matrices.translate(0.0F, -0.3125F, -0.40625F);
        }

    }

    void renderSign(PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, Model model) {
        matrices.pushPose();
        Material spriteIdentifier = new Material(Sheets.SIGN_SHEET, TEXTURE);
        Objects.requireNonNull(model);
        VertexConsumer vertexConsumer = spriteIdentifier.buffer(vertexConsumers, model::renderType);
        this.renderSignModel(matrices, light, overlay, model, vertexConsumer);
        matrices.popPose();
    }

    void renderSignModel(PoseStack matrices, int light, int overlay, Model model, VertexConsumer vertexConsumers) {
        SignRenderer.SignModel signModel = (SignRenderer.SignModel)model;
        signModel.root.render(matrices, vertexConsumers, light, overlay);
    }

    void renderText(BlockPos pos, SignText signText, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int lineHeight, int lineWidth, boolean front) {
        matrices.pushPose();
        this.setTextAngles(matrices, front);
        int i = getColor(signText);
        int j = 4 * lineHeight / 2;
        FormattedCharSequence[] orderedTexts = signText.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), (text) -> {
            List<FormattedCharSequence> list = this.textRenderer.split(text, lineWidth);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        int k;
        boolean bl;
        int l;
        if (signText.hasGlowingText()) {
            k = signText.getColor().getTextColor();
            bl = shouldRender(pos, k);
            l = 15728880;
        } else {
            k = i;
            bl = false;
            l = light;
        }

        for(int m = 0; m < 4; ++m) {
            FormattedCharSequence orderedText = orderedTexts[m];
            float f = (float)(-this.textRenderer.width(orderedText) / 2);
            if (bl) {
                this.textRenderer.drawInBatch8xOutline(orderedText, f, (float)(m * lineHeight - j), k, i, matrices.last().pose(), vertexConsumers, l);
            } else {
                this.textRenderer.drawInBatch(orderedText, f, (float)(m * lineHeight - j), k, false, matrices.last().pose(), vertexConsumers, Font.DisplayMode.POLYGON_OFFSET, 0, l);
            }
        }

        matrices.popPose();
    }

    private void setTextAngles(PoseStack matrices, boolean front) {
        if (!front) {
            matrices.mulPose(Axis.YP.rotationDegrees(180.0F));
        }

        float f = 0.015625F * 0.6666667F;
        matrices.translate(HeadStoneBlockEntityRenderer.TEXT_OFFSET.x, HeadStoneBlockEntityRenderer.TEXT_OFFSET.y, HeadStoneBlockEntityRenderer.TEXT_OFFSET.z);
        matrices.scale(f, -f, f);
    }

    static boolean shouldRender(BlockPos pos, int signColor) {
        if (signColor == DyeColor.BLACK.getTextColor()) {
            return true;
        } else {
            Minecraft minecraftClient = Minecraft.getInstance();
            LocalPlayer clientPlayerEntity = minecraftClient.player;
            if (clientPlayerEntity != null && minecraftClient.options.getCameraType().isFirstPerson() && clientPlayerEntity.isScoping()) {
                return true;
            } else {
                Entity entity = minecraftClient.getCameraEntity();
                return entity != null && entity.distanceToSqr(Vec3.atCenterOf(pos)) < Mth.square(16);
            }
        }
    }

    static int getColor(SignText sign) {
        int i = sign.getColor().getTextColor();
        if (i == DyeColor.BLACK.getTextColor() && sign.hasGlowingText()) {
            return -988212;
        } else {
            int j = (int)((double) FastColor.ARGB32.red(i) * 0.4);
            int k = (int)((double) FastColor.ARGB32.green(i) * 0.4);
            int l = (int)((double) FastColor.ARGB32.blue(i) * 0.4);
            return FastColor.ARGB32.color(0, j, k, l);
        }
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("sign", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -3.0F, -1.5F, 16.0F, 13.0F, 3.0F), PartPose.ZERO);
        modelPartData.addOrReplaceChild("top1", CubeListBuilder.create().texOffs(0, 16).addBox(-7.0F, 10.0F, -1.5F, 14.0F, 1.0F, 3.0F), PartPose.ZERO);
        modelPartData.addOrReplaceChild("top2", CubeListBuilder.create().texOffs(0, 20).addBox(-6.0F, 11.0F, -1.5F, 12.0F, 1.0F, 3.0F), PartPose.ZERO);
        modelPartData.addOrReplaceChild("top3", CubeListBuilder.create().texOffs(0, 24).addBox(-5.0F, 12.0F, -1.5F, 10.0F, 1.0F, 3.0F), PartPose.ZERO);
        modelPartData.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), PartPose.ZERO);
        return LayerDefinition.create(modelData, 64, 32);
    }
}