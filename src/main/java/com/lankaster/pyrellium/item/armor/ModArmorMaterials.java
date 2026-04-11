package com.lankaster.pyrellium.item.armor;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;

public class ModArmorMaterials {
    public static final RegistryKey<EquipmentAsset> OPAL_ARMOR_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Pyrellium.MOD_ID, "opal"));
    public static final RegistryKey<EquipmentAsset> MUSHROOM_ARMOR_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Pyrellium.MOD_ID, "mushroom"));

    public static final ArmorMaterial OPAL_ARMOR_MATERIAL = new ArmorMaterial(56, Util.make(new EnumMap<>(EquipmentType.class), map -> {
        map.put(EquipmentType.BOOTS, 1);
        map.put(EquipmentType.LEGGINGS, 2);
        map.put(EquipmentType.CHESTPLATE, 3);
        map.put(EquipmentType.HELMET, 1);
        map.put(EquipmentType.BODY, 2);
    }), 20, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,0,0, TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "opal_repair")),
            OPAL_ARMOR_KEY);

    public static final ArmorMaterial MUSHROOM_ARMOR_MATERIAL = new ArmorMaterial(74, Util.make(new EnumMap<>(EquipmentType.class), map -> {
        map.put(EquipmentType.BOOTS, 1);
        map.put(EquipmentType.LEGGINGS, 2);
        map.put(EquipmentType.CHESTPLATE, 3);
        map.put(EquipmentType.HELMET, 1);
        map.put(EquipmentType.BODY, 2);
    }), 20, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,0,0, TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "mushroom_repair")),
            MUSHROOM_ARMOR_KEY);
}
