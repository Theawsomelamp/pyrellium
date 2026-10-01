package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin {

    @Shadow
    public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

    @Inject(method = "initEquipment", at = @At(value = "TAIL"))
    protected void addCrystalArrows(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        if (random.nextDouble() <= Config.instance().entities.skeleton_crystal_arrow_chance) {
            AbstractSkeletonEntity entity = (AbstractSkeletonEntity) (Object) this;
            if (entity instanceof SkeletonEntity skeletonEntity && skeletonEntity.getEntityWorld().getRegistryKey().getValue().toString().equals("minecraft:the_nether")) {
                equipStack(EquipmentSlot.OFFHAND, new ItemStack(random.nextBoolean() ? ModItems.OPAL_ARROW : ModItems.AMETHYST_ARROW, random.nextBetween(1, 4)));
            }
        }
    }
}
