package com.lankaster.pyrellium.compat;

import com.lankaster.pyrellium.config.Config;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.Optional;

public class TrinketsCompat {

    public static boolean isTrinketEquipped(Item equipment, LivingEntity entity) {
        // Nothing trinket related is called in this class as a whole yet, so this works here.
        if (!FabricLoader.getInstance().isModLoaded("trinkets") || !Config.instance().compatibility.trinkets.apply_equipment_effects) return false;

        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(entity);

        // Iterate over the player's Trinket inventory.
        if(component.isPresent()) {
            List<Pair<SlotReference, ItemStack>> allEquipped = component.get().getAllEquipped();
            for(Pair<SlotReference, ItemStack> entry : allEquipped) {
                if(entry.getRight().isOf(equipment)) {
                    return true;
                }
            }
        }

        return false;
    }
}
