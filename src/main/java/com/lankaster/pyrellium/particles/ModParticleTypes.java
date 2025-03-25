package com.lankaster.pyrellium.particles;

import com.lankaster.pyrellium.Pyrellium;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticleTypes {
    public static final DefaultParticleType AMETHYST_SHARD = register("amethyst_shard", false);
    public static final DefaultParticleType OPAL_SHARD = register("opal_shard", false);

    public static DefaultParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registries.PARTICLE_TYPE, new Identifier(Pyrellium.MOD_ID, name), FabricParticleTypes.simple(alwaysShow));
    }

    public static void registerParticle() {

    }
}
