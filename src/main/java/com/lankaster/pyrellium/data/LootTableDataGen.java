package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class LootTableDataGen extends FabricBlockLootTableProvider {
    public LootTableDataGen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(ModBlocks.OPAL_BLOCK);
        dropWhenSilkTouch(ModBlocks.SMALL_OPAL_BUD);
        dropWhenSilkTouch(ModBlocks.MEDIUM_OPAL_BUD);
        dropWhenSilkTouch(ModBlocks.LARGE_OPAL_BUD);
        add(ModBlocks.BUDDING_OPAL, noDrop());
        dropSelf(ModBlocks.CLEAR_AMETHYST_BLOCK);
        dropSelf(ModBlocks.CLEAR_OPAL_BLOCK);
        dropSelf(ModBlocks.FREEZING_ICE);
        dropSelf(ModBlocks.SILK_BLOCK);
        dropSelf(ModBlocks.SILK_CARPET);
        add(ModBlocks.NETHERRACK_MYCELIUM, createSingleItemTableWithSilkTouch(ModBlocks.NETHERRACK_MYCELIUM, Blocks.NETHERRACK));
        add(ModBlocks.DEAD_ROOTS, createShearsOnlyDrop(ModBlocks.DEAD_ROOTS));
        add(ModBlocks.DEAD_SPROUTS, createShearsOnlyDrop(ModBlocks.DEAD_SPROUTS));
        dropSelf(ModBlocks.BROWN_BOUNCESHROOM);
        dropSelf(ModBlocks.RED_BOUNCESHROOM);
        add(ModBlocks.RED_WALL_MUSHROOM, createSingleItemTable(Blocks.RED_MUSHROOM));
        add(ModBlocks.BROWN_WALL_MUSHROOM, createSingleItemTable(Blocks.BROWN_MUSHROOM));
        add(ModBlocks.BROWN_SHELF_MUSHROOM, createSilkTouchOrShearsDispatchTable(ModBlocks.BROWN_SHELF_MUSHROOM, applyExplosionCondition(Blocks.BROWN_MUSHROOM, LootItem.lootTableItem(Blocks.BROWN_MUSHROOM))));
        add(ModBlocks.RED_SHELF_MUSHROOM, createSilkTouchOrShearsDispatchTable(ModBlocks.RED_SHELF_MUSHROOM, applyExplosionCondition(Blocks.RED_MUSHROOM, LootItem.lootTableItem(Blocks.RED_MUSHROOM))));
        add(ModBlocks.SPORES, noDrop());
        add(ModBlocks.HANGING_SILK, createSilkTouchOrShearsDispatchTable(ModBlocks.HANGING_SILK, applyExplosionCondition(Items.STRING, LootItem.lootTableItem(Items.STRING))));
        add(ModBlocks.GHOSTLY_LEAVES, createLeavesDrops(ModBlocks.GHOSTLY_LEAVES, ModBlocks.SHADEROOT_SAPLING, 0.05F, 0.0625F, 0.083F, 0.1F));
        dropSelf(ModBlocks.BLACKSTONE_ROCK);
        add(ModBlocks.BURNING_NYLIUM, createSingleItemTableWithSilkTouch(ModBlocks.BURNING_NYLIUM, Blocks.NETHERRACK));
        dropSelf(ModBlocks.BURNING_LOG);
        dropSelf(ModBlocks.BURNING_WOOD);
        dropSelf(ModBlocks.STRIPPED_BURNING_LOG);
        dropSelf(ModBlocks.STRIPPED_BURNING_WOOD);
        dropSelf(ModBlocks.BURNING_PLANKS);
        dropSelf(ModBlocks.BURNING_STAIRS);
        add(ModBlocks.BURNING_SLAB, createSlabItemTable(ModBlocks.BURNING_SLAB));
        dropSelf(ModBlocks.BURNING_FENCE);
        dropSelf(ModBlocks.BURNING_FENCE_GATE);
        dropSelf(ModBlocks.BURNING_TRAPDOOR);
        add(ModBlocks.BURNING_DOOR, createDoorTable(ModBlocks.BURNING_DOOR));
        dropSelf(ModBlocks.BURNING_PRESSURE_PLATE);
        dropSelf(ModBlocks.BURNING_BUTTON);
        dropSelf(ModBlocks.BURNING_SIGN);
        dropSelf(ModBlocks.BURNING_HANGING_SIGN);
        add(ModBlocks.BURNING_LEAVES, createLeavesDrops(ModBlocks.BURNING_LEAVES, ModBlocks.BURNING_SAPLING, 0.05F, 0.0625F, 0.083F, 0.1F));
        add(ModBlocks.BURNING_ROOTS, createShearsOnlyDrop(ModBlocks.BURNING_ROOTS));
        add(ModBlocks.BURNING_SPROUTS, createShearsOnlyDrop(ModBlocks.BURNING_SPROUTS));
        add(ModBlocks.BURNING_VINES, createShearsOnlyDrop(ModBlocks.BURNING_VINES));
        dropSelf(ModBlocks.BURNING_SAPLING);
        add(ModBlocks.POTTED_BURNING_SAPLING, createPotFlowerItemTable(ModBlocks.BURNING_SAPLING));
        dropSelf(ModBlocks.PYROLILY);
        add(ModBlocks.POTTED_PYROLILY, createPotFlowerItemTable(ModBlocks.PYROLILY));
        dropSelf(ModBlocks.HEADSTONE);
        dropSelf(ModBlocks.SHADEROOT_LOG);
        dropSelf(ModBlocks.SHADEROOT_WOOD);
        dropSelf(ModBlocks.STRIPPED_SHADEROOT_LOG);
        dropSelf(ModBlocks.STRIPPED_SHADEROOT_WOOD);
        dropSelf(ModBlocks.SHADEROOT_PLANKS);
        dropSelf(ModBlocks.SHADEROOT_STAIRS);
        add(ModBlocks.SHADEROOT_SLAB, createSlabItemTable(ModBlocks.SHADEROOT_SLAB));
        dropSelf(ModBlocks.SHADEROOT_FENCE);
        dropSelf(ModBlocks.SHADEROOT_FENCE_GATE);
        dropSelf(ModBlocks.SHADEROOT_TRAPDOOR);
        add(ModBlocks.SHADEROOT_DOOR, createDoorTable(ModBlocks.SHADEROOT_DOOR));
        dropSelf(ModBlocks.SHADEROOT_PRESSURE_PLATE);
        dropSelf(ModBlocks.SHADEROOT_BUTTON);
        dropSelf(ModBlocks.SHADEROOT_SIGN);
        dropSelf(ModBlocks.SHADEROOT_HANGING_SIGN);
        add(ModBlocks.GHOSTLY_VINES, createShearsOnlyDrop(ModBlocks.GHOSTLY_VINES));
        add(ModBlocks.FLOWERING_GHOSTLY_LEAVES, createLeavesDrops(ModBlocks.FLOWERING_GHOSTLY_LEAVES, ModBlocks.SHADEROOT_SAPLING, 0.05F, 0.0625F, 0.083F, 0.1F));
        dropSelf(ModBlocks.SHADEROOT_SAPLING);
        add(ModBlocks.POTTED_SHADEROOT_SAPLING, createPotFlowerItemTable(ModBlocks.SHADEROOT_SAPLING));
        add(ModBlocks.WISPBLOOM, createPetalsDrops(ModBlocks.WISPBLOOM));
        dropSelf(ModBlocks.DRAINED_SOUL_SOIL);
        dropSelf(ModBlocks.CHAIN_FENCE);
        add(ModBlocks.SLEEPING_AMETHYST_GEODIN, noDrop());
        add(ModBlocks.SLEEPING_OPAL_GEODIN, noDrop());
        dropWhenSilkTouch(ModBlocks.SMALL_QUARTZ_BUD);
        dropWhenSilkTouch(ModBlocks.MEDIUM_QUARTZ_BUD);
        dropWhenSilkTouch(ModBlocks.LARGE_QUARTZ_BUD);
        add(ModBlocks.BUDDING_QUARTZ, noDrop());
        dropSelf(ModBlocks.ROUGH_QUARTZ_BLOCK);
        add(ModBlocks.SLEEPING_QUARTZ_GEODIN, noDrop());
    }
}
