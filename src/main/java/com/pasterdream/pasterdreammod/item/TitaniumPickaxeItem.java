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
 * Titanium Pickaxe
 */
public class TitaniumPickaxeItem extends PickaxeItem {

    private static final Tier TITANIUMPICKAXEITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
        2031, 9.0f, 4.0f, 15,
        () -> Ingredient.of(Items.NETHERITE_INGOT)
    );

    public TitaniumPickaxeItem() {
        super(TITANIUMPICKAXEITEM_TIER, new Item.Properties().attributes(
            PickaxeItem.createAttributes(TITANIUMPICKAXEITEM_TIER, 1, -2.8f)
        ));
    }

}