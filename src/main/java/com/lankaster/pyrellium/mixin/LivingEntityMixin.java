package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @ModifyVariable(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), argsOnly = true)
    private StatusEffectInstance poisonInHalf(StatusEffectInstance original) {

        RegistryEntry<StatusEffect> effect = original.getEffectType();

        if (effect == StatusEffects.POISON && this.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.MUSHROOM_CAP)) {
            return new StatusEffectInstance(
                    effect,
                    original.getDuration() / 2
            );
        }

        return original;
    }
}