package com.pasterdream.pasterdreammod.network;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.SanCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * San 理智值数据同步网络包
 * 使用 NeoForge 1.21.1 CustomPacketPayload 机制
 * 服务端 → 客户端：同步玩家 San 值数据
 */
public record SanDataPayload(double sanValue, boolean sanCheck) implements CustomPacketPayload {

    /**
     * 网络包类型标识
     */
    public static final CustomPacketPayload.Type<SanDataPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "san_data"));

    /**
     * 流式编解码器
     */
    public static final StreamCodec<FriendlyByteBuf, SanDataPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.DOUBLE, SanDataPayload::sanValue,
                    ByteBufCodecs.BOOL, SanDataPayload::sanCheck,
                    SanDataPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * 客户端数据接收处理器
     * 将接收到的 San 值数据更新到本地玩家的 Attachment 中
     *
     * @param payload 接收到的网络包
     * @param context 网络上下文
     */
    public static void handle(SanDataPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = Minecraft.getInstance().player;
            if (player != null) {
                SanCapability.SanData data = player.getData(SanCapability.SAN_ATTACHMENT);
                data.setSanValue(payload.sanValue());
                data.setSanCheck(payload.sanCheck());
            }
        });
    }
}