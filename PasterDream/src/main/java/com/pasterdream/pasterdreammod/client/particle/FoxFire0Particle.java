package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

/**
 * 狐火粒子 0 (Fox Fire 0 Particle)
 * <p>
 * 用于狐火实体散发的橙色半透明火焰粒子。
 * 粒子缓缓上浮、轻微左右摇曳，模拟幽冷狐火的燃烧效果。
 */
public class FoxFire0Particle extends TextureSheetParticle {

    private final SpriteSet sprites;
    private final float wobblePhase;

    /**
     * 构造狐火粒子 0
     *
     * @param level     客户端世界
     * @param x         初始 X 坐标
     * @param y         初始 Y 坐标
     * @param z         初始 Z 坐标
     * @param vx        X 方向速度
     * @param vy        Y 方向速度
     * @param vz        Z 方向速度
     * @param spriteSet 精灵表
     */
    protected FoxFire0Particle(ClientLevel level, double x, double y, double z,
                                double vx, double vy, double vz, SpriteSet spriteSet) {
        super(level, x, y, z, vx, vy, vz);
        this.sprites = spriteSet;
        this.setSprite(spriteSet.get(this.random));
        this.setSize(0.15f, 0.15f);
        this.quadSize = 0.3f + this.random.nextFloat() * 0.3f;
        this.lifetime = 18 + this.random.nextInt(6);
        this.gravity = -0.06f;
        this.hasPhysics = false;
        this.wobblePhase = this.random.nextFloat() * (float) Math.PI * 2;

        this.xd = vx + (this.random.nextDouble() - 0.5) * 0.01;
        this.yd = vy + this.random.nextDouble() * 0.01;
        this.zd = vz + (this.random.nextDouble() - 0.5) * 0.01;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        float ageRatio = (float) this.age / this.lifetime;
        this.alpha = 1.0f - ageRatio;
        this.quadSize *= 0.97f;

        double wobble = this.age * 0.15 + this.wobblePhase;
        this.xd += Math.sin(wobble) * 0.0005;
        this.zd += Math.cos(wobble * 0.7) * 0.0005;

        this.move(this.xd, this.yd, this.zd);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    /**
     * 狐火粒子 0 的工厂类
     */
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double vx, double vy, double vz) {
            return new FoxFire0Particle(level, x, y, z, vx, vy, vz, this.sprites);
        }
    }
}
