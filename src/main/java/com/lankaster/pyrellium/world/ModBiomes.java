package com.lankaster.pyrellium.world;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.client.sound.MusicType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;

public class ModBiomes {

    public static final RegistryKey<Biome> BLACKSTONE_SPRINGS = registerBiome("blackstone_springs");
    public static final RegistryKey<Biome> BURNING_GROVE = registerBiome("burning_grove");
    public static final RegistryKey<Biome> CRYSTAL_FOREST = registerBiome("crystal_forest");
    public static final RegistryKey<Biome> FROSTBURN_VALLEY = registerBiome("frostburn_valley");
    public static final RegistryKey<Biome> GHOSTLY_WOODS = registerBiome("ghostly_woods");
    public static final RegistryKey<Biome> INFESTED_VALLEY = registerBiome("infested_valley");
    public static final RegistryKey<Biome> MONOLITH_PLAINS = registerBiome("monolith_plains");
    public static final RegistryKey<Biome> MUSHROOM_WASTES = registerBiome("mushroom_wastes");
    public static final RegistryKey<Biome> QUARTZ_CAVERNS = registerBiome("quartz_caverns");

    private static RegistryKey<Biome> registerBiome(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, Identifier.of(Pyrellium.MOD_ID, name));
    }

    public static void bootstrap(Registerable<Biome> context) {
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

    public static Biome defaultBiome(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        return new Biome.Builder()
                .precipitation(false)
                .downfall(0.0f)
                .temperature(2.0f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects(new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .skyColor(7254527)
                        .fogColor(3344392)
                        .moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2))
                        .music(MusicType.createIngameMusic(SoundEvents.MUSIC_NETHER_NETHER_WASTES)).build()
                ).build();
    }
}