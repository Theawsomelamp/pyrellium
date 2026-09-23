package com.lankaster.pyrellium.particles;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Pyrellium.MOD_ID);

    public static final Supplier<SimpleParticleType> AMETHYST_SHARD = register("amethyst_shard", false);
    public static final Supplier<SimpleParticleType> OPAL_SHARD = register("opal_shard", false);

    public static Supplier<SimpleParticleType> register(String name, boolean alwaysShow) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
    }

    public static void registerParticle(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
