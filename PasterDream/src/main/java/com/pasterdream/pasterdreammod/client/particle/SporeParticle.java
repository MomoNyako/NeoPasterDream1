package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

/**
 * 孢子粒子 (Spore Particle)
 * <p>
 * 用于孢子实体飞行或自爆时散发的绿色发光孢子粉尘。
 * 4 帧循环动画，自发光渲染，缓慢飘散后消失。
 */
public class SporeParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    /**
     * 构造孢子粒子
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
    protected SporeParticle(ClientLevel level, double x, double y, double z,
                             double vx, double vy, double vz, SpriteSet spriteSet) {
        super(level, x, y, z, vx, vy, vz);
        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);
        this.setSize(0.05f, 0.05f);
        this.quadSize = 0.15f + this.random.nextFloat() * 0.15f;
        this.lifetime = 10 + this.random.nextInt(6);
        this.gravity = 0.0f;
        this.hasPhysics = false;

        this.xd = vx * 0.1 + (this.random.nextDouble() - 0.5) * 0.01;
        this.yd = vy * 0.1 + (this.random.nextDouble() - 0.5) * 0.01;
        this.zd = vz * 0.1 + (this.random.nextDouble() - 0.5) * 0.01;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.sprites.get((this.age / 3) % 4, 4));
        }
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    /**
     * 孢子粒子的工厂类
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
            return new SporeParticle(level, x, y, z, vx, vy, vz, this.sprites);
        }
    }
}
