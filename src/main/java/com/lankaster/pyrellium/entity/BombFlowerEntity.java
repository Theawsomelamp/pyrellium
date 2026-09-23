package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;

public class BombFlowerEntity extends ThrowableItemProjectile {
    public BombFlowerEntity(EntityType<? extends BombFlowerEntity> entityType, Level world) {
        super(entityType, world);
    }

    public BombFlowerEntity(Level world, LivingEntity owner) {
        super(ModEntities.BOMB_FLOWER.get(), owner, world);
    }

    public BombFlowerEntity(Level world, double x, double y, double z) {
        super(ModEntities.BOMB_FLOWER.get(), x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BOMB_FLOWER.get();
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.level().explode(null, this.getX(), this.getY(), this.getZ(), Config.instance().items.bomb_flower_explosion_strength, false, Level.ExplosionInteraction.NONE);
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }
}
