package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin (Stray.class)
public class StraySpawningMixin {
    @Inject(method = "checkStraySpawnRules", at = @At("HEAD"), cancellable = true)
    private static void SpawnInFrostBurnValley(EntityType<Stray> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> cir) {
        if(world.getBiome(pos).is(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "frostburn_valley"))) {
            cir.setReturnValue(true);
        }
    }
}
