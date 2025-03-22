package com.lankaster.pyrellium.mixin.client;

import com.lankaster.pyrellium.item.ModItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerHeldItemFeatureRenderer.class)
public abstract class PlayerFeatureRendererMixin {
    @WrapOperation(method = "renderItem",at = @At(value = "INVOKE",target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z",ordinal = 0))
    public boolean renderOpalSpyglass(ItemStack instance, Item item, Operation<Boolean> original){
        return instance.isOf(ModItems.OPAL_SPYGLASS) || original.call(instance, item);
    }
}
