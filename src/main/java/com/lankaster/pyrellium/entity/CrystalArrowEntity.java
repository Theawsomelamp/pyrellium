package com.lankaster.pyrellium.entity;

import com.google.common.base.Predicates;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import com.lankaster.pyrellium.networking.OpalPayload;
import com.lankaster.pyrellium.particles.ModParticleTypes;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.EffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
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
        opal = !stack.isOf(ModItems.AMETHYST_ARROW);
        OpalPayload payload = new OpalPayload(opal);

        for (ServerPlayerEntity player : PlayerLookup.tracking(this)) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    public void tick() {
        super.tick();
        if (this.getEntityWorld().isClient()) {
            if (!this.isInGround()) {
                this.getEntityWorld().addParticleClient(EffectParticleEffect.of(ParticleTypes.INSTANT_EFFECT, -1, 1.0F), this.getX(), this.getY(), this.getZ(), (double) 0.0F, (double) 0.0F, (double) 0.0F);
            } else if (opal) {
                for (int i = 0; i < 8; ++i) {
                    this.getEntityWorld().addParticleClient(ModParticleTypes.OPAL_SHARD, this.getX(), this.getY(), this.getZ(), MathHelper.nextBetween(this.getEntityWorld().getRandom(), -1.0F, 1.0F), 0.05F, MathHelper.nextBetween(this.getEntityWorld().getRandom(), -1.0F, 1.0F));
                }
            } else {
                for (int i = 0; i < 8; ++i) {
                    this.getEntityWorld().addParticleClient(ModParticleTypes.AMETHYST_SHARD, this.getX(), this.getY(), this.getZ(), MathHelper.nextBetween(this.getEntityWorld().getRandom(), -1.0F, 1.0F), 0.05F, MathHelper.nextBetween(this.getEntityWorld().getRandom(), -1.0F, 1.0F));
                }
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.getEntityWorld().isClient()) {
            Entity entity = entityHitResult.getEntity();
            Vec3d pos = entity.getEntityPos();
            World world = getEntityWorld();
            int range = Config.instance().items.crystal_arrow_shatter_radius;
            Box box = new Box(pos.add(range, range, range), pos.add(-range, -range + 1, -range));
            for (Entity target : world.getEntitiesByClass(Entity.class, box, Predicates.alwaysTrue())) {
                if (target instanceof LivingEntity) {
                    if (world instanceof ServerWorld serverWorld) {
                        target.damage(serverWorld, target.getDamageSources().arrow(this, getOwner()), Config.instance().items.crystal_arrow_shatter_damage);
                    }
                }
            }
            world.playSound(null, pos.x ,pos.y, pos.z, SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.HOSTILE, 1.0F, 1.0F);
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.getEntityWorld().isClient()) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            World world = getEntityWorld();
            int range = Config.instance().items.crystal_arrow_shatter_radius;
            Box box = new Box(blockPos.add(range, range, range).toCenterPos(), blockPos.add(-range, -range + 1, -range).toCenterPos());
            for (Entity target : world.getEntitiesByClass(Entity.class, box, Predicates.alwaysTrue())) {
                if (target instanceof LivingEntity) {
                    if (world instanceof ServerWorld serverWorld) {
                        target.damage(serverWorld, target.getDamageSources().arrow(this, getOwner()), Config.instance().items.crystal_arrow_shatter_damage);
                    }
                }
            }
            world.playSound(null, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.HOSTILE);
        }
        super.onBlockHit(blockHitResult);
    }

    public void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putBoolean("Opal", opal);
    }

    public void readCustomData(ReadView view) {
        super.readCustomData(view);
        opal = view.getBoolean("Opal", false);
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
