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
 * Titanium Sword
 */
public class TitaniumSwordItem extends SwordItem {

    private static final Tier TITANIUMSWORDITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_STONE_TOOL,
        131, 4.0f, 1.0f, 5,
        () -> Ingredient.of(Items.COBBLESTONE)
    );

    public TitaniumSwordItem() {
        super(TITANIUMSWORDITEM_TIER, new Item.Properties().attributes(
            SwordItem.createAttributes(TITANIUMSWORDITEM_TIER, 3, -2.4f)
        ));
    }

}