package com.lankaster.pyrellium.mixin.client;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.entity.ModBoatEntity;
import com.lankaster.pyrellium.entity.ModChestBoatEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoatEntityRenderer.class)
public class BoatEntityRenderMixin {
    @WrapOperation(method = "render(Lnet/minecraft/entity/vehicle/BoatEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/datafixers/util/Pair;getFirst()Ljava/lang/Object;"))
    private Object addCustomTexture(Pair<Identifier, CompositeEntityModel<BoatEntity>> instance, Operation<Identifier> original, BoatEntity boatEntity) {
        if (boatEntity instanceof ModBoatEntity) {
            return Identifier.of(Pyrellium.MOD_ID, "textures/entity/boat/burning.png");
        } else if (boatEntity instanceof ChestBoatEntity chestBoatEntity) {
            if (chestBoatEntity instanceof ModChestBoatEntity) {
                return Identifier.of(Pyrellium.MOD_ID, "textures/entity/chest_boat/burning.png");
            }
        }
        return instance.getFirst();
    }
}