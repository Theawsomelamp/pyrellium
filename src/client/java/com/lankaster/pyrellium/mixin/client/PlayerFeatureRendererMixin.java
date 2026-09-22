package com.lankaster.pyrellium.mixin.client;

import com.lankaster.pyrellium.item.ModItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerItemInHandLayer.class)
public abstract class PlayerFeatureRendererMixin {
    @WrapOperation(method = "renderArmWithItem",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z",ordinal = 0))
    public boolean renderOpalSpyglass(ItemStack instance, Item item, Operation<Boolean> original){
        return instance.is(ModItems.OPAL_SPYGLASS) || original.call(instance, item);
    }
}
