package com.pasterdream.pasterdreammod.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class TestCurioItem extends Item implements ICurioItem {
    public TestCurioItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
}
}