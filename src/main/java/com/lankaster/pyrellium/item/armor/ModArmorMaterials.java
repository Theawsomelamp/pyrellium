package com.lankaster.pyrellium.item.armor;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final Holder<ArmorMaterial> OPAL_ARMOR_MATERIAL = registerArmorMaterial("opal", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 2);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
        map.put(ArmorItem.Type.BODY, 2);
    }), 24, SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(ModItems.OPAL), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "opal"))), 0, 0));

    public static final Holder<ArmorMaterial> MUSHROOM_ARMOR_MATERIAL = registerArmorMaterial("mushroom", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 2);
        map.put(ArmorItem.Type.CHESTPLATE, 3);
        map.put(ArmorItem.Type.HELMET, 1);
        map.put(ArmorItem.Type.BODY, 2);
    }), 16, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.RED_MUSHROOM), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "mushroom"))), 0, 0));

    public static Holder<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, name), material.get());
    }
}
