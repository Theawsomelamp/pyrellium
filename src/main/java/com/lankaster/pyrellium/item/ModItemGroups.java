package com.lankaster.pyrellium.item;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ModItemGroups {
    public static final ResourceKey<CreativeModeTab> PYRELLIUM = ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "pyrellium"));

    public static void registerItemGroups() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, PYRELLIUM, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.OPAL_BLOCK))
                .title(Component.translatable("itemgroup.pyrellium")).build());
    }
}
