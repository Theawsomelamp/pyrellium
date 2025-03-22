package com.lankaster.pyrellium.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkRegion.class)
public class WarningSuppressor {
    @Inject(method = "isValidForSetBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;error(Ljava/lang/String;)V", ordinal = 0), cancellable = true)
    private void isValidForSetBlock(BlockPos pos, CallbackInfoReturnable<Boolean> setBlockError) {
        setBlockError.setReturnValue(false);
    }
}
