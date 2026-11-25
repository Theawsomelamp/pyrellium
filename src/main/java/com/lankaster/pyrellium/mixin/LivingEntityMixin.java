package com.lankaster.pyrellium.mixin;

import com.google.gson.JsonSyntaxException;
import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.compat.TrinketsCompat;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.enchant.ModEnchants;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Unique
    int flyingTicks = 0;

    @Unique
    private boolean isHoldingUmbrella() {
        return (getEquippedStack(EquipmentSlot.MAINHAND).isOf(ModItems.SILK_UMBRELLA) || getEquippedStack(EquipmentSlot.OFFHAND).isOf(ModItems.SILK_UMBRELLA));
    }

    @Unique
    private EquipmentSlot getUmbrellaHand() {
        if (getEquippedStack(EquipmentSlot.MAINHAND).isOf(ModItems.SILK_UMBRELLA)) {
            return EquipmentSlot.MAINHAND;
        }
        return EquipmentSlot.OFFHAND;
    }

    @ModifyVariable(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), argsOnly = true)
    private StatusEffectInstance poisonInHalf(StatusEffectInstance original) {

        StatusEffect effect = original.getEffectType();

        for (String id : Config.instance().items.mushroom_cap_effects) {
            Optional<StatusEffect> blocked_effect = Registries.STATUS_EFFECT.getOrEmpty(Identifier.tryParse(id));
            if(blocked_effect.isEmpty()) {
                throw new JsonSyntaxException("Error reading status effect: could not find status effect with id: " + id);
            }

            if (effect == blocked_effect.get() && (this.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.MUSHROOM_CAP) || TrinketsCompat.isTrinketEquipped(ModItems.MUSHROOM_CAP, (LivingEntity) (Object) this))) {
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
        if (EnchantmentHelper.getEquipmentLevel(ModEnchants.REBOUND, entity) >= 1) {
            int level = (EnchantmentHelper.getEquipmentLevel(ModEnchants.REBOUND, entity));
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
                if (!(target instanceof PersistentProjectileEntity) || (target instanceof PersistentProjectileEntity projectileEntity && !((PersistentProjectileEntityAccessor) projectileEntity).getInGround())) {
                    target.setVelocity(-MathHelper.sin(entity.getYaw() * ((float) Math.PI / 180F)) * (1 + (level * velocityPerLevel)) * velocity.horizontalLength(), (velocityPerLevel / 2) * level, MathHelper.cos(entity.getYaw() * ((float) Math.PI / 180F)) * (1 + (level * velocityPerLevel)) * velocity.horizontalLength());
                    target.getEntityWorld().addParticle(ParticleTypes.SWEEP_ATTACK, target.getX(), target.getY(), target.getZ(), 0.0F, 0.0F, 0.0F);
                    target.getEntityWorld().playSound(null, target.getBlockPos(), SoundEvents.ITEM_TRIDENT_HIT, SoundCategory.PLAYERS, 0.8F, 0.5F);
                }
            }
        }
    }

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    public double modifyFallingVelocity(double d) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        if(entity.getVelocity().y > 0D) {
            return d;
        }

        if(isHoldingUmbrella() || (entity.isFallFlying() && Config.instance().misc.elytra_catch_updrafts)) {
            for(int i = -1; i < 5; ++i) {
                BlockState state = entity.getEntityWorld().getBlockState(entity.getBlockPos().down(i + 1));
                double velocity = -Config.instance().misc.updraft_velocity;
                if (state.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of(Pyrellium.MOD_ID, "updraft_blocks")))) {
                    Optional<Boolean> lit = state.getOrEmpty(Properties.LIT);
                    if (lit.isPresent() && lit.get() == false) {
                        return d;
                    }

                    entity.onLanding();
                    entity.getEntityWorld().playSound(null, entity.getBlockPos(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.PLAYERS, 0.4F, 2.0F);

                    if (EnchantmentHelper.getEquipmentLevel(ModEnchants.WIND_RIDER, entity) >= 1) {
                        int level = (EnchantmentHelper.getEquipmentLevel(ModEnchants.WIND_RIDER, entity));
                        float velocityPerLevel = Config.instance().enchants.wind_rider.added_updraft_velocity_per_level;
                        velocity -= (level * velocityPerLevel);
                    }

                    return entity.isFallFlying() ? velocity * 2 : velocity;
                } else if (!state.isAir()) {
                    i = 6;
                }
            }

            if (isHoldingUmbrella()) {
                entity.limitFallDistance();
                boolean hasGlide = EnchantmentHelper.getEquipmentLevel(ModEnchants.GLIDE, entity) >= 1;
                return hasGlide ? 0.005 : Math.max(d - (entity.fallDistance / 10), 0.01);
            }
        }
        return d;
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    public void tickSilkUmbrella(CallbackInfo ci) {
        if (isHoldingUmbrella()) {
            LivingEntity entity = ((LivingEntity) (Object) this);
            ItemStack umbrella = getEquippedStack(getUmbrellaHand());
            flyingTicks++;
            if (!entity.isOnGround()) {
                if (!entity.getWorld().isClient && flyingTicks % 80 == 0) {
                    umbrella.damage(1, entity, player -> player.sendEquipmentBreakStatus(getUmbrellaHand()));
                }
            } else {
                flyingTicks = 0;
            }
        }
    }
}
