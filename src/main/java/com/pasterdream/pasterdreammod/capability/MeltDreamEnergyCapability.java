package com.pasterdream.pasterdreammod.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.network.ChannelEventTracker;
import com.pasterdream.pasterdreammod.network.MeltDreamEnergyDataPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * 染梦能量系统（MeltDreamEnergy Capability）
 * 基于 NeoForge 1.21.1 Data Attachments 实现
 * <p>
 * 为玩家附加染梦能量数据，提供能量获取/消耗/同步功能
 * 能量范围：0 ~ 100
 * <p>
 * 参考原模组 MeltDreamEnergyCapability.java 的设计，使用 AttachmentType 替代 Forge Capability
 */
public class MeltDreamEnergyCapability {

    /**
     * AttachmentType 注册器
     */
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, PasterDreamMod.MOD_ID);

    /**
     * 染梦能量 AttachmentType
     * 自动附加到所有玩家实体
     * copyOnDeath() 确保玩家重生后数据保留
     */
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MeltDreamEnergyData>> MELT_DREAM_ENERGY =
            ATTACHMENT_TYPES.register("melt_dream_energy", () -> AttachmentType.builder(
                            () -> new MeltDreamEnergyData()
                    ).serialize(MeltDreamEnergyData.CODEC)
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

        // 注册网络数据包（手动注册，避免使用废弃的 @EventBusSubscriber）
        modEventBus.addListener(ChannelEventTracker::register);

        NeoForge.EVENT_BUS.addListener(MeltDreamEnergyCapability::playerClone);
        NeoForge.EVENT_BUS.addListener(MeltDreamEnergyCapability::playerRespawn);
        NeoForge.EVENT_BUS.addListener(MeltDreamEnergyCapability::playerChangeDimension);
        NeoForge.EVENT_BUS.addListener(MeltDreamEnergyCapability::playerLoggedIn);
    }

    // ==================== 静态辅助方法 ====================

    /**
     * 获取玩家当前染梦能量值
     *
     * @param player 目标玩家
     * @return 当前能量值 (0 ~ 100)
     */
    public static double getEnergy(Player player) {
        return player.getData(MELT_DREAM_ENERGY).getEnergy();
    }

    /**
     * 设置玩家染梦能量值
     *
     * @param player 目标玩家
     * @param value  要设置的能量值 (0 ~ 100)
     */
    public static void setEnergy(Player player, double value) {
        if (player instanceof ServerPlayer sp) {
            MeltDreamEnergyData data = sp.getData(MELT_DREAM_ENERGY);
            data.setEnergy(value);
            sp.setData(MELT_DREAM_ENERGY, data);
            sync(sp);
        }
    }

    /**
     * 增加玩家染梦能量值
     *
     * @param player 目标玩家
     * @param value  增加的能量值（可为负数）
     */
    public static void addEnergy(Player player, double value) {
        if (player instanceof ServerPlayer sp) {
            MeltDreamEnergyData data = sp.getData(MELT_DREAM_ENERGY);
            data.addEnergy(value);
            sp.setData(MELT_DREAM_ENERGY, data);
            sync(sp);
        }
    }

    /**
     * 消耗玩家染梦能量
     *
     * @param player 目标玩家
     * @param value  要消耗的能量值
     * @return true 如果消耗成功，false 能量不足
     */
    public static boolean consumeEnergy(Player player, double value) {
        if (player instanceof ServerPlayer sp) {
            MeltDreamEnergyData data = sp.getData(MELT_DREAM_ENERGY);
            if (data.isNoNeedConsumeActive() || sp.isCreative()) {
                return true;
            }
            if (data.getEnergy() > value) {
                data.addEnergy(-value);
                sp.setData(MELT_DREAM_ENERGY, data);
                sync(sp);
                return true;
            }
            return false;
        }
        MeltDreamEnergyData data = player.getData(MELT_DREAM_ENERGY);
        return data.isNoNeedConsumeActive() || player.isCreative() || data.getEnergy() > value;
    }

    /**
     * 获取玩家免消耗状态
     *
     * @param player 目标玩家
     * @return 是否免消耗
     */
    public static boolean getNoNeedConsume(Player player) {
        return player.getData(MELT_DREAM_ENERGY).isNoNeedConsumeActive();
    }

    /**
     * 设置玩家免消耗状态
     *
     * @param player 目标玩家
     * @param flag   免消耗标志
     */
    public static void setNoNeedConsume(Player player, boolean flag) {
        if (player instanceof ServerPlayer sp) {
            MeltDreamEnergyData data = sp.getData(MELT_DREAM_ENERGY);
            data.setNoNeedConsume(flag);
            sp.setData(MELT_DREAM_ENERGY, data);
            sync(sp);
        }
    }

    /**
     * 同步玩家能量数据到客户端
     *
     * @param player 目标玩家
     */
    public static void sync(Player player) {
        if (player instanceof ServerPlayer sp) {
            MeltDreamEnergyData data = sp.getData(MELT_DREAM_ENERGY);
            ChannelEventTracker.sendToPlayer(new MeltDreamEnergyDataPayload(data.getEnergy(), data.isNoNeedConsumeActive()), sp);
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
     * 染梦能量数据容器
     * 使用 RecordCodecBuilder 实现序列化
     */
    public static class MeltDreamEnergyData {
        /**
         * 能量值范围 0 ~ 100
         */
        private double energy;

        /**
         * 免消耗计数（>0 时消耗能量不扣减）
         */
        private int noNeedConsume;

        /**
         * Codec 序列化器
         */
        public static final Codec<MeltDreamEnergyData> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.DOUBLE.fieldOf("energy").forGetter(MeltDreamEnergyData::getEnergy),
                        Codec.INT.fieldOf("no_need_consume").forGetter(MeltDreamEnergyData::getNoNeedConsume)
                ).apply(instance, MeltDreamEnergyData::new)
        );

        public MeltDreamEnergyData() {
            this.energy = 0;
            this.noNeedConsume = 0;
        }

        /**
         * @param energy        初始能量值
         * @param noNeedConsume 免消耗计数
         */
        public MeltDreamEnergyData(double energy, int noNeedConsume) {
            this.energy = Math.max(0, Math.min(energy, 100));
            this.noNeedConsume = Math.max(0, noNeedConsume);
        }

        public double getEnergy() {
            return energy;
        }

        /**
         * @param value 要设置的能量值，自动限制在 0~100
         */
        public void setEnergy(double value) {
            this.energy = Math.max(0, Math.min(value, 100));
        }

        /**
         * @param value 增加的能量值，结果自动限制在 0~100
         */
        public void addEnergy(double value) {
            this.energy = Math.max(0, Math.min(this.energy + value, 100));
        }

        public int getNoNeedConsume() {
            return noNeedConsume;
        }

        /**
         * 便捷检查：是否处于免消耗状态
         *
         * @return true 如果 noNeedConsume > 0
         */
        public boolean isNoNeedConsumeActive() {
            return noNeedConsume > 0;
        }

        /**
         * @param flag true 增加计数，false 减少计数
         */
        public void setNoNeedConsume(boolean flag) {
            this.noNeedConsume = Math.max(0, this.noNeedConsume + (flag ? 1 : -1));
        }

        /**
         * @param noNeedConsume 直接设置免消耗计数
         */
        public void setNoNeedConsumeCount(int noNeedConsume) {
            this.noNeedConsume = Math.max(0, noNeedConsume);
        }

        /**
         * 从另一个数据对象复制所有值
         *
         * @param other 源数据对象
         */
        public void copyFrom(MeltDreamEnergyData other) {
            this.energy = other.energy;
            this.noNeedConsume = other.noNeedConsume;
        }
    }
}