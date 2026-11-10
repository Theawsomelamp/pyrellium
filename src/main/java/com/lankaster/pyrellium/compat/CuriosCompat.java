package com.lankaster.pyrellium.compat;

import com.lankaster.pyrellium.config.Config;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class CuriosCompat {

    public static boolean isCurioEquipped(Item equipment, LivingEntity entity) {
        if (!ModList.get().isLoaded("curios") || !Config.instance().compatibility.curios.apply_equipment_effects) return false;

        Optional<ICuriosItemHandler> component = CuriosApi.getCuriosInventory(entity);

        return component.map(handler -> handler.isEquipped(equipment)).orElse(false);

    }
}
