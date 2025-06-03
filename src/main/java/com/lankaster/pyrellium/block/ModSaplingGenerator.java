package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModSaplingGenerator {
    SaplingGenerator BURNING = new SaplingGenerator(
            "burning",
            Optional.empty(),
            Optional.of(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Pyrellium.MOD_ID, "burning_tree"))),
            Optional.empty()
    );
}
