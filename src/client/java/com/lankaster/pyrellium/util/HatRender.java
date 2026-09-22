package com.lankaster.pyrellium.util;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.ItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class HatRender implements ArmorRenderer {
    private static final OpalTiaraRenderer<?> opalTiara = new OpalTiaraRenderer<>(OpalTiaraRenderer.getTexturedModelData().bakeRoot());
    private static final MushroomCapRenderer<?> mushroomCap = new MushroomCapRenderer<>(MushroomCapRenderer.getTexturedModelData().bakeRoot());

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity,
                       EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {

        if (!stack.isEmpty() && stack.is(ModItems.OPAL_TIARA)) {
            matrices.pushPose();
            contextModel.getHead().translateAndRotate(matrices);
            matrices.scale(1.19F, 1.19F, 1.19F);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, opalTiara.renderType(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/item/opal_tiara_model.png")), false);
            opalTiara.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, -1);
            matrices.popPose();
        }

        if (!stack.isEmpty() && stack.is(ModItems.MUSHROOM_CAP)) {
            matrices.pushPose();
            contextModel.getHead().translateAndRotate(matrices);
            matrices.scale(1.19F, 1.19F, 1.19F);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, mushroomCap.renderType(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/item/mushroom_cap_model.png")), false);
            mushroomCap.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, -1);
            matrices.popPose();
        }
    }
}