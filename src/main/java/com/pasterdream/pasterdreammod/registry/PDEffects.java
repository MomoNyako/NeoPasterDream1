package com.pasterdream.pasterdreammod.registry;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.api.effect.MobEffectAPI;
import com.pasterdream.pasterdreammod.api.effect.MobEffectResult;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 状态效果（BUFF/DEBUFF）注册类
 * <p>
 * 支持两种注册方式：
 * <ul>
 *   <li><b>旧方式</b>：直接使用 {@link #MOB_EFFECTS} 手动注册</li>
 *   <li><b>新方式</b>：使用 {@link MobEffectAPI} 的 Facade+Builder 模式（推荐）</li>
 * </ul>
 * <p>
 * 参考 STORYLINE.md 和原模组效果设计：
 * - DREAMWISH_BUFF：梦境祝福效果，用于进入染梦维度
 * - 暗影侵蚀效果：灯影世界中的负面状态（待实现）
 *
 * @see MobEffectAPI
 * @see com.pasterdream.pasterdreammod.api.effect.builder.MobEffectBuilder
 * @see com.pasterdream.pasterdreammod.api.effect.base.PasterDreamEffect
 */
public class PDEffects {

    /**
     * 状态效果注册器（旧方式，向后兼容）
     */
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
            Registries.MOB_EFFECT, PasterDreamMod.MOD_ID);

    // ==================== 旧方式注册示例（注释态） ====================

    /*
     * 梦境祝福效果 (dreamwish_buff) —— 旧方式
     */
    // public static final DeferredHolder<MobEffect, MobEffect> DREAMWISH_BUFF =
    //         MOB_EFFECTS.register("dreamwish_buff",
    //                 () -> new DreamwishEffect(MobEffectCategory.BENEFICIAL, 0xFF69B4));

    /*
     * 暗影侵蚀效果 (shadow_erosion) —— 旧方式
     */
    // public static final DeferredHolder<MobEffect, MobEffect> SHADOW_EROSION =
    //         MOB_EFFECTS.register("shadow_erosion",
    //                 () -> new ShadowErosionEffect(MobEffectCategory.HARMFUL, 0x2F2F2F));

    // ==================== 新方式示例（MobEffectAPI，注释态） ====================

    /*
     * 梦境祝福效果 (dreamwish_buff) —— 新方式
     * 粉红色有益效果，带结束粒子着色器
     *
     * 使用示例（取消注释即可启用）：
     *
     * public static final MobEffectResult DREAMWISH_BUFF =
     *     MobEffectAPI.createEffect("dreamwish_buff")
     *         .beneficial()
     *         .color(0xFF69B4)
     *         .shaderTexture(new ResourceLocation(PasterDreamMod.MOD_ID, "textures/misc/dreamwish_blur.png"))
     *         .particleType(ParticleTypes.END_ROD)
     *         .onApply((entity, amp) -> entity.heal(2.0f * (amp + 1)))
     *         .onRemove((entity, amp) -> {
     *             PasterDreamMod.LOGGER.info("梦境祝福效果结束: entity={}", entity);
     *         })
     *         .stackingHandler((existing, newInstance) -> {
     *             // 叠加时延长持续时间（最多 5 分钟）
     *             int totalDuration = existing.getDuration() + newInstance.getDuration();
     *             existing.duration = Math.min(totalDuration, 6000);
     *             return existing;
     *         })
     *         .build();
     */

    /*
     * 暗影侵蚀效果 (shadow_erosion) —— 新方式
     * 深灰色有害效果，带烟雾粒子
     *
     * public static final MobEffectResult SHADOW_EROSION =
     *     MobEffectAPI.createEffect("shadow_erosion")
     *         .harmful()
     *         .color(0x2F2F2F)
     *         .particleType(ParticleTypes.SMOKE)
     *         .onApply((entity, amp) -> entity.setRemainingFireTicks(100))
     *         .onRemove((entity, amp) -> entity.clearFire())
     *         .build();
     */

    /*
     * 狂乱效果 (insane_buff) —— 新方式
     * 紫色中性效果，带扭曲着色器
     *
     * public static final MobEffectResult INSANE_BUFF =
     *     MobEffectAPI.createEffect("insane_buff")
     *         .neutral()
     *         .color(0x4B0082)
     *         .shaderTexture(new ResourceLocation(PasterDreamMod.MOD_ID, "textures/misc/insane_warp.png"))
     *         .particleType(ParticleTypes.SCULK_SOUL)
     *         .onApply((entity, amp) -> entity.hurt(entity.damageSources().magic(), 1.0f))
     *         .build();
     */
}