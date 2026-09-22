package com.lankaster.pyrellium.enchant;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

public class ModEnchants {
    public static final ResourceKey<Enchantment> REBOUND = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "rebound"));

    public static void bootstrap(BootstrapContext<Enchantment> registerable) {
        var enchantments = registerable.lookup(Registries.ENCHANTMENT);
        var items = registerable.lookup(Registries.ITEM);

        register(registerable, REBOUND, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        5,
                        Config.instance().enchants.rebound.max_level,
                        Enchantment.dynamicCost(5, 7),
                        Enchantment.dynamicCost(25, 9),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "exclusive_set/rebound")))));
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}
