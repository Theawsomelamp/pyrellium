package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenseItemBehavior.class)
public interface DispenseItemBehaviorMixin {
    @Inject(method = "bootStrap", at = @At("HEAD"))
    private static void addCustomArrows(CallbackInfo ci) {
        DispenserBlock.registerProjectileBehavior(ModItems.AMETHYST_ARROW);
        DispenserBlock.registerProjectileBehavior(ModItems.OPAL_ARROW);
    }
}
