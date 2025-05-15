package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.ConfigHandler;
import com.lankaster.pyrellium.item.ModItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.PlaceableOnWaterItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


public class ModBlocks {
    public static final Block OPAL_BLOCK = registerBlock("opal_block", new Block(AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK)));
    public static final Block SMALL_OPAL_BUD = registerBlock("small_opal_bud", new AmethystClusterBlock(5, 3, AbstractBlock.Settings.copy(Blocks.SMALL_AMETHYST_BUD)));
    public static final Block MEDIUM_OPAL_BUD = registerBlock("medium_opal_bud", new AmethystClusterBlock(4, 3, AbstractBlock.Settings.copy(Blocks.MEDIUM_AMETHYST_BUD)));
    public static final Block LARGE_OPAL_BUD = registerBlock("large_opal_bud", new AmethystClusterBlock(3, 4, AbstractBlock.Settings.copy(Blocks.LARGE_AMETHYST_BUD)));
    public static final Block OPAL_CLUSTER = registerBlock("opal_cluster", new AmethystClusterBlock(7, 3, AbstractBlock.Settings.copy(Blocks.AMETHYST_CLUSTER)));
    public static final Block BUDDING_OPAL = registerBlock("budding_opal", new BuddingBlock(AbstractBlock.Settings.copy(Blocks.BUDDING_AMETHYST), SMALL_OPAL_BUD, MEDIUM_OPAL_BUD, LARGE_OPAL_BUD, OPAL_CLUSTER));

    public static final Block CLEAR_AMETHYST_BLOCK = registerBlock("clear_amethyst_block", new TransparentBlock(AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).nonOpaque()));
    public static final Block CLEAR_OPAL_BLOCK = registerBlock("clear_opal_block", new TransparentBlock(AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).nonOpaque()));

    public static final Block FREEZING_ICE = registerBlock("freezing_ice", new FreezingIceBlock(AbstractBlock.Settings.copy(Blocks.PACKED_ICE)));
    public static final Block SILK_BLOCK = registerBlock("silk_block", new Block(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)));
    public static final Block SILK_CARPET = registerBlock("silk_carpet", new CarpetBlock(AbstractBlock.Settings.copy(Blocks.MOSS_CARPET).sounds(BlockSoundGroup.WOOL).velocityMultiplier(0.4F).jumpVelocityMultiplier(0.5F)));
    public static final Block HANGING_SILK = registerBlock("hanging_silk", new HangingSilkBlock(AbstractBlock.Settings.create().ticksRandomly().noCollision().burnable().strength(0.75F).pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block NETHERRACK_MYCELIUM = registerBlock("netherrack_mycelium", new Block(AbstractBlock.Settings.copy(Blocks.CRIMSON_NYLIUM)));
    public static final Block BROWN_BOUNCESHROOM = registerBlock("brown_bounceshroom", new BounceMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM_BLOCK), ConfigHandler.getConfig().blocksConfig().brownBounce()));
    public static final Block RED_BOUNCESHROOM = registerBlock("red_bounceshroom", new BounceMushroomBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK), ConfigHandler.getConfig().blocksConfig().redBounce()));
    public static final Block BROWN_WALL_MUSHROOM = registerBlockWithoutBlockItem("brown_wall_mushroom", new WallMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)));
    public static final Block RED_WALL_MUSHROOM = registerBlockWithoutBlockItem("red_wall_mushroom", new WallMushroomBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM)));
    public static final Block BROWN_SHELF_MUSHROOM = registerBlock("brown_shelf_mushroom", new BrownShelfMushroomBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)));
    public static final Block RED_SHELF_MUSHROOM = registerBlock("red_shelf_mushroom", new RedShelfMushroomBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM)));
    public static final Block SPORES = registerBlock("spores", new SporesBlock(AbstractBlock.Settings.copy(Blocks.COBWEB).strength(-1.0F).sounds(BlockSoundGroup.FROGSPAWN)));

    public static final Block DEAD_ROOTS = registerBlock("dead_roots", new RootsBlock(AbstractBlock.Settings.copy(Blocks.WARPED_ROOTS)));
    public static final Block DEAD_SPROUTS = registerBlock("dead_sprouts", new SproutsBlock(AbstractBlock.Settings.copy(Blocks.NETHER_SPROUTS)));
    public static final Block GHOSTLY_LEAVES = registerBlock("ghostly_leaves", new Block(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    public static final Block BONE = registerBlockWithoutBlockItem("bone", new BoneItemBlock(AbstractBlock.Settings.copy(Blocks.BONE_BLOCK)));
    public static final Block BOMB_PLANT = registerBlock("bomb_plant", new BombPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block QUARTZ_CRYSTAL = registerBlock("quartz_crystal", new AmethystClusterBlock(7, 3, AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK)));
    public static final Block BLACKSTONE_ROCK = registerPlaceableOnWaterBlock("blackstone_rock", new CarpetBlock(AbstractBlock.Settings.copy(Blocks.BLACKSTONE).nonOpaque()));

    private static Block registerBlockWithoutBlockItem(String name, Block block){
        return Registry.register(Registries.BLOCK, Identifier.of(Pyrellium.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Pyrellium.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(Pyrellium.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.PYRELLIUM).register(entries -> entries.add(item));
        return item;
    }

    private static Block registerPlaceableOnWaterBlock(String name, Block block) {
        registerPlaceableOnWaterBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Pyrellium.MOD_ID, name), block);
    }

    private static Item registerPlaceableOnWaterBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(Pyrellium.MOD_ID, name),
                new PlaceableOnWaterItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.PYRELLIUM).register(entries -> entries.add(item));
        return item;
    }

    public static void registerModBlocks() {
    }
}
