package com.pasterdream.pasterdreammod.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

/**
 * dyedream_perfume 物品类
 * 原版稀有度: COMMON
 */
public class DyedreamPerfumeItem extends Item {

    /**
     * 构造方法
     *
     * @param properties 物品属性
     */
    public DyedreamPerfumeItem(Item.Properties properties) {
        super(properties.stacksTo(64).food(new FoodProperties.Builder()
                    .nutrition(0).saturationModifier(0f).build()));
}

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("\u00A77\u996E\u7528\u540E\u83B7\u5F97\u6548\u679C:"));
        tooltipComponents.add(Component.literal("\u00A77\u25AA \u00A79\u8BA9\u8718\u86DB\u8FDC\u79BB\u4F60 (1:00)"));
        tooltipComponents.add(Component.literal("\u00A77\u25AA \u00A79\u6E05\u7A7A\u672A\u7761\u7720\u7D2F\u8BA1\u91CF\u8868"));
        tooltipComponents.add(Component.literal("\u00A77\u00A7o\u5F53\u4F60\u5728\u601D\u8003\u4E3A\u4EC0\u4E48\u9999\u6C34\u8981\u7528\u6765\u559D\u65F6 \u6216\u8BB8\u5E94\u8BE5\u5148\u8003\u8651\u4F60\u73B0\u5728\u6B63\u5728\u505A\u68A6\uFF1F"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
