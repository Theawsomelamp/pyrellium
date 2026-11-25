package com.lankaster.pyrellium.enchant;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchants {
    public static final Enchantment REBOUND = register("rebound", new ReboundEnchantment(Enchantment.Rarity.RARE, Config.instance().enchants.rebound.max_level));
    public static final Enchantment WIND_RIDER = register("wind_rider", new WindRiderEnchantment(Enchantment.Rarity.RARE, Config.instance().enchants.wind_rider.max_level));
    public static final Enchantment GLIDE = register("glide", new GlideEnchantment(Enchantment.Rarity.RARE, Config.instance().enchants.glide.max_level));
    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, Identifier.of(Pyrellium.MOD_ID, name), enchantment);
    }

    public static void registerEnchants() {

    }
}
