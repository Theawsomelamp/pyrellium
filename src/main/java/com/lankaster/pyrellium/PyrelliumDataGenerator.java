package com.lankaster.pyrellium;

import com.lankaster.pyrellium.data.*;
import com.lankaster.pyrellium.enchant.ModEnchants;
import com.lankaster.pyrellium.world.ModBiomes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class PyrelliumDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(BlockTagDataGen::new);
        pack.addProvider(ItemTagDataGen::new);
        pack.addProvider(LootTableDataGen::new);
        pack.addProvider(EnchantmentTagDataGen::new);
        pack.addProvider(RegistryDataGen::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
        builder.addRegistry(RegistryKeys.ENCHANTMENT, ModEnchants::bootstrap);
    }
}
