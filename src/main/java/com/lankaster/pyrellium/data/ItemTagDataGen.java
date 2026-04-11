package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ItemTagDataGen extends FabricTagProvider.ItemTagProvider{

    public ItemTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.@NonNull WrapperLookup wrapperLookup) {
        valueLookupBuilder(ItemTags.ARROWS).add(
                ModItems.AMETHYST_ARROW,
                ModItems.OPAL_ARROW
        );

        valueLookupBuilder(ItemTags.PLANKS).add(
                ModBlocks.BURNING_PLANKS.asItem(),
                ModBlocks.SHADEROOT_PLANKS.asItem()
        );

        valueLookupBuilder(ItemTags.WOODEN_SLABS).add(
                ModBlocks.BURNING_SLAB.asItem(),
                ModBlocks.SHADEROOT_SLAB.asItem()
        );

        valueLookupBuilder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
                ModBlocks.DRAINED_SOUL_SOIL.asItem()
        );

        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "burning_logs"))).add(
                ModBlocks.BURNING_LOG.asItem(),
                ModBlocks.BURNING_WOOD.asItem(),
                ModBlocks.STRIPPED_BURNING_LOG.asItem(),
                ModBlocks.STRIPPED_BURNING_WOOD.asItem()
        );

        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "shaderoot_logs"))).add(
                ModBlocks.SHADEROOT_LOG.asItem(),
                ModBlocks.SHADEROOT_WOOD.asItem(),
                ModBlocks.STRIPPED_SHADEROOT_LOG.asItem(),
                ModBlocks.STRIPPED_SHADEROOT_WOOD.asItem()
        );

        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "opal_repair"))).add(
                ModItems.OPAL
        );

        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(Pyrellium.MOD_ID, "mushroom_repair"))).add(
                Items.RED_MUSHROOM
        );
    }
}