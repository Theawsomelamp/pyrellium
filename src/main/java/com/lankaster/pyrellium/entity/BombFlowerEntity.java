package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.config.ConfigHandler;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BombFlowerEntity extends ThrownItemEntity {
    public BombFlowerEntity(EntityType<? extends BombFlowerEntity> entityType, World world) {
        super(entityType, world);
    }

    public BombFlowerEntity(World world, LivingEntity owner) {
        super(ModEntities.BOMB_FLOWER, owner, world);
    }

    public BombFlowerEntity(World world, double x, double y, double z) {
        super(ModEntities.BOMB_FLOWER, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BOMB_FLOWER;
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(null, this.getX(), this.getY(), this.getZ(), ConfigHandler.getConfig().blocksConfig().explodeStrength(), false, World.ExplosionSourceType.NONE);
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }
}
