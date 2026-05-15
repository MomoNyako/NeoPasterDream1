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
 * Thermal Dagger
 */
public class ThermalDaggerItem extends SwordItem {

    private static final Tier THERMALDAGGERITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_WOODEN_TOOL,
        59, 2.0f, 0.0f, 15,
        () -> Ingredient.of(Items.OAK_PLANKS)
    );

    public ThermalDaggerItem() {
        super(THERMALDAGGERITEM_TIER, new Item.Properties().attributes(
            SwordItem.createAttributes(THERMALDAGGERITEM_TIER, 3, -2.3f)
        ));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
                        list.add(Component.literal("\u00A77\u00A7o-- \u6DF1\u6D77\u8FF7\u822A"));
    }

}