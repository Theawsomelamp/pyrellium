package com.lankaster.pyrellium.client.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class ArrowShatterParticle extends TextureSheetParticle {
    protected ArrowShatterParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);
        float j = this.random.nextFloat() * 0.1F + 0.2F;
        this.rCol = j;
        this.gCol = j;
        this.bCol = j;
        this.setSize(0.02F, 0.02F);
        this.quadSize = 0.25F;
        this.xd *= (double)1.0F;
        this.yd *= (double)0.5F;
        this.zd *= (double)1.0F;
        this.lifetime = (int)((double)5.0F / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().move(dx, dy, dz));
        this.setLocationFromBoundingbox();
    }

    public void tick() {
        super.tick();
    }

    public static class AmethystFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public AmethystFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType SimpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            ArrowShatterParticle arrowShatterParticle = new ArrowShatterParticle(clientWorld, d, e, f, g, h, i);
            arrowShatterParticle.pickSprite(this.spriteProvider);
            arrowShatterParticle.setColor(1.0F, 1.0F, 1.0F);
            return arrowShatterParticle;
        }
    }

    public static class OpalFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public OpalFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType SimpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            ArrowShatterParticle arrowShatterParticle = new ArrowShatterParticle(clientWorld, d, e, f, g, h, i);
            arrowShatterParticle.pickSprite(this.spriteProvider);
            arrowShatterParticle.setColor(1.0F, 1.0F, 1.0F);
            return arrowShatterParticle;
        }
    }
}
