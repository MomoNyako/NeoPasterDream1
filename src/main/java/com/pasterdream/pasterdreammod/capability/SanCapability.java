package com.pasterdream.pasterdreammod.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.network.ChannelEventTracker;
import com.pasterdream.pasterdreammod.network.SanDataPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * San 理智值系统（SanCapability）
 * 基于 NeoForge 1.21.1 Data Attachments 实现
 * <p>
 * 为玩家附加 San 值数据（0~100），提供增减/同步/检查功能
 * 参考原模组 SanCapability.java 的设计，使用 AttachmentType 替代 Forge Capability
 * <p>
 * 后续拓展计划：
 * - 游戏规则：SANCHECKSYSTEM、SANVARIABILITYPERTICK、STARTSANONREVIVE
 * - 自定义属性：SAN_VARIABILITY
 * - 环境 San 值影响（维度、时间等）
 * - HUD 显示（SanTank）
 * - 低 San 值减益效果
 */
public class SanCapability {

    /**
     * AttachmentType 注册器
     */
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, PasterDreamMod.MOD_ID);

    /**
     * San 值 AttachmentType
     * 自动附加到所有玩家实体
     * copyOnDeath() 确保玩家重生后数据保留
     */
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SanData>> SAN_ATTACHMENT =
            ATTACHMENT_TYPES.register("player_san", () -> AttachmentType.builder(
                            () -> new SanData()
                    ).serialize(SanData.CODEC)
                    .copyOnDeath()
                    .build());

    /**
     * 初始化方法
     * 在主模组构造函数中调用
     *
     * @param modEventBus 模组事件总线，用于注册 AttachmentType
     */
    public static void init(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(SanCapability::playerClone);
        NeoForge.EVENT_BUS.addListener(SanCapability::playerRespawn);
        NeoForge.EVENT_BUS.addListener(SanCapability::playerChangeDimension);
        NeoForge.EVENT_BUS.addListener(SanCapability::playerLoggedIn);
    }

    // ==================== 静态辅助方法 ====================

    /**
     * 获取玩家当前 San 值
     *
     * @param player 目标玩家
     * @return 当前 San 值 (0 ~ 100)
     */
    public static double getSan(Player player) {
        return player.getData(SAN_ATTACHMENT).getSanValue();
    }

    /**
     * 设置玩家 San 值
     *
     * @param player 目标玩家
     * @param value  要设置的 San 值 (0 ~ 100)
     */
    public static void setSan(Player player, double value) {
        if (player instanceof ServerPlayer sp) {
            SanData data = sp.getData(SAN_ATTACHMENT);
            data.setSanValue(value);
            sp.setData(SAN_ATTACHMENT, data);
            sync(sp);
        }
    }

    /**
     * 增加玩家 San 值
     *
     * @param player 目标玩家
     * @param value  增加的 San 值（可为负数，自动限制 0~100）
     */
    public static void addSan(Player player, double value) {
        if (player instanceof ServerPlayer sp) {
            SanData data = sp.getData(SAN_ATTACHMENT);
            data.addSanValue(value);
            sp.setData(SAN_ATTACHMENT, data);
            sync(sp);
        }
    }

    /**
     * 获取 San 检查系统是否启用
     *
     * @param player 目标玩家
     * @return 当前 SanCheck 状态
     */
    public static boolean getSanCheck(Player player) {
        return player.getData(SAN_ATTACHMENT).isSanCheck();
    }

    /**
     * 设置 San 检查系统状态
     *
     * @param player 目标玩家
     * @param flag   SanCheck 标志
     */
    public static void setSanCheck(Player player, boolean flag) {
        if (player instanceof ServerPlayer sp) {
            SanData data = sp.getData(SAN_ATTACHMENT);
            data.setSanCheck(flag);
            sp.setData(SAN_ATTACHMENT, data);
            sync(sp);
        }
    }

    /**
     * 同步玩家 San 数据到客户端
     *
     * @param player 目标玩家
     */
    public static void sync(Player player) {
        if (player instanceof ServerPlayer sp) {
            SanData data = sp.getData(SAN_ATTACHMENT);
            ChannelEventTracker.sendToPlayer(new SanDataPayload(data.getSanValue(), data.isSanCheck()), sp);
        }
    }

    // ==================== 事件处理器 ====================

    /**
     * 玩家重生/传送后数据克隆时同步
     */
    private static void playerClone(PlayerEvent.Clone event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            sync(sp);
        }
    }

    /**
     * 玩家重生时同步
     */
    private static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        sync(event.getEntity());
    }

    /**
     * 玩家切换维度时同步
     */
    private static void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        sync(event.getEntity());
    }

    /**
     * 玩家登录时同步
     */
    private static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        sync(event.getEntity());
    }

    // ==================== 数据类 ====================

    /**
     * San 值数据容器
     * 使用 RecordCodecBuilder 实现序列化
     */
    public static class SanData {
        /**
         * San 值范围 0 ~ 100，默认 100
         */
        private double sanValue;

        /**
         * San 检查系统是否启用
         */
        private boolean sanCheck;

        /**
         * Codec 序列化器
         */
        public static final Codec<SanData> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.DOUBLE.fieldOf("san_value").forGetter(SanData::getSanValue),
                        Codec.BOOL.fieldOf("san_check").forGetter(SanData::isSanCheck)
                ).apply(instance, SanData::new)
        );

        public SanData() {
            this.sanValue = 100;
            this.sanCheck = true;
        }

        /**
         * @param sanValue 初始 San 值
         * @param sanCheck SanCheck 初始状态
         */
        public SanData(double sanValue, boolean sanCheck) {
            this.sanValue = Math.max(0, Math.min(sanValue, 100));
            this.sanCheck = sanCheck;
        }

        public double getSanValue() {
            return sanValue;
        }

        /**
         * @param value 要设置的 San 值，自动限制在 0~100
         */
        public void setSanValue(double value) {
            this.sanValue = Math.max(0, Math.min(value, 100));
        }

        /**
         * @param value 增加的 San 值，结果自动限制在 0~100
         */
        public void addSanValue(double value) {
            this.sanValue = Math.max(0, Math.min(this.sanValue + value, 100));
        }

        public boolean isSanCheck() {
            return sanCheck;
        }

        /**
         * @param sanCheck SanCheck 标志
         */
        public void setSanCheck(boolean sanCheck) {
            this.sanCheck = sanCheck;
        }

        /**
         * 从另一个数据对象复制所有值
         *
         * @param other 源数据对象
         */
        public void copyFrom(SanData other) {
            this.sanValue = other.sanValue;
            this.sanCheck = other.sanCheck;
        }
    }
}