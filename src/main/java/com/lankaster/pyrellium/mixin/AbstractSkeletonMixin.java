package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonMixin {

    @Shadow
    public abstract void setItemSlot(EquipmentSlot slot, ItemStack stack);

    @Inject(method = "populateDefaultEquipmentSlots", at = @At(value = "TAIL"))
    protected void addCrystalArrows(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (random.nextDouble() < Config.instance().entities.skeleton_crystal_arrow_chance) {
            AbstractSkeleton entity = (AbstractSkeleton) (Object) this;
            if (entity instanceof Skeleton skeletonEntity && skeletonEntity.getCommandSenderWorld().dimension().location().toString().equals("minecraft:the_nether")) {
                setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(random.nextBoolean() ? ModItems.OPAL_ARROW.get() : ModItems.AMETHYST_ARROW.get(), random.nextInt(1, 4)));
            }
        }
    }
}
