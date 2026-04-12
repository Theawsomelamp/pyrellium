package com.lankaster.pyrellium.util;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.render.MushroomCapRenderer;
import com.lankaster.pyrellium.render.OpalTiaraRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jspecify.annotations.NonNull;

public class HatRender implements ArmorRenderer {
    private static final OpalTiaraRenderer opalTiara = new OpalTiaraRenderer(OpalTiaraRenderer.getTexturedModelData().createModel());
    private static final MushroomCapRenderer mushroomCap = new MushroomCapRenderer(MushroomCapRenderer.getTexturedModelData().createModel());

    @Override
    public void render(@NonNull MatrixStack matrices, @NonNull OrderedRenderCommandQueue orderedRenderCommandQueue, ItemStack stack, @NonNull BipedEntityRenderState bipedEntityRenderState, @NonNull EquipmentSlot slot, int light, @NonNull BipedEntityModel<BipedEntityRenderState> contextModel) {

        if (!stack.isEmpty() && stack.isOf(ModItems.OPAL_TIARA)) {
            matrices.push();
            contextModel.getHead().applyTransform(matrices);
            matrices.scale(1.19F, 1.19F, 1.19F);
            orderedRenderCommandQueue.submitModel(opalTiara, bipedEntityRenderState, matrices, RenderLayers.armorCutoutNoCull(Identifier.of(Pyrellium.MOD_ID, "textures/item/opal_tiara_model.png")), light, OverlayTexture.DEFAULT_UV, -1, null, bipedEntityRenderState.outlineColor, null);
            matrices.pop();
        }

        if (!stack.isEmpty() && stack.isOf(ModItems.MUSHROOM_CAP)) {
            matrices.push();
            contextModel.getHead().applyTransform(matrices);
            matrices.scale(1.19F, 1.19F, 1.19F);
            orderedRenderCommandQueue.submitModel(mushroomCap, bipedEntityRenderState, matrices, RenderLayers.armorCutoutNoCull(Identifier.of(Pyrellium.MOD_ID, "textures/item/mushroom_cap_model.png")), light, OverlayTexture.DEFAULT_UV, -1, null, bipedEntityRenderState.outlineColor, null);
            matrices.pop();
        }
    }
}