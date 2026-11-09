package com.lankaster.pyrellium.compat;

import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.util.HatRender;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.item.Item;

public class ClientTrinketsCompat {
    private static void registerTrinketRenderer(Item item) {
        TrinketRendererRegistry.registerRenderer(item, (stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance) -> {
            if (contextModel instanceof BipedEntityModel) {
                HatRender.renderModels(matrices, vertexConsumers, stack, (BipedEntityRenderState) entity, light, (BipedEntityModel<BipedEntityRenderState>) contextModel);
            }
        });
    }

    public static void init() {
        if (!Config.instance().compatibility.trinkets.render_equipment) return;

        registerTrinketRenderer(ModItems.OPAL_TIARA);
        registerTrinketRenderer(ModItems.MUSHROOM_CAP);
    }
}