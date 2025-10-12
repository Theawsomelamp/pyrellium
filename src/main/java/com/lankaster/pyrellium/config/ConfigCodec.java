package com.lankaster.pyrellium.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ConfigCodec(BlocksConfig blocksConfig, BiomeConfig biomeConfig, GlobalFeatureConfig globalFeatureConfig, BiomeFeatureConfig biomeFeatureConfig) {
    public static final Codec<ConfigCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlocksConfig.CODEC.fieldOf("blocks").orElse(BlocksConfig.DEFAULT).forGetter(ConfigCodec::blocksConfig),
            BiomeConfig.CODEC.fieldOf("biomes").orElse(BiomeConfig.DEFAULT).forGetter(ConfigCodec::biomeConfig),
            GlobalFeatureConfig.CODEC.fieldOf("features_global").orElse(GlobalFeatureConfig.DEFAULT).forGetter(ConfigCodec::globalFeatureConfig),
            BiomeFeatureConfig.CODEC.fieldOf("features_biome").orElse(BiomeFeatureConfig.DEFAULT).forGetter(ConfigCodec::biomeFeatureConfig)
    ).apply(instance, ConfigCodec::new));

    public record BlocksConfig(float redBounce, float brownBounce, float explodeStrength) {
        public static final Codec<BlocksConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.FLOAT.fieldOf("red_bounceshroom_bounce").orElse(1.0F).forGetter(BlocksConfig::redBounce),
                Codec.FLOAT.fieldOf("brown_bounceshroom_bounce").orElse(0.75F).forGetter(BlocksConfig::brownBounce),
                Codec.FLOAT.fieldOf("bomb_flower_explosion_strength").orElse(1.0F).forGetter(BlocksConfig::explodeStrength)
        ).apply(instance, BlocksConfig::new));
        public static final BlocksConfig DEFAULT = new BlocksConfig(1.0F, 0.75F, 1.0F);
    }

    public record BiomeConfig(boolean doBlackstoneSprings, boolean doBurningDesert, boolean doCrystalForest, boolean doFrostburnValley, boolean doGhostlyWoods, boolean doInfestedValley, boolean doMonolithPlains, boolean doMushroomWastes, boolean doQuartzCaverns) {
        public static final Codec<BiomeConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("blackstone_springs_enabled").orElse(true).forGetter(BiomeConfig::doBlackstoneSprings),
                Codec.BOOL.fieldOf("burning_grove_enabled").orElse(true).forGetter(BiomeConfig::doBurningDesert),
                Codec.BOOL.fieldOf("crystal_forest_enabled").orElse(true).forGetter(BiomeConfig::doCrystalForest),
                Codec.BOOL.fieldOf("frostburn_valley_enabled").orElse(true).forGetter(BiomeConfig::doFrostburnValley),
                Codec.BOOL.fieldOf("ghostly_woods_enabled").orElse(true).forGetter(BiomeConfig::doGhostlyWoods),
                Codec.BOOL.fieldOf("infested_valley_enabled").orElse(true).forGetter(BiomeConfig::doInfestedValley),
                Codec.BOOL.fieldOf("monolith_plains_enabled").orElse(true).forGetter(BiomeConfig::doMonolithPlains),
                Codec.BOOL.fieldOf("mushroom_wastes_enabled").orElse(true).forGetter(BiomeConfig::doMushroomWastes),
                Codec.BOOL.fieldOf("quartz_caverns_enabled").orElse(true).forGetter(BiomeConfig::doQuartzCaverns)
        ).apply(instance, BiomeConfig::new));
        public static final BiomeConfig DEFAULT = new BiomeConfig(true, true, true, true, true, true, true, true, true);
    }

    public record GlobalFeatureConfig(boolean doOpalGeodes, boolean doCoolLavaLake, boolean doFallenLogs, boolean doGildedBlackstone, boolean doThickCeiling, boolean doIncreasedHeight) {
        public static final Codec<GlobalFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("opal_geodes").orElse(true).forGetter(GlobalFeatureConfig::doOpalGeodes),
                Codec.BOOL.fieldOf("lava_lake_additions").orElse(true).forGetter(GlobalFeatureConfig::doCoolLavaLake),
                Codec.BOOL.fieldOf("nether_forest_fallen_logs").orElse(true).forGetter(GlobalFeatureConfig::doFallenLogs),
                Codec.BOOL.fieldOf("gilded_blackstone_patches").orElse(true).forGetter(GlobalFeatureConfig::doGildedBlackstone),
                Codec.BOOL.fieldOf("thicker_bedrock_ceiling").orElse(true).forGetter(GlobalFeatureConfig::doThickCeiling),
                Codec.BOOL.fieldOf("192_block_nether_height").orElse(true).forGetter(GlobalFeatureConfig::doIncreasedHeight)
        ).apply(instance, GlobalFeatureConfig::new));
        public static final GlobalFeatureConfig DEFAULT = new GlobalFeatureConfig(true, true, true, true, true, true);
    }

    public record BiomeFeatureConfig(boolean doBones, boolean doSpores, boolean doWallMushrooms, boolean doBombFlowers, boolean doMonolith, boolean doBlackstoneRocks, boolean doFloorCrystals, boolean doFloorSilk, boolean doHangingSilk, boolean doSpikes, boolean doPyrolily, boolean doHeadstones) {
        public static final Codec<BiomeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("soul_sand_valley_bones").orElse(true).forGetter(BiomeFeatureConfig::doBones),
                Codec.BOOL.fieldOf("mushroom_wastes_spores").orElse(true).forGetter(BiomeFeatureConfig::doSpores),
                Codec.BOOL.fieldOf("mushroom_wastes_wall_mushrooms").orElse(true).forGetter(BiomeFeatureConfig::doWallMushrooms),
                Codec.BOOL.fieldOf("monolith_plains_bomb_flowers").orElse(true).forGetter(BiomeFeatureConfig::doBombFlowers),
                Codec.BOOL.fieldOf("monolith_plains_monolith").orElse(true).forGetter(BiomeFeatureConfig::doMonolith),
                Codec.BOOL.fieldOf("blackstone_springs_blackstone_rocks").orElse(true).forGetter(BiomeFeatureConfig::doBlackstoneRocks),
                Codec.BOOL.fieldOf("crystal_forest_floor_crystals").orElse(true).forGetter(BiomeFeatureConfig::doFloorCrystals),
                Codec.BOOL.fieldOf("infested_valley_floor_decorations").orElse(true).forGetter(BiomeFeatureConfig::doFloorSilk),
                Codec.BOOL.fieldOf("infested_valley_hanging_silk").orElse(true).forGetter(BiomeFeatureConfig::doHangingSilk),
                Codec.BOOL.fieldOf("quartz_caverns_spikes").orElse(true).forGetter(BiomeFeatureConfig::doSpikes),
                Codec.BOOL.fieldOf("burning_grove_pyrolily").orElse(true).forGetter(BiomeFeatureConfig::doPyrolily),
                Codec.BOOL.fieldOf("ghostly_woods_headstones").orElse(true).forGetter(BiomeFeatureConfig::doHeadstones)
        ).apply(instance, BiomeFeatureConfig::new));
        public static final BiomeFeatureConfig DEFAULT = new BiomeFeatureConfig(true, true, true, true, true, true, true, true, true, true, true, true);
    }
}
