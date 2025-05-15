package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin (StrayEntity.class)
public class StraySpawningMixin {
    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private static void SpawnInFrostBurnValley(EntityType<StrayEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if(world.getBiome(pos).matchesId(Identifier.of(Pyrellium.MOD_ID, "frostburn_valley"))) {
            cir.setReturnValue(true);
        }
    }
}