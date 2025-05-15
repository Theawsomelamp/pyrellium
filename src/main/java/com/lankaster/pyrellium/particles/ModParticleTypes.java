package com.lankaster.pyrellium.particles;

import com.lankaster.pyrellium.Pyrellium;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticleTypes {
    public static final SimpleParticleType AMETHYST_SHARD = register("amethyst_shard", false);
    public static final SimpleParticleType OPAL_SHARD = register("opal_shard", false);

    public static SimpleParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Pyrellium.MOD_ID, name), FabricParticleTypes.simple(alwaysShow));
    }

    public static void registerParticle() {

    }
}
