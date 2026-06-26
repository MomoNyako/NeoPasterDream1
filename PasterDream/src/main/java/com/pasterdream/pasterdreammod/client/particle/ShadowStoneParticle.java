package com.pasterdream.pasterdreammod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

/**
 * 暗影石粒子 (Shadow Stone Particle)
 * <p>
 * 用于暗影魔像技能爆炸时飞溅的碎石效果。
 * 粒子受重力影响下落，具有实体碰撞，呈现不透明的暗色碎屑。
 */
public class ShadowStoneParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    /**
     * 构造暗影石粒子
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
    protected ShadowStoneParticle(ClientLevel level, double x, double y, double z,
                                   double vx, double vy, double vz, SpriteSet spriteSet) {
        super(level, x, y, z, vx, vy, vz);
        this.sprites = spriteSet;
        this.setSprite(spriteSet.get(this.random));
        this.setSize(0.2f, 0.2f);
        this.quadSize = 0.5f + this.random.nextFloat() * 0.5f;
        this.lifetime = 20 + this.random.nextInt(10);
        this.gravity = 0.2f;
        this.hasPhysics = true;

        this.xd = vx + (this.random.nextDouble() - 0.5) * 0.1;
        this.yd = vy + this.random.nextDouble() * 0.1;
        this.zd = vz + (this.random.nextDouble() - 0.5) * 0.1;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    /**
     * 暗影石粒子的工厂类
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
            return new ShadowStoneParticle(level, x, y, z, vx, vy, vz, this.sprites);
        }
    }
}
