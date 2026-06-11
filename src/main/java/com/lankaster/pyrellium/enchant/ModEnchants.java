package com.lankaster.pyrellium.enchant;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModEnchants {
    public static final RegistryKey<Enchantment> REBOUND = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Pyrellium.MOD_ID, "rebound"));

    public static void bootstrap(Registerable<Enchantment> registerable) {
        var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        register(registerable, REBOUND, Enchantment.builder(Enchantment.definition(
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        5,
                        Config.instance().enchants.rebound.max_level,
                        Enchantment.leveledCost(5, 7),
                        Enchantment.leveledCost(25, 9),
                        4,
                        AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantments.getOrThrow(TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Pyrellium.MOD_ID, "exclusive_set/rebound")))));
    }

    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }
}
