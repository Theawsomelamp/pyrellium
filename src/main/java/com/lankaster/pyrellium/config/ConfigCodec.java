package com.lankaster.pyrellium.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ConfigCodec(BlocksConfig blocksConfig, BiomeConfig biomeConfig, GlobalFeatureConfig globalFeatureConfig) {
    public static final Codec<ConfigCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlocksConfig.CODEC.fieldOf("blocks").orElse(BlocksConfig.DEFAULT).forGetter(ConfigCodec::blocksConfig),
            BiomeConfig.CODEC.fieldOf("biomes").orElse(BiomeConfig.DEFAULT).forGetter(ConfigCodec::biomeConfig),
            GlobalFeatureConfig.CODEC.fieldOf("features_global").orElse(GlobalFeatureConfig.DEFAULT).forGetter(ConfigCodec::globalFeatureConfig)
    ).apply(instance, ConfigCodec::new));

    public record BlocksConfig(float redBounce, float brownBounce, float explodeStrength) {
        public static final Codec<BlocksConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.FLOAT.fieldOf("red_bounceshroom_bounce").orElse(1.0F).forGetter(BlocksConfig::redBounce),
                Codec.FLOAT.fieldOf("brown_bounceshroom_bounce").orElse(0.75F).forGetter(BlocksConfig::brownBounce),
                Codec.FLOAT.fieldOf("bomb_flower_explosion_strength").orElse(1.0F).forGetter(BlocksConfig::explodeStrength)
        ).apply(instance, BlocksConfig::new));
        public static final BlocksConfig DEFAULT = new BlocksConfig(1.0F, 0.75F, 1.0F);
    }

    public record BiomeConfig(BlackstoneSpringsConfig blackstoneSpringsConfig, BurningGroveConfig burningGroveConfig, CrystalForestConfig crystalForestConfig, FrostburnValleyConfig frostburnValleyConfig, GhostlyWoodsConfig ghostlyWoodsConfig, InfestedValleyConfig infestedValleyConfig, MonolithPlainsConfig monolithPlainsConfig, MushroomWastesConfig mushroomWastesConfig, QuartzCavernsConfig quartzCavernsConfig) {
        public static final Codec<BiomeConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlackstoneSpringsConfig.CODEC.fieldOf("blackstone_springs").orElse(BlackstoneSpringsConfig.DEFAULT).forGetter(BiomeConfig::blackstoneSpringsConfig),
                BurningGroveConfig.CODEC.fieldOf("burning_grove").orElse(BurningGroveConfig.DEFAULT).forGetter(BiomeConfig::burningGroveConfig),
                CrystalForestConfig.CODEC.fieldOf("crystal_forest").orElse(CrystalForestConfig.DEFAULT).forGetter(BiomeConfig::crystalForestConfig),
                FrostburnValleyConfig.CODEC.fieldOf("frostburn_valley").orElse(FrostburnValleyConfig.DEFAULT).forGetter(BiomeConfig::frostburnValleyConfig),
                GhostlyWoodsConfig.CODEC.fieldOf("ghostly_woods").orElse(GhostlyWoodsConfig.DEFAULT).forGetter(BiomeConfig::ghostlyWoodsConfig),
                InfestedValleyConfig.CODEC.fieldOf("infested_valley").orElse(InfestedValleyConfig.DEFAULT).forGetter(BiomeConfig::infestedValleyConfig),
                MonolithPlainsConfig.CODEC.fieldOf("monolith_plains").orElse(MonolithPlainsConfig.DEFAULT).forGetter(BiomeConfig::monolithPlainsConfig),
                MushroomWastesConfig.CODEC.fieldOf("mushroom_wastes").orElse(MushroomWastesConfig.DEFAULT).forGetter(BiomeConfig::mushroomWastesConfig),
                QuartzCavernsConfig.CODEC.fieldOf("quartz_caverns").orElse(QuartzCavernsConfig.DEFAULT).forGetter(BiomeConfig::quartzCavernsConfig)
        ).apply(instance, BiomeConfig::new));
        public static final BiomeConfig DEFAULT = new BiomeConfig(BlackstoneSpringsConfig.DEFAULT, BurningGroveConfig.DEFAULT, CrystalForestConfig.DEFAULT, FrostburnValleyConfig.DEFAULT, GhostlyWoodsConfig.DEFAULT, InfestedValleyConfig.DEFAULT, MonolithPlainsConfig.DEFAULT, MushroomWastesConfig.DEFAULT, QuartzCavernsConfig.DEFAULT);
    }

    public record BiomeNoiseConfig(float temperature, float humidity, float continentalness, float erosion, float weirdness, float depth, float offset) {
        public static final Codec<BiomeNoiseConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.FLOAT.fieldOf("temperature").orElse(0.0f).forGetter(BiomeNoiseConfig::temperature),
                Codec.FLOAT.fieldOf("humidity").orElse(0.0f).forGetter(BiomeNoiseConfig::humidity),
                Codec.FLOAT.fieldOf("continentalness").orElse(0.0f).forGetter(BiomeNoiseConfig::continentalness),
                Codec.FLOAT.fieldOf("erosion").orElse(0.0f).forGetter(BiomeNoiseConfig::erosion),
                Codec.FLOAT.fieldOf("weirdness").orElse(0.0f).forGetter(BiomeNoiseConfig::weirdness),
                Codec.FLOAT.fieldOf("depth").orElse(0.0f).forGetter(BiomeNoiseConfig::depth),
                Codec.FLOAT.fieldOf("offset").orElse(0.0f).forGetter(BiomeNoiseConfig::offset)
        ).apply(instance, BiomeNoiseConfig::new));
    }

    public record BlackstoneSpringsConfig(boolean enabled, BiomeNoiseConfig biomeNoiseConfig, boolean doBlackstoneRocks) {
        public static final Codec<BlackstoneSpringsConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("enable_biome").orElse(true).forGetter(BlackstoneSpringsConfig::enabled),
                BiomeNoiseConfig.CODEC.fieldOf("biome_noise").orElse(new BiomeNoiseConfig(-0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.15f)).forGetter(BlackstoneSpringsConfig::biomeNoiseConfig),
                Codec.BOOL.fieldOf("generate_blackstone_rocks").orElse(true).forGetter(BlackstoneSpringsConfig::doBlackstoneRocks)
        ).apply(instance, BlackstoneSpringsConfig::new));
        public static final BlackstoneSpringsConfig DEFAULT = new BlackstoneSpringsConfig(true, new BiomeNoiseConfig(-0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.15f), true);
    }

    public record BurningGroveConfig(boolean enabled, BiomeNoiseConfig biomeNoiseConfig, boolean doPyrolily) {
        public static final Codec<BurningGroveConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("enable_biome").orElse(true).forGetter(BurningGroveConfig::enabled),
                BiomeNoiseConfig.CODEC.fieldOf("biome_noise").orElse(new BiomeNoiseConfig(-0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)).forGetter(BurningGroveConfig::biomeNoiseConfig),
                Codec.BOOL.fieldOf("generate_pyrolily").orElse(true).forGetter(BurningGroveConfig::doPyrolily)
        ).apply(instance, BurningGroveConfig::new));
        public static final BurningGroveConfig DEFAULT = new BurningGroveConfig(true, new BiomeNoiseConfig(-0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), true);
    }

    public record CrystalForestConfig(boolean enabled, BiomeNoiseConfig biomeNoiseConfig, boolean doFloorCrystals) {
        public static final Codec<CrystalForestConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("enable_biome").orElse(true).forGetter(CrystalForestConfig::enabled),
                BiomeNoiseConfig.CODEC.fieldOf("biome_noise").orElse(new BiomeNoiseConfig(0.0f, 0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.325f)).forGetter(CrystalForestConfig::biomeNoiseConfig),
                Codec.BOOL.fieldOf("generate_floor_crystals").orElse(true).forGetter(CrystalForestConfig::doFloorCrystals)
        ).apply(instance, CrystalForestConfig::new));
        public static final CrystalForestConfig DEFAULT = new CrystalForestConfig(true, new BiomeNoiseConfig(0.0f, 0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.325f), true);
    }

    public record FrostburnValleyConfig(boolean enabled, BiomeNoiseConfig biomeNoiseConfig) {
        public static final Codec<FrostburnValleyConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("enable_biome").orElse(true).forGetter(FrostburnValleyConfig::enabled),
                BiomeNoiseConfig.CODEC.fieldOf("biome_noise").orElse(new BiomeNoiseConfig(0.0f, -0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)).forGetter(FrostburnValleyConfig::biomeNoiseConfig)
        ).apply(instance, FrostburnValleyConfig::new));
        public static final FrostburnValleyConfig DEFAULT = new FrostburnValleyConfig(true, new BiomeNoiseConfig(0.0f, -0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
    }

    public record GhostlyWoodsConfig(boolean enabled, BiomeNoiseConfig biomeNoiseConfig, boolean doHeadstones) {
        public static final Codec<GhostlyWoodsConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("enable_biome").orElse(true).forGetter(GhostlyWoodsConfig::enabled),
                BiomeNoiseConfig.CODEC.fieldOf("biome_noise").orElse(new BiomeNoiseConfig(0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)).forGetter(GhostlyWoodsConfig::biomeNoiseConfig),
                Codec.BOOL.fieldOf("generate_headstones").orElse(true).forGetter(GhostlyWoodsConfig::doHeadstones)
        ).apply(instance, GhostlyWoodsConfig::new));
        public static final GhostlyWoodsConfig DEFAULT = new GhostlyWoodsConfig(true, new BiomeNoiseConfig(0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), true);
    }

    public record InfestedValleyConfig(boolean enabled, BiomeNoiseConfig biomeNoiseConfig, boolean doFloorSilk, boolean doHangingSilk) {
        public static final Codec<InfestedValleyConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("enable_biome").orElse(true).forGetter(InfestedValleyConfig::enabled),
                BiomeNoiseConfig.CODEC.fieldOf("biome_noise").orElse(new BiomeNoiseConfig(0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.325f)).forGetter(InfestedValleyConfig::biomeNoiseConfig),
                Codec.BOOL.fieldOf("generate_floor_decorations").orElse(true).forGetter(InfestedValleyConfig::doFloorSilk),
                Codec.BOOL.fieldOf("generate_hanging_silk").orElse(true).forGetter(InfestedValleyConfig::doHangingSilk)
        ).apply(instance, InfestedValleyConfig::new));
        public static final InfestedValleyConfig DEFAULT = new InfestedValleyConfig(true, new BiomeNoiseConfig(0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.325f), true, true);
    }

    public record MonolithPlainsConfig(boolean enabled, BiomeNoiseConfig biomeNoiseConfig, boolean doBombFlowers, boolean doMonolith) {
        public static final Codec<MonolithPlainsConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("enable_biome").orElse(true).forGetter(MonolithPlainsConfig::enabled),
                BiomeNoiseConfig.CODEC.fieldOf("biome_noise").orElse(new BiomeNoiseConfig(0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)).forGetter(MonolithPlainsConfig::biomeNoiseConfig),
                Codec.BOOL.fieldOf("generate_bomb_flowers").orElse(true).forGetter(MonolithPlainsConfig::doBombFlowers),
                Codec.BOOL.fieldOf("generate_monolith").orElse(true).forGetter(MonolithPlainsConfig::doMonolith)
        ).apply(instance, MonolithPlainsConfig::new));
        public static final MonolithPlainsConfig DEFAULT = new MonolithPlainsConfig(true, new BiomeNoiseConfig(0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), true, true);
    }

    public record MushroomWastesConfig(boolean enabled, BiomeNoiseConfig biomeNoiseConfig, boolean doSpores, boolean doWallMushrooms) {
        public static final Codec<MushroomWastesConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("enable_biome").orElse(true).forGetter(MushroomWastesConfig::enabled),
                BiomeNoiseConfig.CODEC.fieldOf("biome_noise").orElse(new BiomeNoiseConfig(-0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f)).forGetter(MushroomWastesConfig::biomeNoiseConfig),
                Codec.BOOL.fieldOf("generate_spores").orElse(true).forGetter(MushroomWastesConfig::doSpores),
                Codec.BOOL.fieldOf("generate_wall_mushrooms").orElse(true).forGetter(MushroomWastesConfig::doWallMushrooms)
        ).apply(instance, MushroomWastesConfig::new));
        public static final MushroomWastesConfig DEFAULT = new MushroomWastesConfig(true, new BiomeNoiseConfig(-0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f), true, true);
    }

    public record QuartzCavernsConfig(boolean enabled, BiomeNoiseConfig biomeNoiseConfig, boolean doSpikes) {
        public static final Codec<QuartzCavernsConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("enable_biome").orElse(true).forGetter(QuartzCavernsConfig::enabled),
                BiomeNoiseConfig.CODEC.fieldOf("biome_noise").orElse(new BiomeNoiseConfig(0.25f, 0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.25f)).forGetter(QuartzCavernsConfig::biomeNoiseConfig),
                Codec.BOOL.fieldOf("generate_quartz_spikes").orElse(true).forGetter(QuartzCavernsConfig::doSpikes)
        ).apply(instance, QuartzCavernsConfig::new));
        public static final QuartzCavernsConfig DEFAULT = new QuartzCavernsConfig(true, new BiomeNoiseConfig(0.25f, 0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.25f), true);
    }

    public record GlobalFeatureConfig(boolean doOpalGeodes, boolean doCoolLavaLake, boolean doFallenLogs, boolean doGildedBlackstone, boolean doThickCeiling, boolean doIncreasedHeight, boolean doBones) {
        public static final Codec<GlobalFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("opal_geodes").orElse(true).forGetter(GlobalFeatureConfig::doOpalGeodes),
                Codec.BOOL.fieldOf("lava_lake_additions").orElse(true).forGetter(GlobalFeatureConfig::doCoolLavaLake),
                Codec.BOOL.fieldOf("nether_forest_fallen_logs").orElse(true).forGetter(GlobalFeatureConfig::doFallenLogs),
                Codec.BOOL.fieldOf("gilded_blackstone_patches").orElse(true).forGetter(GlobalFeatureConfig::doGildedBlackstone),
                Codec.BOOL.fieldOf("thicker_bedrock_ceiling").orElse(true).forGetter(GlobalFeatureConfig::doThickCeiling),
                Codec.BOOL.fieldOf("192_block_nether_height").orElse(true).forGetter(GlobalFeatureConfig::doIncreasedHeight),
                Codec.BOOL.fieldOf("soul_sand_valley_bones").orElse(true).forGetter(GlobalFeatureConfig::doBones)
        ).apply(instance, GlobalFeatureConfig::new));
        public static final GlobalFeatureConfig DEFAULT = new GlobalFeatureConfig(true, true, true, true, true, true, true);
    }
}