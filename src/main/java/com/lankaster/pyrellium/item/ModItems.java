package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.armor.ModArmorMaterials;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final Item OPAL = registerItem("opal", Item::new);
    public static final Item OPAL_SPYGLASS = registerItem("opal_spyglass", setting -> new OpalSpyglassItem(setting.maxCount(1)));
    public static final Item OPAL_TIARA = registerItem("opal_tiara", setting -> new DescriptiveItem(setting.armor(ModArmorMaterials.OPAL_ARMOR_MATERIAL, EquipmentType.HELMET)));
    public static final Item AMETHYST_ARROW = registerItem("amethyst_arrow", CrystalArrowItem::new);
    public static final Item OPAL_ARROW = registerItem("opal_arrow", CrystalArrowItem::new);
    public static final Item BOMB_FLOWER = registerItem("bomb_flower", setting -> new BombFlowerItem((setting.maxCount(16))));
    public static final Item MUSHROOM_CAP = registerItem("mushroom_cap", setting -> new DescriptiveItem(setting.armor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, EquipmentType.HELMET)));


    public static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, Identifier.of(Pyrellium.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, name)))));
    }

    private static void addToItemGroup(Item item) {
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.PYRELLIUM).register(entries -> entries.add(item));
    }

    public static void registerModItems() {

    }
}
