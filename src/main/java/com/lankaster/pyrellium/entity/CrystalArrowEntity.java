package com.lankaster.pyrellium.entity;

import com.google.common.base.Predicates;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CrystalArrowEntity extends PersistentProjectileEntity {
    public boolean opal = false;

    public CrystalArrowEntity(EntityType<? extends CrystalArrowEntity> type, World world) {
        super(type, world);
    }

    public CrystalArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.CRYSTAL_ARROW, owner, world);
    }

    public CrystalArrowEntity(World world, double x, double y, double z) {
        super(ModEntities.CRYSTAL_ARROW, x, y, z, world);
    }

    public void initFromStack(ItemStack stack) {
        this.opal = !stack.isOf(ModItems.AMETHYST_ARROW);
    }

    public void tick() {
        super.tick();
        if (this.getWorld().isClient && !this.inGround) {
            this.getWorld().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.getWorld().isClient()) {
            Entity entity = entityHitResult.getEntity();
            Vec3d pos = entity.getPos();
            World world = getWorld();
            Box box = new Box(pos.add(2, 2, 2), pos.add(-2, -1, -2));
            for (Entity target : world.getEntitiesByClass(Entity.class, box, Predicates.alwaysTrue())) {
                if (target instanceof LivingEntity) {
                    target.damage(target.getDamageSources().arrow(this, getOwner()), 2);
                }
            }
            world.playSound(null, pos.x ,pos.y, pos.z, SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.HOSTILE, 1.0F, 1.0F);
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.getWorld().isClient()) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            World world = getWorld();
            Box box = new Box(blockPos.add(2, 2, 2), blockPos.add(-2, -1, -2));
            for (Entity target : world.getEntitiesByClass(Entity.class, box, Predicates.alwaysTrue())) {
                if (target instanceof LivingEntity) {
                    target.damage(target.getDamageSources().arrow(this, getOwner()), 2);
                }
            }
            world.playSound(null, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.HOSTILE);
        }
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void age() {
        ++this.inGroundTime;
        if (this.inGroundTime >= 1) {
            this.discard();
        }
    }

    @Override
    protected ItemStack asItemStack() {
        if (opal) {
            return new ItemStack(ModItems.OPAL_ARROW);
        } else {
            return new ItemStack(ModItems.AMETHYST_ARROW);
        }
    }
}
