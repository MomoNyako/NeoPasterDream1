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
 * Grass Sword
 */
public class GrassSwordItem extends SwordItem {

    private static final Tier GRASSSWORDITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_STONE_TOOL,
        131, 4.0f, 1.0f, 5,
        () -> Ingredient.of(Items.COBBLESTONE)
    );

    public GrassSwordItem() {
        super(GRASSSWORDITEM_TIER, new Item.Properties().attributes(
            SwordItem.createAttributes(GRASSSWORDITEM_TIER, 3, -2.5f)
        ));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
                        list.add(Component.literal("\u9576\u5D4C\uFF1A\u00A77\u65E0"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u653B\u51FB\u65BD\u52A0\u4E2D\u6BD2\u6548\u679C"));
    }

}