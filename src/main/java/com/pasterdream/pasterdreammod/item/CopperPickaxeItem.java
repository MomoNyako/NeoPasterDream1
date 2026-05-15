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

/**
 * Copper Pickaxe
 */
public class CopperPickaxeItem extends PickaxeItem {

    private static final Tier COPPERPICKAXEITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_STONE_TOOL,
        131, 4.0f, 1.0f, 5,
        () -> Ingredient.of(Items.COBBLESTONE)
    );

    public CopperPickaxeItem() {
        super(COPPERPICKAXEITEM_TIER, new Item.Properties().attributes(
            PickaxeItem.createAttributes(COPPERPICKAXEITEM_TIER, 1, -2.8f)
        ));
    }

}