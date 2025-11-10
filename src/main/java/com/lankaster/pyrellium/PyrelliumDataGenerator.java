package com.lankaster.pyrellium;

import com.lankaster.pyrellium.data.BlockTagDataGen;
import com.lankaster.pyrellium.data.ItemTagDataGen;
import com.lankaster.pyrellium.data.LootTableDataGen;
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
    }

    @Override
    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
    }
}
