package com.lankaster.pyrellium.particles;

import com.lankaster.pyrellium.Pyrellium;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModParticleTypes {
    public static final SimpleParticleType AMETHYST_SHARD = register("amethyst_shard", false);
    public static final SimpleParticleType OPAL_SHARD = register("opal_shard", false);

    public static SimpleParticleType register(String name, boolean alwaysShow) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, name), FabricParticleTypes.simple(alwaysShow));
    }

    public static void registerParticle() {

    }
}
