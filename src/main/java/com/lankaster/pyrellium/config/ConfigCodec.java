package com.lankaster.pyrellium.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ConfigCodec(BlocksConfig blocksConfig, BiomeConfig biomeConfig) {
    public static final Codec<ConfigCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlocksConfig.CODEC.fieldOf("blocks").orElse(BlocksConfig.DEFAULT).forGetter(ConfigCodec::blocksConfig),
            BiomeConfig.CODEC.fieldOf("biomes").orElse(BiomeConfig.DEFAULT).forGetter(ConfigCodec::biomeConfig)
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
}