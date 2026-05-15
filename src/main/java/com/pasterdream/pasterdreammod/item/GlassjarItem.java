package com.pasterdream.pasterdreammod.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

/**
 * glassjar 物品类
 * 原版稀有度: COMMON
 */
public class GlassjarItem extends Item {

    /**
     * 构造方法
     *
     * @param properties 物品属性
     */
    public GlassjarItem(Item.Properties properties) {
        super(properties.stacksTo(64));
}

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return InteractionResultHolder.pass(player.getItemInHand(hand));
}
}
