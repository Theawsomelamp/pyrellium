package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class BurningSaplingGenerator extends SaplingGenerator {
    public BurningSaplingGenerator() {
    }

    @Override
    protected @Nullable RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "burning_tree"));
    }
}
