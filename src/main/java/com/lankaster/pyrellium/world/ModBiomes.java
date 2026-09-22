package com.lankaster.pyrellium.world;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class ModBiomes {

    public static final ResourceKey<Biome> BLACKSTONE_SPRINGS = registerBiome("blackstone_springs");
    public static final ResourceKey<Biome> BURNING_GROVE = registerBiome("burning_grove");
    public static final ResourceKey<Biome> CRYSTAL_FOREST = registerBiome("crystal_forest");
    public static final ResourceKey<Biome> FROSTBURN_VALLEY = registerBiome("frostburn_valley");
    public static final ResourceKey<Biome> GHOSTLY_WOODS = registerBiome("ghostly_woods");
    public static final ResourceKey<Biome> INFESTED_VALLEY = registerBiome("infested_valley");
    public static final ResourceKey<Biome> MONOLITH_PLAINS = registerBiome("monolith_plains");
    public static final ResourceKey<Biome> MUSHROOM_WASTES = registerBiome("mushroom_wastes");
    public static final ResourceKey<Biome> QUARTZ_CAVERNS = registerBiome("quartz_caverns");

    private static ResourceKey<Biome> registerBiome(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(BLACKSTONE_SPRINGS, defaultBiome(context));
        context.register(BURNING_GROVE, defaultBiome(context));
        context.register(CRYSTAL_FOREST, defaultBiome(context));
        context.register(FROSTBURN_VALLEY, defaultBiome(context));
        context.register(GHOSTLY_WOODS, defaultBiome(context));
        context.register(INFESTED_VALLEY, defaultBiome(context));
        context.register(MONOLITH_PLAINS, defaultBiome(context));
        context.register(MUSHROOM_WASTES, defaultBiome(context));
        context.register(QUARTZ_CAVERNS, defaultBiome(context));
    }

    public static Biome defaultBiome(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        net.minecraft.world.level.biome.BiomeGenerationSettings.Builder biomeBuilder =
                new net.minecraft.world.level.biome.BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE),
                        context.lookup(Registries.CONFIGURED_CARVER));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(2.0f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new net.minecraft.world.level.biome.BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .skyColor(7254527)
                        .fogColor(3344392)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2))
                        .backgroundMusic(new Music(SoundEvents.MUSIC_BIOME_NETHER_WASTES, 12000, 24000, false)).build()
                ).build();
    }
}