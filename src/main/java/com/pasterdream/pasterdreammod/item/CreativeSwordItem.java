package com.pasterdream.pasterdreammod.item;

import com.pasterdream.pasterdreammod.registry.PDItems;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.SimpleTier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import java.util.List;

/**
 * Creative Sword
 */
public class CreativeSwordItem extends SwordItem {

    private static final Tier CREATIVESWORDITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_STONE_TOOL,
        131, 4.0f, 1.0f, 5,
        () -> Ingredient.of(Items.COBBLESTONE)
    );

    public CreativeSwordItem() {
        super(CREATIVESWORDITEM_TIER, new Item.Properties().attributes(
            SwordItem.createAttributes(CREATIVESWORDITEM_TIER, 3, -2.4f)
        ));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
                        list.add(Component.literal("\u00A77\u521B\u9020\u6A21\u5F0F\u8C03\u8BD5\u5DE5\u5177"));
                        list.add(Component.literal("\u00A77\u5F3A\u5236\u51FB\u6740\u5E76\u6E05\u9664\u5B9E\u4F53"));
    }

}