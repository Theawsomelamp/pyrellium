package com.lankaster.pyrellium.mixin.client;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.entity.ModBoatEntity;
import com.lankaster.pyrellium.entity.ModChestBoatEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.model.ListModel;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoatRenderer.class)
public class BoatEntityRenderMixin {
    @WrapOperation(method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/datafixers/util/Pair;getFirst()Ljava/lang/Object;"))
    private Object addCustomTexture(Pair<ResourceLocation, ListModel<Boat>> instance, Operation<ResourceLocation> original, Boat boatEntity) {
        if (boatEntity instanceof ModBoatEntity modBoatEntity) {
            return ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/entity/boat/" + modBoatEntity.getCustomVariant().getSerializedName() + ".png");
        } else if (boatEntity instanceof ChestBoat chestBoatEntity) {
            if (chestBoatEntity instanceof ModChestBoatEntity modChestBoatEntity) {
                return ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "textures/entity/chest_boat/" + modChestBoatEntity.getCustomVariant().getSerializedName() + ".png");
            }
        }
        return instance.getFirst();
    }
}