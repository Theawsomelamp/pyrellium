package com.lankaster.pyrellium.particles;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ModParticles {

    public static void registerParticle() {
        ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();

        particleRegistry.register(ModParticleTypes.AMETHYST_SHARD, ArrowShatterParticle.AmethystFactory::new);
        particleRegistry.register(ModParticleTypes.OPAL_SHARD, ArrowShatterParticle.OpalFactory::new);
    }

}
