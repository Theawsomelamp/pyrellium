package com.lankaster.pyrellium.mixin;

import com.google.gson.JsonSyntaxException;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.enchant.ModEnchants;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "swingHand(Lnet/minecraft/util/Hand;Z)V", at = @At("HEAD"))
    public void applyRebound(Hand hand, boolean fromServerPlayer, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        RegistryEntry<Enchantment> entry = entity.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(ModEnchants.REBOUND);
        if (EnchantmentHelper.getEquipmentLevel(entry, entity) >= 1) {
            int level = (EnchantmentHelper.getEquipmentLevel(entry, entity));
            float velocityPerLevel = Config.instance().enchants.rebound.added_velocity_per_level;
            float range = Config.instance().enchants.rebound.rebound_range;
            Vec3d min = entity.getCameraPosVec(1.0f);
            Vec3d vec3d = entity.getRotationVec(1.0F);
            Vec3d max = min.add(vec3d.multiply(range));
            Box box = entity.getBoundingBox().stretch(vec3d.multiply(range)).expand(1.0F);
            EntityHitResult hitResult = ProjectileUtil.raycast(entity, min, max, box, (entityx) -> !(entityx instanceof LivingEntity), range);
            if (hitResult != null) {
                Entity target = hitResult.getEntity();
                Vec3d velocity = target.getVelocity();
                target.setVelocity(-MathHelper.sin(entity.getYaw() * ((float) Math.PI / 180F)) * (1 + (level * velocityPerLevel)) * velocity.horizontalLength(), (velocityPerLevel / 2) * level, MathHelper.cos(entity.getYaw() * ((float) Math.PI / 180F)) * (1 + (level * velocityPerLevel)) * velocity.horizontalLength());
            }
        }
    }
}