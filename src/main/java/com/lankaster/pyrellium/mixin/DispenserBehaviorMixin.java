package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBehavior.class)
public interface DispenserBehaviorMixin {
    @Inject(method = "registerDefaults", at = @At("HEAD"))
    private static void addCustomArrows(CallbackInfo ci) {
        DispenserBlock.registerProjectileBehavior(ModItems.AMETHYST_ARROW);
        DispenserBlock.registerProjectileBehavior(ModItems.OPAL_ARROW);
    }
}
