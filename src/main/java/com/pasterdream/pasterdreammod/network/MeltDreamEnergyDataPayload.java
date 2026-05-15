package com.pasterdream.pasterdreammod.network;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.MeltDreamEnergyCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * 染梦能量数据同步网络包
 * 使用 NeoForge 1.21.1 CustomPacketPayload 机制
 * 服务端 → 客户端：同步玩家染梦能量数据
 */
public record MeltDreamEnergyDataPayload(double energy, boolean noNeedConsume) implements CustomPacketPayload {

    /**
     * 网络包类型标识
     */
    public static final CustomPacketPayload.Type<MeltDreamEnergyDataPayload> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_energy_data"));

    /**
     * 流式编解码器
     */
    public static final StreamCodec<FriendlyByteBuf, MeltDreamEnergyDataPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.DOUBLE, MeltDreamEnergyDataPayload::energy,
                    ByteBufCodecs.BOOL, MeltDreamEnergyDataPayload::noNeedConsume,
                    MeltDreamEnergyDataPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * 客户端数据接收处理器
     * 将接收到的能量数据更新到本地玩家的 Attachment 中
     *
     * @param payload 接收到的网络包
     * @param context 网络上下文
     */
    public static void handle(MeltDreamEnergyDataPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = Minecraft.getInstance().player;
            if (player != null) {
                MeltDreamEnergyCapability.MeltDreamEnergyData data = player.getData(MeltDreamEnergyCapability.MELT_DREAM_ENERGY);
                data.setEnergy(payload.energy());
                data.setNoNeedConsumeCount(payload.noNeedConsume() ? 1 : 0);
            }
        });
    }
}