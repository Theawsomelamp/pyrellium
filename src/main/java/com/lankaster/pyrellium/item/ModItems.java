package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.armor.ModArmorMaterials;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpyglassItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item OPAL = registerItem("opal", new Item(new Item.Settings()));
    public static final Item OPAL_SPYGLASS = registerItem("opal_spyglass", new SpyglassItem(new Item.Settings().maxCount(1)));
    public static final Item OPAL_TIARA = registerItem("opal_tiara", new ArmorItem(ModArmorMaterials.OPAL_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))));
    public static final Item AMETHYST_ARROW = registerItem("amethyst_arrow", new CrystalArrowItem(new Item.Settings()));
    public static final Item OPAL_ARROW = registerItem("opal_arrow", new CrystalArrowItem(new Item.Settings()));
    public static final Item BOMB_FLOWER = registerItem("bomb_flower", new BombFlowerItem((new Item.Settings()).maxCount(16)));


    public static Item registerItem(String name, Item item) {
        addToItemGroup(item);
        return Registry.register(Registries.ITEM, Identifier.of(Pyrellium.MOD_ID, name), item);
    }

    private static void addToItemGroup(Item item) {
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.PYRELLIUM).register(entries -> entries.add(item));
    }

    public static void registerModItems() {

    }
}
