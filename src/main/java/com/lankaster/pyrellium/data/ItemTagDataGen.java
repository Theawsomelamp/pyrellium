package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class ItemTagDataGen extends FabricTagProvider.ItemTagProvider{

    public ItemTagDataGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.ARROWS).add(
                ModItems.AMETHYST_ARROW,
                ModItems.OPAL_ARROW
        );

        getOrCreateTagBuilder(ItemTags.PLANKS).add(
                ModBlocks.BURNING_PLANKS.asItem(),
                ModBlocks.SHADEROOT_PLANKS.asItem()
        );

        getOrCreateTagBuilder(ItemTags.WOODEN_SLABS).add(
                ModBlocks.BURNING_SLAB.asItem(),
                ModBlocks.SHADEROOT_SLAB.asItem()
        );

        getOrCreateTagBuilder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
                ModBlocks.DRAINED_SOUL_SOIL.asItem()
        );

        getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "burning_logs"))).add(
                ModBlocks.BURNING_LOG.asItem(),
                ModBlocks.BURNING_WOOD.asItem(),
                ModBlocks.STRIPPED_BURNING_LOG.asItem(),
                ModBlocks.STRIPPED_BURNING_WOOD.asItem()
        );

        getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "shaderoot_logs"))).add(
                ModBlocks.SHADEROOT_LOG.asItem(),
                ModBlocks.SHADEROOT_WOOD.asItem(),
                ModBlocks.STRIPPED_SHADEROOT_LOG.asItem(),
                ModBlocks.STRIPPED_SHADEROOT_WOOD.asItem()
        );

        getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "crystals"))).add(
                Items.AMETHYST_SHARD,
                ModItems.OPAL,
                Items.QUARTZ
        );

        getOrCreateTagBuilder(ConventionalItemTags.BUDDING_BLOCKS).add(
                ModBlocks.BUDDING_OPAL.asItem(),
                ModBlocks.BUDDING_QUARTZ.asItem()
        );

        getOrCreateTagBuilder(ConventionalItemTags.BUDS).add(
                ModBlocks.SMALL_OPAL_BUD.asItem(),
                ModBlocks.MEDIUM_OPAL_BUD.asItem(),
                ModBlocks.LARGE_OPAL_BUD.asItem(),
                ModBlocks.SMALL_QUARTZ_BUD.asItem(),
                ModBlocks.MEDIUM_QUARTZ_BUD.asItem(),
                ModBlocks.LARGE_QUARTZ_BUD.asItem()
        );

        getOrCreateTagBuilder(ConventionalItemTags.CLUSTERS).add(
                ModBlocks.OPAL_CLUSTER.asItem(),
                ModBlocks.QUARTZ_CRYSTAL.asItem()
        );

        getOrCreateTagBuilder(ConventionalItemTags.GEMS).add(
                ModItems.OPAL
        );
    }
}