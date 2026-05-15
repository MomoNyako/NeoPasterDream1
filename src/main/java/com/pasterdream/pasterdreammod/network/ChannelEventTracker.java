package com.pasterdream.pasterdreammod.network;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * 网络通信通道跟踪器
 * 负责注册所有自定义网络包（CustomPacketPayload）及提供发送工具方法
 * <p>
 * 基于 NeoForge 1.21.1 的 RegisterPayloadHandlersEvent 机制
 * 不再使用废弃的 @EventBusSubscriber，改为手动注册
 */
public class ChannelEventTracker {

    /**
     * 注册所有网络包处理器
     * 在 MeltDreamEnergyCapability.init() 中通过 modEventBus 注册
     *
     * @param event 网络包注册事件
     */
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PasterDreamMod.MOD_ID);

        // 注册染梦能量数据同步包（服务端 → 客户端）
        registrar.playToClient(
                MeltDreamEnergyDataPayload.TYPE,
                MeltDreamEnergyDataPayload.STREAM_CODEC,
                MeltDreamEnergyDataPayload::handle
        );

        // 注册 San 理智值数据同步包（服务端 → 客户端）
        registrar.playToClient(
                SanDataPayload.TYPE,
                SanDataPayload.STREAM_CODEC,
                SanDataPayload::handle
        );
    }

    // ==================== 发送工具方法 ====================

    /**
     * 发送消息到服务端
     *
     * @param message 消息对象
     * @param <MSG>   消息类型
     */
    public static <MSG extends CustomPacketPayload> void sendToServer(MSG message) {
        PacketDistributor.sendToServer(message);
    }

    /**
     * 发送消息到指定玩家
     *
     * @param message 消息对象
     * @param player  目标玩家
     * @param <MSG>   消息类型
     */
    public static <MSG extends CustomPacketPayload> void sendToPlayer(MSG message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }

    /**
     * 发送消息到所有在线玩家
     *
     * @param message 消息对象
     * @param <MSG>   消息类型
     */
    public static <MSG extends CustomPacketPayload> void sendToAllPlayers(MSG message) {
        PacketDistributor.sendToAllPlayers(message);
    }

    /**
     * 发送消息到追踪指定实体的所有玩家
     *
     * @param message      消息对象
     * @param entity       被追踪的实体
     * @param sendToSource 是否同时发送给实体自身（如果实体是玩家）
     * @param <MSG>        消息类型
     */
    public static <MSG extends CustomPacketPayload> void sendToPlayersTrackingEntity(MSG message, Entity entity, boolean sendToSource) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, message);
        if (sendToSource && entity instanceof ServerPlayer serverPlayer) {
            sendToPlayer(message, serverPlayer);
        }
    }

    /**
     * 发送消息到追踪指定实体的所有玩家（不发送给源实体）
     *
     * @param message 消息对象
     * @param entity  被追踪的实体
     * @param <MSG>   消息类型
     */
    public static <MSG extends CustomPacketPayload> void sendToPlayersTrackingEntity(MSG message, Entity entity) {
        sendToPlayersTrackingEntity(message, entity, false);
    }
}