package com.lankaster.pyrellium.client.util;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class HatRender<T extends LivingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {
    private static final OpalTiaraRenderer<?> opalTiara = new OpalTiaraRenderer<>(OpalTiaraRenderer.getTexturedModelData().bakeRoot());
    private static final MushroomCapRenderer<?> mushroomCap = new MushroomCapRenderer<>(MushroomCapRenderer.getTexturedModelData().bakeRoot());

    public HatRender(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        for (ItemStack stack : livingEntity.getArmorSlots()) {
            if (!stack.isEmpty() && stack.is(ModItems.OPAL_TIARA)) {
                poseStack.pushPose();
                this.getParentModel().getHead().translateAndRotate(poseStack);
                poseStack.scale(1.19F, 1.19F, 1.19F);
                VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(bufferSource, opalTiara.renderType(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/item/opal_tiara_model.png")), false);
                opalTiara.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
                poseStack.popPose();
            }

            if (!stack.isEmpty() && stack.is(ModItems.MUSHROOM_CAP)) {
                poseStack.pushPose();
                this.getParentModel().getHead().translateAndRotate(poseStack);
                poseStack.scale(1.19F, 1.19F, 1.19F);
                VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(bufferSource, mushroomCap.renderType(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/item/mushroom_cap_model.png")), false);
                mushroomCap.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
                poseStack.popPose();
            }
        }
    }
}