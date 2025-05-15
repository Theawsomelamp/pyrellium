package com.lankaster.pyrellium.util;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class HatRender implements ArmorRenderer {
    private static final com.lankaster.pyrellium.util.HatRenderer<?> model = new HatRenderer<>(HatRenderer.getTexturedModelData().createModel());

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity,
                       EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {

        if (!stack.isEmpty() && stack.isOf(ModItems.OPAL_TIARA)) {
            matrices.push();
            contextModel.getHead().rotate(matrices);
            matrices.scale(1.19F, 1.19F, 1.19F);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, model.getLayer(Identifier.of(Pyrellium.MOD_ID, "textures/item/opal_tiara_model.png")), false);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
            matrices.pop();
        }
    }
}