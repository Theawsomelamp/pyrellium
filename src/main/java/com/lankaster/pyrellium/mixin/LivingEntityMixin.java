package com.lankaster.pyrellium.mixin;

import com.google.gson.JsonSyntaxException;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.enchant.ModEnchants;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
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
    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @ModifyVariable(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), argsOnly = true)
    private MobEffectInstance poisonInHalf(MobEffectInstance original) {

        Holder<MobEffect> effect = original.getEffect();

        for (String id : Config.instance().items.mushroom_cap_effects) {
            Optional<MobEffect> blocked_effect = BuiltInRegistries.MOB_EFFECT.getOptional(ResourceLocation.tryParse(id));
            if(blocked_effect.isEmpty()) {
                throw new JsonSyntaxException("Error reading status effect: could not find status effect with id: " + id);
            }

            if (Objects.equals(effect, BuiltInRegistries.MOB_EFFECT.wrapAsHolder(blocked_effect.get())) && this.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MUSHROOM_CAP)) {
                return new MobEffectInstance(
                        effect,
                        (int) (original.getDuration() * Config.instance().items.mushroom_cap_effect_multiplier)
                );
            }
        }

        return original;
    }

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"))
    public void applyRebound(InteractionHand hand, boolean fromServerPlayer, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        Holder<Enchantment> entry = entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchants.REBOUND);
        if (EnchantmentHelper.getEnchantmentLevel(entry, entity) >= 1) {
            int level = (EnchantmentHelper.getEnchantmentLevel(entry, entity));
            float velocityPerLevel = Config.instance().enchants.rebound.added_velocity_per_level;
            float range = Config.instance().enchants.rebound.rebound_range;
            Vec3 min = entity.getEyePosition(1.0f);
            Vec3 vec3d = entity.getViewVector(1.0F);
            Vec3 max = min.add(vec3d.scale(range));
            AABB box = entity.getBoundingBox().expandTowards(vec3d.scale(range)).inflate(1.0F);
            EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(entity, min, max, box, (entityx) -> !(entityx instanceof LivingEntity), range);
            if (hitResult != null) {
                Entity target = hitResult.getEntity();
                Vec3 velocity = target.getDeltaMovement();
                if (!(target instanceof AbstractArrow) || (target instanceof AbstractArrow projectileEntity && !((AbstractArrowAccessor) projectileEntity).getInGround())) {
                    target.setDeltaMovement(-Mth.sin(entity.getYRot() * ((float) Math.PI / 180F)) * (1 + (level * velocityPerLevel)) * velocity.horizontalDistance(), (velocityPerLevel / 2) * level, Mth.cos(entity.getYRot() * ((float) Math.PI / 180F)) * (1 + (level * velocityPerLevel)) * velocity.horizontalDistance());
                    target.getCommandSenderWorld().addParticle(ParticleTypes.SWEEP_ATTACK, target.getX(), target.getY(), target.getZ(), 0.0F, 0.0F, 0.0F);
                    target.getCommandSenderWorld().playSound(null, target.blockPosition(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 0.8F, 0.5F);
                }
            }
        }
    }
}