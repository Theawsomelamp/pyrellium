package com.lankaster.pyrellium.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ArrowShatterParticle extends SpriteBillboardParticle {
    protected ArrowShatterParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);
        float j = this.random.nextFloat() * 0.1F + 0.2F;
        this.red = j;
        this.green = j;
        this.blue = j;
        this.setBoundingBoxSpacing(0.02F, 0.02F);
        this.scale = 0.25F;
        this.velocityX *= (double)1.0F;
        this.velocityY *= (double)0.5F;
        this.velocityZ *= (double)1.0F;
        this.maxAge = (int)((double)5.0F / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    public void tick() {
        super.tick();
    }

    public static class AmethystFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public AmethystFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            ArrowShatterParticle arrowShatterParticle = new ArrowShatterParticle(clientWorld, d, e, f, g, h, i);
            arrowShatterParticle.setSprite(this.spriteProvider);
            arrowShatterParticle.setColor(1.0F, 1.0F, 1.0F);
            return arrowShatterParticle;
        }
    }

    public static class OpalFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public OpalFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            ArrowShatterParticle arrowShatterParticle = new ArrowShatterParticle(clientWorld, d, e, f, g, h, i);
            arrowShatterParticle.setSprite(this.spriteProvider);
            arrowShatterParticle.setColor(1.0F, 1.0F, 1.0F);
            return arrowShatterParticle;
        }
    }
}
