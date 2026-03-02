package com.lankaster.pyrellium.config;

public class Config {
    protected static Config INSTANCE = new Config();

    public Blocks blocks = new Blocks();
    public Items items = new Items();
    public GlobalFeatures globalFeatures = new GlobalFeatures();
    public Biomes biomes = new Biomes();

    public static Config instance() {
        return INSTANCE;
    }

    public static class Blocks {
        public float red_bounceshroom_bounce = 1.0F;
        public float brown_bounceshroom_bounce = 0.75F;
        public float bomb_plant_explosion_strength = 1.0F;
        public String spores_effect = "minecraft:poison";
        public int spores_effect_time = 100;
    }

    public static class Items {
        public float bomb_flower_explosion_strength = 1.0F;
        public boolean opal_spyglass_block_sharing = true;
        public float mushroom_cap_effect_multiplier = 0.5F;
        public String[] mushroom_cap_effects = {"minecraft:poison"};
        public int crystal_arrow_shatter_radius = 2;
        public int crystal_arrow_shatter_damage = 2;
    }

    public static class GlobalFeatures {
        public boolean raised_nether_height = true;
        public boolean thicker_bedrock_ceiling = true;
        public boolean opal_geodes = true;
        public boolean lava_lake_additions = true;
        public boolean nether_forest_fallen_logs = true;
        public boolean gilded_blackstone_patches = true;
        public boolean soul_sand_valley_bones = true;
    }

    public static class Biomes {
        public BlackstoneSprings blackstone_springs = new BlackstoneSprings();
        public BurningGrove burning_grove = new BurningGrove();
        public CrystalForest crystal_forest = new CrystalForest();
        public FrostburnValley frostburn_valley = new FrostburnValley();
        public GhostlyWoods ghostly_woods = new GhostlyWoods();
        public InfestedValley infested_valley = new InfestedValley();
        public MonolithPlains monolith_plains = new MonolithPlains();
        public MushroomWastes mushroom_wastes = new MushroomWastes();
        public QuartzCaverns quartz_caverns = new QuartzCaverns();
    }

    public record BiomeNoise(float temperature, float humidity, float continentalness, float erosion, float depth, float weirdness, float offset) {
    }

    public static class BlackstoneSprings {
        public boolean enable_biome = true;
        public BiomeNoise biome_noise = new BiomeNoise(-0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.15f);
        public boolean generate_blackstone_rocks = true;
    }

    public static class BurningGrove {
        public boolean enable_biome = true;
        public BiomeNoise biome_noise = new BiomeNoise(-0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        public boolean generate_pyrolily = true;
    }

    public static class CrystalForest {
        public boolean enable_biome = true;
        public BiomeNoise biome_noise = new BiomeNoise(0.0f, 0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.325f);
        public boolean generate_floor_crystals = true;
    }

    public static class FrostburnValley {
        public boolean enable_biome = true;
        public BiomeNoise biome_noise = new BiomeNoise(0.0f, -0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public static class GhostlyWoods {
        public boolean enable_biome = true;
        public BiomeNoise biome_noise = new BiomeNoise(0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        public boolean generate_headstones = true;
    }

    public static class InfestedValley {
        public boolean enable_biome = true;
        public BiomeNoise biome_noise = new BiomeNoise(0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.325f);
        public boolean generate_floor_decorations = true;
        public boolean generate_hanging_silk = true;
    }

    public static class MonolithPlains{
        public boolean enable_biome = true;
        public BiomeNoise biome_noise = new BiomeNoise(0.75f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        public boolean generate_bomb_flowers = true;
        public boolean generate_monolith = true;
    }

    public static class MushroomWastes {
        public boolean enable_biome = true;
        public BiomeNoise biome_noise = new BiomeNoise(-0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f);
        public boolean generate_spores = true;
        public boolean generate_wall_mushrooms = true;
    }

    public static class QuartzCaverns {
        public boolean enable_biome = true;
        public BiomeNoise biome_noise = new BiomeNoise(0.25f, 0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.25f);
        public boolean generate_quartz_spikes = true;
    }
}