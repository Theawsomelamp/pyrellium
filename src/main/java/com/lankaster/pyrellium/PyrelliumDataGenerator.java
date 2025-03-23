package com.lankaster.pyrellium;

import com.lankaster.pyrellium.data.BlockTagDataGen;
import com.lankaster.pyrellium.data.ItemTagDataGen;
import com.lankaster.pyrellium.data.LootTableDataGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PyrelliumDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(BlockTagDataGen::new);
        pack.addProvider(ItemTagDataGen::new);
        pack.addProvider(LootTableDataGen::new);
    }
}
