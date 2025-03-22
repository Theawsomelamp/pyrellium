package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.GhastEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GhastEntity.class)
public class GhastEntityMixin {
    @ModifyArg(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 3), index = 1)
    public Goal isWearingOpalTiara(Goal goal) {
        TargetPredicate predicate = ((ActiveTargetGoalAccessor) goal).getTargetPredicate();

        predicate.setPredicate(
                (LivingEntity entity) -> !entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.OPAL_TIARA)
        );
        return goal;
    }
}
