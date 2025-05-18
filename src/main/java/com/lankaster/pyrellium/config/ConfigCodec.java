package com.lankaster.pyrellium.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ConfigCodec(BlocksConfig blocksConfig, BiomeConfig biomeConfig, FeatureConfig featureConfig) {
    public static final Codec<ConfigCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlocksConfig.CODEC.fieldOf("blocks").orElse(BlocksConfig.DEFAULT).forGetter(ConfigCodec::blocksConfig),
            BiomeConfig.CODEC.fieldOf("biomes").orElse(BiomeConfig.DEFAULT).forGetter(ConfigCodec::biomeConfig),
            FeatureConfig.CODEC.fieldOf("features").orElse(FeatureConfig.DEFAULT).forGetter(ConfigCodec::featureConfig)
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
                Codec.BOOL.fieldOf("burning_desert_enabled").orElse(true).forGetter(BiomeConfig::doBurningDesert),
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

    public record FeatureConfig(boolean doOpalGeodes, boolean doCoolLavaLake, boolean doBones, boolean doFallenLogs, boolean doSpores, boolean doWallMushrooms, boolean doBombFlowers, boolean doMonolith, boolean doGildedBlackstone, boolean doBlackstoneRocks, boolean doFloorCrystals, boolean doFloorSilk, boolean doHangingSilk, boolean doSpikes) {
        public static final Codec<FeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("opal_geodes").orElse(true).forGetter(FeatureConfig::doOpalGeodes),
                Codec.BOOL.fieldOf("lava_lake_additions").orElse(true).forGetter(FeatureConfig::doCoolLavaLake),
                Codec.BOOL.fieldOf("soul_sand_valley_bones").orElse(true).forGetter(FeatureConfig::doBones),
                Codec.BOOL.fieldOf("nether_forest_fallen_logs").orElse(true).forGetter(FeatureConfig::doFallenLogs),
                Codec.BOOL.fieldOf("mushroom_wastes_spores").orElse(true).forGetter(FeatureConfig::doSpores),
                Codec.BOOL.fieldOf("mushroom_wastes_wall_mushrooms").orElse(true).forGetter(FeatureConfig::doWallMushrooms),
                Codec.BOOL.fieldOf("monolith_plains_bomb_flowers").orElse(true).forGetter(FeatureConfig::doBombFlowers),
                Codec.BOOL.fieldOf("monolith_plains_monolith").orElse(true).forGetter(FeatureConfig::doMonolith),
                Codec.BOOL.fieldOf("blackstone_springs_gilded_blackstone").orElse(true).forGetter(FeatureConfig::doGildedBlackstone),
                Codec.BOOL.fieldOf("blackstone_springs_blackstone_rocks").orElse(true).forGetter(FeatureConfig::doBlackstoneRocks),
                Codec.BOOL.fieldOf("crystal_forest_floor_crystals").orElse(true).forGetter(FeatureConfig::doFloorCrystals),
                Codec.BOOL.fieldOf("infested_valley_floor_decorations").orElse(true).forGetter(FeatureConfig::doFloorSilk),
                Codec.BOOL.fieldOf("infested_valley_hanging_silk").orElse(true).forGetter(FeatureConfig::doHangingSilk),
                Codec.BOOL.fieldOf("quartz_caverns_spikes").orElse(true).forGetter(FeatureConfig::doSpikes)
        ).apply(instance, FeatureConfig::new));
        public static final FeatureConfig DEFAULT = new FeatureConfig(true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }
}
