package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BombFlowerEntity extends ThrownItemEntity {
    public BombFlowerEntity(EntityType<? extends BombFlowerEntity> entityType, World world) {
        super(entityType, world);
    }

    public BombFlowerEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.BOMB_FLOWER, owner, world, stack);
    }

    public BombFlowerEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModEntities.BOMB_FLOWER, x, y, z, world, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BOMB_FLOWER;
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getEntityWorld().isClient()) {
            this.getEntityWorld().createExplosion(null, this.getX(), this.getY(), this.getZ(), Config.instance().items.bomb_flower_explosion_strength, false, World.ExplosionSourceType.NONE);
            this.getEntityWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }
}
