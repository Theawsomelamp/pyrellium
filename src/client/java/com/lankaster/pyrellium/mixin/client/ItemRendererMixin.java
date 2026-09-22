package com.lankaster.pyrellium.mixin.client;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.ModItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @Shadow
    public abstract ItemModelShaper getItemModelShaper();

    @ModifyVariable(
            method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel renderItem(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ItemDisplayContext renderMode) {
        if (stack.is(ModItems.OPAL_TIARA) && renderMode == ItemDisplayContext.HEAD) {
            return getItemModelShaper().getModelManager().getModel(ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "opal_tiara_model")));
        } else if (stack.getItem() == ModItems.OPAL_SPYGLASS && (renderMode == ItemDisplayContext.GUI || renderMode == ItemDisplayContext.GROUND || renderMode == ItemDisplayContext.FIXED)) {
            return getItemModelShaper().getModelManager().getModel(ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "opal_spyglass")));
        }

        return bakedModel;
    }

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() == ModItems.OPAL_SPYGLASS) {
            return this.itemModelShaper.getModelManager().getModel(ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "opal_spyglass_model")));
        }

        return bakedModel;
    }
}
