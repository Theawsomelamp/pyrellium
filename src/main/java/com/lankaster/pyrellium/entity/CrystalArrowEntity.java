package com.lankaster.pyrellium.entity;

import com.google.common.base.Predicates;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.networking.OpalPayload;
import com.lankaster.pyrellium.particles.ModParticleTypes;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrystalArrowEntity extends PersistentProjectileEntity {
    public static boolean opal;

    public CrystalArrowEntity(EntityType<? extends CrystalArrowEntity> type, World world) {
        super(type, world);
    }

    public CrystalArrowEntity(World world, LivingEntity shooter, ItemStack itemStack, @Nullable ItemStack shotFrom) {
        super(ModEntities.CRYSTAL_ARROW, shooter, world, itemStack, shotFrom);
    }

    public CrystalArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(ModEntities.CRYSTAL_ARROW, x, y, z, world, stack, shotFrom);
    }

    public void initFromStack(ItemStack stack) {
        OpalPayload payload = new OpalPayload(!stack.isOf(ModItems.AMETHYST_ARROW));

        ServerPlayNetworking.send((ServerPlayerEntity) this.getOwner(), payload);
    }

    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            if (!this.inGround) {
                this.getWorld().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), (double) 0.0F, (double) 0.0F, (double) 0.0F);
            } else if (opal) {
                for (int i = 0; i < 8; ++i) {
                    this.getWorld().addParticle(ModParticleTypes.OPAL_SHARD, this.getX(), this.getY(), this.getZ(), MathHelper.nextBetween(this.getWorld().getRandom(), -1.0F, 1.0F), 0.05F, MathHelper.nextBetween(this.getWorld().getRandom(), -1.0F, 1.0F));
                }
            } else {
                for (int i = 0; i < 8; ++i) {
                    this.getWorld().addParticle(ModParticleTypes.AMETHYST_SHARD, this.getX(), this.getY(), this.getZ(), MathHelper.nextBetween(this.getWorld().getRandom(), -1.0F, 1.0F), 0.05F, MathHelper.nextBetween(this.getWorld().getRandom(), -1.0F, 1.0F));
                }
            }
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
            Box box = new Box(blockPos.add(2, 2, 2).toCenterPos(), blockPos.add(-2, -1, -2).toCenterPos());
            for (Entity target : world.getEntitiesByClass(Entity.class, box, Predicates.alwaysTrue())) {
                if (target instanceof LivingEntity) {
                    target.damage(target.getDamageSources().arrow(this, getOwner()), 2);
                }
            }
            world.playSound(null, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.HOSTILE);
        }
        super.onBlockHit(blockHitResult);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Opal", opal);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        opal = nbt.getBoolean("Opal");
    }

    @Override
    protected void age() {
        ++this.inGroundTime;
        if (this.inGroundTime >= 2) {
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

    @Override
    protected ItemStack getDefaultItemStack() {
        if (opal) {
            return new ItemStack(ModItems.OPAL_ARROW);
        } else {
            return new ItemStack(ModItems.AMETHYST_ARROW);
        }
    }
}
