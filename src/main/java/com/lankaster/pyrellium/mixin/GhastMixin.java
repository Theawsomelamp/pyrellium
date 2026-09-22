package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Ghast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Ghast.class)
public class GhastMixin {
    @ModifyArg(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 3), index = 1)
    public Goal isWearingOpalTiara(Goal goal) {
        TargetingConditions predicate = ((NearestAttackableTargetGoalAccessor) goal).getTargetConditions();

        predicate.selector(
                (LivingEntity entity) -> !entity.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.OPAL_TIARA)
        );
        return goal;
    }
}
