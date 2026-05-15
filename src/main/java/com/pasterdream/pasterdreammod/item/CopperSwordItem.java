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

/**
 * Copper Sword
 */
public class CopperSwordItem extends SwordItem {

    private static final Tier COPPERSWORDITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_WOODEN_TOOL,
        59, 2.0f, 0.0f, 15,
        () -> Ingredient.of(Items.OAK_PLANKS)
    );

    public CopperSwordItem() {
        super(COPPERSWORDITEM_TIER, new Item.Properties().attributes(
            SwordItem.createAttributes(COPPERSWORDITEM_TIER, 3, -2.4f)
        ));
    }

}