package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.entity.ModEntities;
import com.lankaster.pyrellium.world.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class PyrelliumSpawnModifiers {
    public static final ResourceKey<BiomeModifier> SPAWN_MODIFIERS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "spawn_modifiers"));

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
        HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);

        if (Config.instance().entities.geodin.spawn_weight > 0) {
            bootstrap.register(SPAWN_MODIFIERS, new BiomeModifiers.AddSpawnsBiomeModifier(
                            HolderSet.direct(biomes.getOrThrow(ModBiomes.CRYSTAL_FOREST)),
                            List.of(
                                    new MobSpawnSettings.SpawnerData(ModEntities.GEODIN.get(), Config.instance().entities.geodin.spawn_weight, 2, 4)
                            )
                    )
            );
            bootstrap.register(SPAWN_MODIFIERS, new BiomeModifiers.AddSpawnsBiomeModifier(
                            HolderSet.direct(biomes.getOrThrow(ModBiomes.QUARTZ_CAVERNS)),
                            List.of(
                                    new MobSpawnSettings.SpawnerData(ModEntities.GEODIN.get(), Config.instance().entities.geodin.spawn_weight, 2, 4)
                            )
                    )
            );
        }
    });
}
