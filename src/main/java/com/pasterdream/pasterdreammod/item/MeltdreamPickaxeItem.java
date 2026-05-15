package com.pasterdream.pasterdreammod.item;

import com.pasterdream.pasterdreammod.registry.PDItems;
import net.minecraft.world.item.PickaxeItem;
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
 * Meltdream Pickaxe
 */
public class MeltdreamPickaxeItem extends PickaxeItem {

    private static final Tier MELTDREAMPICKAXEITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_IRON_TOOL,
        250, 6.0f, 2.0f, 14,
        () -> Ingredient.of(Items.IRON_INGOT)
    );

    public MeltdreamPickaxeItem() {
        super(MELTDREAMPICKAXEITEM_TIER, new Item.Properties().attributes(
            PickaxeItem.createAttributes(MELTDREAMPICKAXEITEM_TIER, 1, -2.8f)
        ));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
                        list.add(Component.literal("\u00A7f\u624B\u6301\u5DE5\u5177\u65F6"));
                        list.add(Component.literal("\u00A7f\u25AA \u00A77\u878D\u68A6\u4FEE\u8865\uFF1A0.01E/1\u8010\u4E45"));
    }

}