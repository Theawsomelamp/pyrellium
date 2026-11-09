package com.lankaster.pyrellium.compat;

import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.util.HatRender;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;

public class ClientTrinketsCompat {
    private static void registerTrinketRenderer(Item item) {
        TrinketRendererRegistry.registerRenderer(item, (stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch) -> {
            if (entity instanceof AbstractClientPlayerEntity) {
                HatRender.renderModels(matrices, vertexConsumers, stack, light, (BipedEntityModel<LivingEntity>) contextModel);
            }
        });
    }

    public static void init() {
        if (!Config.instance().compatibility.trinkets.render_equipment) return;

        registerTrinketRenderer(ModItems.OPAL_TIARA);
        registerTrinketRenderer(ModItems.MUSHROOM_CAP);
    }
}
