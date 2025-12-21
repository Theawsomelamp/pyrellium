package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.item.armor.MushroomCapItem;
import com.lankaster.pyrellium.item.armor.OpalTiaraItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item OPAL = registerItem("opal", new Item(new FabricItemSettings()));
    public static final Item OPAL_SPYGLASS = registerItem("opal_spyglass", new OpalSpyglassItem(new FabricItemSettings().maxCount(1)));
    public static final Item OPAL_TIARA = registerItem("opal_tiara", new OpalTiaraItem(new FabricItemSettings()));
    public static final Item AMETHYST_ARROW = registerItem("amethyst_arrow", new CrystalArrowItem(new FabricItemSettings()));
    public static final Item OPAL_ARROW = registerItem("opal_arrow", new CrystalArrowItem(new FabricItemSettings()));
    public static final Item BOMB_FLOWER = registerItem("bomb_flower", new BombFlowerItem((new Item.Settings()).maxCount(16)));
    public static final Item MUSHROOM_CAP = registerItem("mushroom_cap", new MushroomCapItem(new FabricItemSettings()));

    public static Item registerItem(String name, Item item) {
        addToItemGroup(item);
        return Registry.register(Registries.ITEM, new Identifier(Pyrellium.MOD_ID, name), item);
    }

    private static void addToItemGroup(Item item) {
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.PYRELLIUM).register(entries -> entries.add(item));
    }

    public static void registerModItems() {

    }
}
