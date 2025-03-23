package com.lankaster.pyrellium.entity;

import com.lankaster.pyrellium.Pyrellium;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<CrystalArrowEntity> CRYSTAL_ARROW = Registry.register(Registries.ENTITY_TYPE, new Identifier(Pyrellium.MOD_ID, "crystal_arrow"),
            FabricEntityTypeBuilder.<CrystalArrowEntity>create(SpawnGroup.MISC, CrystalArrowEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());
}
