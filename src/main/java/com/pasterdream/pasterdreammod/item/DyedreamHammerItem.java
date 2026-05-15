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
 * Dyedream Hammer
 */
public class DyedreamHammerItem extends PickaxeItem {

    private static final Tier DYEDREAMHAMMERITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
        2031, 9.0f, 4.0f, 15,
        () -> Ingredient.of(Items.NETHERITE_INGOT)
    );

    public DyedreamHammerItem() {
        super(DYEDREAMHAMMERITEM_TIER, new Item.Properties().attributes(
            PickaxeItem.createAttributes(DYEDREAMHAMMERITEM_TIER, 1, -2.8f)
        ));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
                        list.add(Component.literal("\u00A7f\u25AA \u00A79\u8303\u56F4\u6316\u6398 3*3*3"));
    }

}