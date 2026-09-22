package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.entity.ModEntities;
import com.lankaster.pyrellium.item.armor.ModArmorMaterials;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModItems {
    public static final Item OPAL = registerItem("opal", new Item(new Item.Properties()));
    public static final Item OPAL_SPYGLASS = registerItem("opal_spyglass", new OpalSpyglassItem(new Item.Properties().stacksTo(1)));
    public static final Item OPAL_TIARA = registerItem("opal_tiara", new DescriptiveArmorItem(ModArmorMaterials.OPAL_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(5))));
    public static final Item AMETHYST_ARROW = registerItem("amethyst_arrow", new CrystalArrowItem(new Item.Properties()));
    public static final Item OPAL_ARROW = registerItem("opal_arrow", new CrystalArrowItem(new Item.Properties()));
    public static final Item BOMB_FLOWER = registerItem("bomb_flower", new BombFlowerItem((new Item.Properties()).stacksTo(16)));
    public static final Item MUSHROOM_CAP = registerItem("mushroom_cap", new DescriptiveArmorItem(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(7))));
    public static final Item GEODIN_SPAWN_EGG = registerItem("geodin_spawn_egg", new SpawnEggItem(ModEntities.GEODIN, 10066840, 5652608, new Item.Properties()));


    public static Item registerItem(String name, Item item) {
        addToItemGroup(item);
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, name), item);
    }

    private static void addToItemGroup(Item item) {
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.PYRELLIUM).register(entries -> entries.accept(item));
    }

    public static void registerModItems() {

    }
}
