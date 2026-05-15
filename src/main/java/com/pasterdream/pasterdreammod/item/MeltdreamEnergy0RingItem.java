package com.pasterdream.pasterdreammod.item;

import com.pasterdream.pasterdreammod.capability.MeltDreamEnergyCapability;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 染梦能量戒指（0级）
 * 佩戴后在染梦维度和灯影世界中缓慢恢复染梦能量
 * <p>
 * 参考原模组 MeltdreamEnergy0RingItem 的设计
 * 使用 MeltDreamEnergyCapability 进行能量操作
 */
public class MeltdreamEnergy0RingItem extends Item {

    /**
     * 染梦维度 ID
     */
    private static final ResourceKey<Level> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_world"));

    /**
     * 灯影世界维度 ID
     */
    private static final ResourceKey<Level> LAMP_SHADOW_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath("pasterdream", "lamp_shadow_world"));

    public MeltdreamEnergy0RingItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
}

    @Override
    public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        list.add(Component.literal("品质：§b精良 ★★★"));
        list.add(Component.literal("§7▫ §9身处梦境时 融梦能量+0.15/min"));
}

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (entity instanceof Player player && player.tickCount % 20 == 0) {
            if (level.dimension() == DYEDREAM_WORLD || level.dimension() == LAMP_SHADOW_WORLD) {
}
        }
    }
}
