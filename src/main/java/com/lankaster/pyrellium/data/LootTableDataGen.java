package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LootTableDataGen extends FabricBlockLootTableProvider {
    public LootTableDataGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.OPAL_BLOCK);
        addDropWithSilkTouch(ModBlocks.SMALL_OPAL_BUD);
        addDropWithSilkTouch(ModBlocks.MEDIUM_OPAL_BUD);
        addDropWithSilkTouch(ModBlocks.LARGE_OPAL_BUD);
        addDrop(ModBlocks.BUDDING_OPAL, dropsNothing());
        addDrop(ModBlocks.CLEAR_AMETHYST_BLOCK);
        addDrop(ModBlocks.CLEAR_OPAL_BLOCK);
        addDrop(ModBlocks.FREEZING_ICE);
        addDrop(ModBlocks.SILK_BLOCK);
        addDrop(ModBlocks.SILK_CARPET);
        addDrop(ModBlocks.NETHERRACK_MYCELIUM, drops(ModBlocks.NETHERRACK_MYCELIUM, Blocks.NETHERRACK));
        addDrop(ModBlocks.DEAD_ROOTS, dropsWithShears(ModBlocks.DEAD_ROOTS));
        addDrop(ModBlocks.DEAD_SPROUTS, dropsWithShears(ModBlocks.DEAD_SPROUTS));
        addDrop(ModBlocks.BROWN_BOUNCESHROOM);
        addDrop(ModBlocks.RED_BOUNCESHROOM);
        addDrop(ModBlocks.RED_WALL_MUSHROOM, drops(Blocks.RED_MUSHROOM));
        addDrop(ModBlocks.BROWN_WALL_MUSHROOM, drops(Blocks.BROWN_MUSHROOM));
        addDrop(ModBlocks.BROWN_SHELF_MUSHROOM, dropsWithSilkTouchOrShears(ModBlocks.BROWN_SHELF_MUSHROOM, addSurvivesExplosionCondition(Blocks.BROWN_MUSHROOM, ItemEntry.builder(Blocks.BROWN_MUSHROOM))));
        addDrop(ModBlocks.RED_SHELF_MUSHROOM, dropsWithSilkTouchOrShears(ModBlocks.RED_SHELF_MUSHROOM, addSurvivesExplosionCondition(Blocks.RED_MUSHROOM, ItemEntry.builder(Blocks.RED_MUSHROOM))));
        addDrop(ModBlocks.SPORES, dropsNothing());
        addDrop(ModBlocks.HANGING_SILK, dropsWithSilkTouchOrShears(ModBlocks.HANGING_SILK, addSurvivesExplosionCondition(Items.STRING, ItemEntry.builder(Items.STRING))));
        addDrop(ModBlocks.GHOSTLY_LEAVES, dropsWithShears(ModBlocks.GHOSTLY_LEAVES));
        addDrop(ModBlocks.QUARTZ_CRYSTAL);
        addDrop(ModBlocks.BLACKSTONE_ROCK);
        addDrop(ModBlocks.BURNING_NYLIUM, drops(ModBlocks.BURNING_NYLIUM, Blocks.NETHERRACK));
        addDrop(ModBlocks.BURNING_LOG);
        addDrop(ModBlocks.BURNING_WOOD);
        addDrop(ModBlocks.STRIPPED_BURNING_LOG);
        addDrop(ModBlocks.STRIPPED_BURNING_WOOD);
        addDrop(ModBlocks.BURNING_PLANKS);
        addDrop(ModBlocks.BURNING_STAIRS);
        addDrop(ModBlocks.BURNING_SLAB, slabDrops(ModBlocks.BURNING_SLAB));
        addDrop(ModBlocks.BURNING_FENCE);
        addDrop(ModBlocks.BURNING_FENCE_GATE);
        addDrop(ModBlocks.BURNING_TRAPDOOR);
        addDrop(ModBlocks.BURNING_DOOR, doorDrops(ModBlocks.BURNING_DOOR));
        addDrop(ModBlocks.BURNING_PRESSURE_PLATE);
        addDrop(ModBlocks.BURNING_BUTTON);
        addDrop(ModBlocks.BURNING_SIGN);
        addDrop(ModBlocks.BURNING_HANGING_SIGN);
    }
}
