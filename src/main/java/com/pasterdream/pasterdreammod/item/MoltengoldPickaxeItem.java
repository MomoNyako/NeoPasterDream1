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
 * Moltengold Pickaxe
 */
public class MoltengoldPickaxeItem extends PickaxeItem {

    private static final Tier MOLTENGOLDPICKAXEITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_STONE_TOOL,
        131, 4.0f, 1.0f, 5,
        () -> Ingredient.of(Items.COBBLESTONE)
    );

    public MoltengoldPickaxeItem() {
        super(MOLTENGOLDPICKAXEITEM_TIER, new Item.Properties().attributes(
            PickaxeItem.createAttributes(MOLTENGOLDPICKAXEITEM_TIER, 1, -2.7f)
        ));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
                        list.add(Component.literal("\u9576\u5D4C\uFF1A\u00A77\u65E0"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u53F3\u952E\u4F7F\u7528\u5C06\u6D88\u8017\u9971\u98DF\u5EA6\u6765\u83B7\u5F97\u77ED\u6682\u6025\u8FEB\u6548\u679C"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u51B7\u5374\u65F6\u95F4\uFF1A10\u79D2"));
    }

}