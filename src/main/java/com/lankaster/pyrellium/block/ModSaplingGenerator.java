package com.lankaster.pyrellium.block;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class ModSaplingGenerator {
    TreeGrower BURNING = new TreeGrower(
            "burning",
            Optional.empty(),
            Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "burning_tree"))),
            Optional.empty()
    );

    TreeGrower SHADEROOT = new TreeGrower(
            "shaderoot",
            Optional.empty(),
            Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "ghost_tree"))),
            Optional.empty()
    );
}
