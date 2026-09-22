package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.entity.ModBoatEntity;
import com.lankaster.pyrellium.entity.ModChestBoatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;
import java.util.function.Predicate;

public class ModBoatItem extends Item {
    private static final Predicate<Entity> RIDERS;
    private final ModBoatEntity.Type type;
    private final ModChestBoatEntity.Type chestype;
    private final boolean chest;

    public ModBoatItem(boolean chest, ModBoatEntity.Type type, ModChestBoatEntity.Type chestType, Item.Properties settings) {
        super(settings);
        this.chest = chest;
        this.type = type;
        this.chestype = chestType;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        HitResult hitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            Vec3 vec3d = user.getViewVector(1.0F);
            List<Entity> list = world.getEntities(user, user.getBoundingBox().expandTowards(vec3d.scale((double)5.0F)).inflate((double)1.0F), RIDERS);
            if (!list.isEmpty()) {
                Vec3 vec3d2 = user.getEyePosition();

                for(Entity entity : list) {
                    AABB box = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (box.contains(vec3d2)) {
                        return InteractionResultHolder.pass(itemStack);
                    }
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                Boat boatEntity = this.createEntity(world, hitResult);
                if (boatEntity instanceof ModBoatEntity modBoatEntity) {
                    modBoatEntity.setVariant(this.type);
                    modBoatEntity.setYRot(user.getYRot());
                } else if (boatEntity instanceof ModChestBoatEntity modBoatEntity) {
                    modBoatEntity.setVariant(this.chestype);
                    modBoatEntity.setYRot(user.getYRot());
                }

                if (!world.noCollision(boatEntity, boatEntity.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemStack);
                } else {
                    if (!world.isClientSide) {
                        world.addFreshEntity(boatEntity);
                        world.gameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getLocation());
                        if (!user.getAbilities().instabuild) {
                            itemStack.shrink(1);
                        }
                    }

                    user.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemStack);
            }
        }
    }

    private Boat createEntity(Level world, HitResult hitResult) {
        return (this.chest ? new ModChestBoatEntity(world, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z) : new ModBoatEntity(world, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z));
    }

    static {
        RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    }
}