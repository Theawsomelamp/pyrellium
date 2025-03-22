package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> PYRELLIUM = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Pyrellium.MOD_ID, "pyrellium"));

    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP, PYRELLIUM, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.OPAL_BLOCK))
                .displayName(Text.translatable("itemgroup.pyrellium")).build());
    }
}
