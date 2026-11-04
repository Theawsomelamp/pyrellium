package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.block.ModBlocks;
import com.lankaster.pyrellium.world.ModBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementDataGen extends FabricAdvancementProvider {
    public AdvancementDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    protected static Advancement.Builder requireCustomBiomes(RegistryWrapper.WrapperLookup wrapperLookup, Advancement.Builder builder) {
        for(RegistryKey<Biome> registryKey : ModBiomes.getAll()) {
            RegistryEntryLookup<Biome> registryEntryLookup = wrapperLookup.getOrThrow(RegistryKeys.BIOME);

            builder.criterion(registryKey.getValue().toString(), TickCriterion.Conditions.createLocation(LocationPredicate.Builder.createBiome(registryEntryLookup.getOrThrow(registryKey))));
        }

        return builder;
    }

    protected static final String KEY = "advancement.pyrellium.";
    private static final AdvancementEntry dummyExploreNetherAdvancement = Advancement.Builder.create().build(Identifier.of("minecraft", "nether/explore_nether"));

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry VISIT_PYRELLIUM_BIOMES = requireCustomBiomes(wrapperLookup, Advancement.Builder.create()
                .display(ModBlocks.BURNING_NYLIUM,
                        Text.translatable(KEY + "visit_pyrellium_biomes"),
                        Text.translatable(KEY + "visit_pyrellium_biomes.desc"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true, true, false)
                .parent(dummyExploreNetherAdvancement)
                .rewards(AdvancementRewards.NONE)).build(consumer, Identifier.of(Pyrellium.MOD_ID, "visit_pyrellium_biomes").toString());
    }
}