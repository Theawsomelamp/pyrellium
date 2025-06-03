package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.ConfigHandler;
import com.lankaster.pyrellium.entity.ModBoatEntity;
import com.lankaster.pyrellium.entity.ModChestBoatEntity;
import com.lankaster.pyrellium.item.ModBoatItem;
import com.lankaster.pyrellium.item.ModItemGroups;
import com.lankaster.pyrellium.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
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

    public static final Block NETHERRACK_MYCELIUM = registerBlock("netherrack_mycelium", new NyliumBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_NYLIUM)));
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

    public static final WoodType BURNING = WoodTypeBuilder.copyOf(WoodType.OAK).register(Identifier.of(Pyrellium.MOD_ID, "burning"), new BlockSetType("burning"));
    public static final Block BURNING_NYLIUM = registerBlock("burning_nylium", new ModNyliumBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_NYLIUM)));
    public static final Block BURNING_LEAVES = registerBlock("burning_leaves", new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    public static final Block BURNING_SAPLING = registerBlock("burning_sapling", new SaplingBlock(new ModSaplingGenerator().BURNING, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)));
    public static final Block POTTED_BURNING_SAPLING = registerBlockWithoutBlockItem("potted_burning_sapling", new FlowerPotBlock(ModBlocks.BURNING_SAPLING, AbstractBlock.Settings.copy(Blocks.POTTED_OAK_SAPLING)));
    public static final Block BURNING_LOG = registerBlock("burning_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_STEM).sounds(BlockSoundGroup.WOOD)));
    public static final Block BURNING_WOOD = registerBlock("burning_wood", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_HYPHAE).sounds(BlockSoundGroup.WOOD)));
    public static final Block STRIPPED_BURNING_LOG = registerBlock("stripped_burning_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_CRIMSON_STEM).sounds(BlockSoundGroup.WOOD)));
    public static final Block STRIPPED_BURNING_WOOD = registerBlock("stripped_burning_wood", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_CRIMSON_HYPHAE).sounds(BlockSoundGroup.WOOD)));
    public static final Block BURNING_PLANKS = registerBlock("burning_planks", new Block(AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS).sounds(BlockSoundGroup.WOOD)));
    public static final Block BURNING_STAIRS = registerBlock("burning_stairs", new StairsBlock(BURNING_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(BURNING_PLANKS)));
    public static final Block BURNING_SLAB = registerBlock("burning_slab", new SlabBlock(AbstractBlock.Settings.copy(BURNING_PLANKS)));
    public static final Block BURNING_FENCE = registerBlock("burning_fence", new FenceBlock(AbstractBlock.Settings.copy(BURNING_PLANKS)));
    public static final Block BURNING_FENCE_GATE = registerBlock("burning_fence_gate", new FenceGateBlock(BURNING, AbstractBlock.Settings.copy(BURNING_PLANKS)));
    public static final Block BURNING_DOOR = registerBlock("burning_door", new DoorBlock(BURNING.setType(), AbstractBlock.Settings.copy(BURNING_PLANKS)));
    public static final Block BURNING_TRAPDOOR = registerBlock("burning_trapdoor", new TrapdoorBlock(BURNING.setType(), AbstractBlock.Settings.copy(BURNING_PLANKS).nonOpaque()));
    public static final Block BURNING_PRESSURE_PLATE = registerBlock("burning_pressure_plate", new PressurePlateBlock(BURNING.setType(), AbstractBlock.Settings.copy(BURNING_PLANKS).noCollision().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block BURNING_BUTTON = registerBlock("burning_button", new ButtonBlock(BURNING.setType(), 30, AbstractBlock.Settings.copy(BURNING_PLANKS).noCollision().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block BURNING_SIGN = registerBlockWithoutBlockItem("burning_sign", new SignBlock(BURNING, AbstractBlock.Settings.create().mapColor(Blocks.CRIMSON_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F)));
    public static final Block BURNING_WALL_SIGN = registerBlockWithoutBlockItem("burning_wall_sign", new WallSignBlock(BURNING, AbstractBlock.Settings.create().mapColor(Blocks.CRIMSON_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F).dropsLike(BURNING_SIGN)));
    public static final Block BURNING_HANGING_SIGN = registerBlockWithoutBlockItem("burning_hanging_sign", new HangingSignBlock(BURNING, AbstractBlock.Settings.create().mapColor(Blocks.CRIMSON_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F)));
    public static final Block BURNING_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("burning_wall_hanging_sign", new WallHangingSignBlock(BURNING, AbstractBlock.Settings.create().mapColor(Blocks.CRIMSON_PLANKS.getDefaultMapColor()).instrument(NoteBlockInstrument.BASS).solid().noCollision().strength(1.0F).dropsLike(BURNING_HANGING_SIGN)));
    public static final Block BURNING_ROOTS = registerBlock("burning_roots", new RootsBlock(AbstractBlock.Settings.copy(Blocks.WARPED_ROOTS)));
    public static final Block BURNING_SPROUTS = registerBlock("burning_sprouts", new SproutsBlock(AbstractBlock.Settings.copy(Blocks.NETHER_SPROUTS)));
    public static final Block BURNING_VINES = registerBlock("burning_vines", new HangingVinesBlock(AbstractBlock.Settings.copy(Blocks.TWISTING_VINES)));
    public static final Block PYROLILY = registerBlock("pyrolily", new BurningFlowerBlock(StatusEffects.FIRE_RESISTANCE, 5, AbstractBlock.Settings.copy(Blocks.POPPY).luminance((state) -> 9)));
    public static final Block POTTED_PYROLILY = registerBlockWithoutBlockItem("potted_pyrolily", new FlowerPotBlock(ModBlocks.PYROLILY, AbstractBlock.Settings.copy(Blocks.POTTED_POPPY).luminance((state) -> 7)));

    /// Registry order being an ass
    public static final Item BURNING_SIGN_ITEM = ModItems.registerItem("burning_sign", new SignItem((new Item.Settings().maxCount(16)), ModBlocks.BURNING_SIGN, ModBlocks.BURNING_WALL_SIGN));
    public static final Item BURNING_HANGING_SIGN_ITEM = ModItems.registerItem("burning_hanging_sign", new HangingSignItem(ModBlocks.BURNING_HANGING_SIGN, ModBlocks.BURNING_WALL_HANGING_SIGN, (new Item.Settings().maxCount(16))));
    public static final Item BURNING_BOAT = ModItems.registerItem("burning_boat", new ModBoatItem(false, ModBoatEntity.Type.BURNING, ModChestBoatEntity.Type.BURNING, (new Item.Settings()).maxCount(1)));
    public static final Item BURNING_CHEST_BOAT = ModItems.registerItem("burning_chest_boat", new ModBoatItem(true, ModBoatEntity.Type.BURNING, ModChestBoatEntity.Type.BURNING, (new Item.Settings()).maxCount(1)));

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

    public static void registerStrippables() {
        StrippableBlockRegistry.register(BURNING_LOG, STRIPPED_BURNING_LOG);
        StrippableBlockRegistry.register(BURNING_WOOD, STRIPPED_BURNING_WOOD);
    }

    public static void registerModBlocks() {
        registerStrippables();
        BlockEntityType.SIGN.addSupportedBlock(ModBlocks.BURNING_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(ModBlocks.BURNING_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(ModBlocks.BURNING_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(ModBlocks.BURNING_WALL_HANGING_SIGN);
    }
}
