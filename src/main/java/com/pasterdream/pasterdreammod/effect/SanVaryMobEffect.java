package com.pasterdream.pasterdreammod.effect;

import com.pasterdream.pasterdreammod.capability.SanCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * San 值变化药水效果
 * 瞬间生效，根据 isIncrease 标志增加或减少目标玩家的 San 值
 * <p>
 * 原模组参考：SanVaryMobEffect
 * 两个实例：
 * - san_increase（绿色）：增加 San 值，有益效果
 * - san_decrease（橙棕色）：减少 San 值，有害效果
 */
public class SanVaryMobEffect extends InstantenousMobEffect {

    private final boolean isIncrease;

    /**
     * @param isIncrease true = 增加 San 值（有益），false = 减少 San 值（有害）
     */
    public SanVaryMobEffect(boolean isIncrease) {
        super(isIncrease ? MobEffectCategory.BENEFICIAL : MobEffectCategory.HARMFUL,
                isIncrease ? 0xADFF2F : 0x9b4400);
        this.isIncrease = isIncrease;
    }

    /**
     * 应用效果时根据增幅值调整 San 值
     * amplifier & 0xff 防止溢出
     *
     * @param entity    目标实体
     * @param amplifier 效果增幅
     */
    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayer player) {
            int amount = (amplifier & 0xFF) + 1;
            if (isIncrease) {
                SanCapability.addSan(player, amount);
            } else {
                SanCapability.addSan(player, -amount);
            }
        }
        return true;
    }
}