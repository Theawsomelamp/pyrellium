package com.lankaster.pyrellium.entity;

import com.google.common.base.Predicates;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.networking.OpalPayload;
import com.lankaster.pyrellium.particles.ModParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class CrystalArrowEntity extends AbstractArrow {
    public static boolean opal;

    public CrystalArrowEntity(EntityType<? extends CrystalArrowEntity> type, Level world) {
        super(type, world);
    }

    public CrystalArrowEntity(Level world, LivingEntity shooter, ItemStack itemStack, @Nullable ItemStack shotFrom) {
        super(ModEntities.CRYSTAL_ARROW.get(), shooter, world, itemStack, shotFrom);
    }

    public CrystalArrowEntity(Level world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(ModEntities.CRYSTAL_ARROW.get(), x, y, z, world, stack, shotFrom);
    }

    public void initFromStack(ItemStack stack) {
        opal = !stack.is(ModItems.AMETHYST_ARROW.get());
        OpalPayload payload = new OpalPayload(opal);

        PacketDistributor.sendToPlayersTrackingEntity(this, payload);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (!this.inGround) {
                this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), (double) 0.0F, (double) 0.0F, (double) 0.0F);
            } else if (opal) {
                for (int i = 0; i < 8; ++i) {
                    this.level().addParticle(ModParticleTypes.OPAL_SHARD.get(), this.getX(), this.getY(), this.getZ(), Mth.randomBetween(this.level().getRandom(), -1.0F, 1.0F), 0.05F, Mth.randomBetween(this.level().getRandom(), -1.0F, 1.0F));
                }
            } else {
                for (int i = 0; i < 8; ++i) {
                    this.level().addParticle(ModParticleTypes.AMETHYST_SHARD.get(), this.getX(), this.getY(), this.getZ(), Mth.randomBetween(this.level().getRandom(), -1.0F, 1.0F), 0.05F, Mth.randomBetween(this.level().getRandom(), -1.0F, 1.0F));
                }
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (!this.level().isClientSide()) {
            Entity entity = entityHitResult.getEntity();
            Vec3 pos = entity.position();
            Level world = level();
            int range = Config.instance().items.crystal_arrow_shatter_radius;
            AABB box = new AABB(pos.add(range, range, range), pos.add(-range, -range + 1, -range));
            for (Entity target : world.getEntitiesOfClass(Entity.class, box, Predicates.alwaysTrue())) {
                if (target instanceof LivingEntity) {
                    target.hurt(target.damageSources().arrow(this, getOwner()), Config.instance().items.crystal_arrow_shatter_damage);
                }
            }
            world.playSound(null, pos.x ,pos.y, pos.z, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.HOSTILE, 1.0F, 1.0F);
        }
        super.onHitEntity(entityHitResult);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (!this.level().isClientSide()) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Level world = level();
            int range = Config.instance().items.crystal_arrow_shatter_radius;
            AABB box = new AABB(blockPos.offset(range, range, range).getCenter(), blockPos.offset(-range, -range + 1, -range).getCenter());
            for (Entity target : world.getEntitiesOfClass(Entity.class, box, Predicates.alwaysTrue())) {
                if (target instanceof LivingEntity) {
                    target.hurt(target.damageSources().arrow(this, getOwner()), Config.instance().items.crystal_arrow_shatter_damage);
                }
            }
            world.playSound(null, blockPos, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.HOSTILE);
        }
        super.onHitBlock(blockHitResult);
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Opal", opal);
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        opal = nbt.getBoolean("Opal");
    }

    @Override
    protected void tickDespawn() {
        ++this.inGroundTime;
        if (this.inGroundTime >= 2) {
            this.discard();
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        if (opal) {
            return new ItemStack(ModItems.OPAL_ARROW.get());
        } else {
            return new ItemStack(ModItems.AMETHYST_ARROW.get());
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        if (opal) {
            return new ItemStack(ModItems.OPAL_ARROW.get());
        } else {
            return new ItemStack(ModItems.AMETHYST_ARROW.get());
        }
    }
}
