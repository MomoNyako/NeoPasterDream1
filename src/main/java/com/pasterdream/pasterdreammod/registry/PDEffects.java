package com.pasterdream.pasterdreammod.registry;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.effect.SanVaryMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 状态效果（BUFF/DEBUFF）注册类
 * 使用 DeferredRegister 模式注册所有自定义 MobEffect
 * <p>
 * 参考 STORYLINE.md 和原模组效果设计：
 * - DREAMWISH_BUFF：梦境祝福效果，用于进入染梦维度
 * - san_increase / san_decrease：San 理智值增减效果（已实现）
 * - 暗影侵蚀效果：灯影世界中的负面状态（待实现）
 */
public class PDEffects {

    /**
     * 状态效果注册器
     */
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
            Registries.MOB_EFFECT, PasterDreamMod.MOD_ID);

    // ==================== San 值效果 ====================

    /**
     * San 值增加效果 (san_increase)
     * 瞬间生效，增加玩家 San 值
     */
    public static final DeferredHolder<MobEffect, MobEffect> SAN_INCREASE = MOB_EFFECTS.register(
            "san_increase",
            () -> new SanVaryMobEffect(true));

    /**
     * San 值减少效果 (san_decrease)
     * 瞬间生效，减少玩家 San 值
     */
    public static final DeferredHolder<MobEffect, MobEffect> SAN_DECREASE = MOB_EFFECTS.register(
            "san_decrease",
            () -> new SanVaryMobEffect(false));

    // ==================== 后续待注册效果 ====================

    /*
     * 梦境祝福效果 (dreamwish_buff)
     * 用于进入染梦维度的前置条件
     * 效果：在夜晚睡觉时触发传送
     */
    // public static final DeferredHolder<MobEffect, MobEffect> DREAMWISH_BUFF =
    //         MOB_EFFECTS.register("dreamwish_buff",
    //                 () -> new DreamwishEffect(MobEffectCategory.BENEFICIAL, 0xFF69B4));

    /*
     * 暗影侵蚀效果 (shadow_erosion)
     * 灯影世界中的持续负面效果
     */
    // public static final DeferredHolder<MobEffect, MobEffect> SHADOW_EROSION =
    //         MOB_EFFECTS.register("shadow_erosion",
    //                 () -> new ShadowErosionEffect(MobEffectCategory.HARMFUL, 0x2F2F2F));

    /*
     * Insane 狂乱效果 (insane_buff)
     * 低 San 值触发的负面效果，模糊视野、随机移动
     */
    // public static final DeferredHolder<MobEffect, MobEffect> INSANE_BUFF =
    //         MOB_EFFECTS.register("insane_buff",
    //                 () -> new InsaneEffect(MobEffectCategory.HARMFUL, 0x4B0082));
}