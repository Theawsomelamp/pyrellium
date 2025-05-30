package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.entity.ModBoatEntity;
import com.lankaster.pyrellium.entity.ModChestBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.function.Predicate;

public class ModBoatItem extends Item {
    private static final Predicate<Entity> RIDERS;
    private final ModBoatEntity.Type type;
    private final ModChestBoatEntity.Type chestype;
    private final boolean chest;

    public ModBoatItem(boolean chest, ModBoatEntity.Type type, ModChestBoatEntity.Type chestType, Item.Settings settings) {
        super(settings);
        this.chest = chest;
        this.type = type;
        this.chestype = chestType;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else {
            Vec3d vec3d = user.getRotationVec(1.0F);
            List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vec3d.multiply((double)5.0F)).expand((double)1.0F), RIDERS);
            if (!list.isEmpty()) {
                Vec3d vec3d2 = user.getEyePos();

                for(Entity entity : list) {
                    Box box = entity.getBoundingBox().expand((double)entity.getTargetingMargin());
                    if (box.contains(vec3d2)) {
                        return TypedActionResult.pass(itemStack);
                    }
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BoatEntity boatEntity = this.createEntity(world, hitResult);
                if (boatEntity instanceof ModBoatEntity modBoatEntity) {
                    modBoatEntity.setVariant(this.type);
                    modBoatEntity.setYaw(user.getYaw());
                } else if (boatEntity instanceof ModChestBoatEntity modBoatEntity) {
                    modBoatEntity.setVariant(this.chestype);
                    modBoatEntity.setYaw(user.getYaw());
                }

                if (!world.isSpaceEmpty(boatEntity, boatEntity.getBoundingBox())) {
                    return TypedActionResult.fail(itemStack);
                } else {
                    if (!world.isClient) {
                        world.spawnEntity(boatEntity);
                        world.emitGameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getPos());
                        if (!user.getAbilities().creativeMode) {
                            itemStack.decrement(1);
                        }
                    }

                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    return TypedActionResult.success(itemStack, world.isClient());
                }
            } else {
                return TypedActionResult.pass(itemStack);
            }
        }
    }

    private BoatEntity createEntity(World world, HitResult hitResult) {
        return (this.chest ? new ModChestBoatEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z) : new ModBoatEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z));
    }

    static {
        RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::canHit);
    }
}
