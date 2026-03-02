package com.lankaster.pyrellium.mixin;

import com.google.gson.JsonSyntaxException;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Objects;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @ModifyVariable(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), argsOnly = true)
    private StatusEffectInstance poisonInHalf(StatusEffectInstance original) {

        RegistryEntry<StatusEffect> effect = original.getEffectType();

        for (String id : Config.instance().items.mushroom_cap_effects) {
            Optional<StatusEffect> blocked_effect = Registries.STATUS_EFFECT.getOrEmpty(Identifier.tryParse(id));
            if(blocked_effect.isEmpty()) {
                throw new JsonSyntaxException("Error reading status effect: could not find status effect with id: " + id);
            }

            if (Objects.equals(effect, Registries.STATUS_EFFECT.getEntry(blocked_effect.get())) && this.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.MUSHROOM_CAP)) {
                return new StatusEffectInstance(
                        effect,
                        (int) (original.getDuration() * Config.instance().items.mushroom_cap_effect_multiplier)
                );
            }
        }

        return original;
    }
}