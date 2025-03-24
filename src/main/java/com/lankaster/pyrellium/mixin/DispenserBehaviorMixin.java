package com.lankaster.pyrellium.mixin;

import com.lankaster.pyrellium.entity.CrystalArrowEntity;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBehavior.class)
public interface DispenserBehaviorMixin {
    @Inject(method = "registerDefaults", at = @At("HEAD"))
    private static void addCustomArrows(CallbackInfo ci) {
        DispenserBlock.registerBehavior(ModItems.AMETHYST_ARROW, new ProjectileDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                CrystalArrowEntity crystalArrowEntity = new CrystalArrowEntity(world, position.getX(), position.getY(), position.getZ());
                crystalArrowEntity.initFromStack(stack);
                crystalArrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
                return crystalArrowEntity;
            }
        });
        DispenserBlock.registerBehavior(ModItems.OPAL_ARROW, new ProjectileDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                CrystalArrowEntity crystalArrowEntity = new CrystalArrowEntity(world, position.getX(), position.getY(), position.getZ());
                crystalArrowEntity.initFromStack(stack);
                crystalArrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
                return crystalArrowEntity;
            }
        });
    }
}
