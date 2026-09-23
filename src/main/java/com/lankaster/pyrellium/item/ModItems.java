package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.entity.ModEntities;
import com.lankaster.pyrellium.item.armor.ModArmorMaterials;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Pyrellium.MOD_ID);

    public static final DeferredItem<Item> OPAL = registerItem("opal", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OPAL_SPYGLASS = registerItem("opal_spyglass", () -> new OpalSpyglassItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> OPAL_TIARA = registerItem("opal_tiara", () -> new DescriptiveArmorItem(ModArmorMaterials.OPAL_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(5))));
    public static final DeferredItem<Item> AMETHYST_ARROW = registerItem("amethyst_arrow", () -> new CrystalArrowItem(new Item.Properties()));
    public static final DeferredItem<Item> OPAL_ARROW = registerItem("opal_arrow", () -> new CrystalArrowItem(new Item.Properties()));
    public static final DeferredItem<Item> BOMB_FLOWER = registerItem("bomb_flower", () -> new BombFlowerItem((new Item.Properties()).stacksTo(16)));
    public static final DeferredItem<Item> MUSHROOM_CAP = registerItem("mushroom_cap", () -> new DescriptiveArmorItem(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(7))));
    public static final DeferredItem<Item> GEODIN_SPAWN_EGG = registerItem("geodin_spawn_egg", () -> new DeferredSpawnEggItem(ModEntities.GEODIN, 10066840, 5652608, new Item.Properties()));


    public static <T extends Item> DeferredItem<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static void registerModItems(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
