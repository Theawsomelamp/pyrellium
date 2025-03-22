package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.armor.OpalTiaraItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.SpyglassItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item OPAL = registerItem("opal", new Item(new FabricItemSettings()));
    public static final Item OPAL_SPYGLASS = registerItem("opal_spyglass", new SpyglassItem(new FabricItemSettings().maxCount(1)));
    public static final Item OPAL_TIARA = registerItem("opal_tiara", new OpalTiaraItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        addToItemGroup(item);
        return Registry.register(Registries.ITEM, new Identifier(Pyrellium.MOD_ID, name), item);
    }

    private static void addToItemGroup(Item item) {
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.PYRELLIUM).register(entries -> entries.add(item));
    }

    public static void registerModItems() {

    }
}
