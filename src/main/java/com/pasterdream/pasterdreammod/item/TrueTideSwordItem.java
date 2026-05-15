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
 * TrueTide Sword
 */
public class TrueTideSwordItem extends SwordItem {

    private static final Tier TRUETIDESWORDITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_STONE_TOOL,
        131, 4.0f, 1.0f, 5,
        () -> Ingredient.of(Items.COBBLESTONE)
    );

    public TrueTideSwordItem() {
        super(TRUETIDESWORDITEM_TIER, new Item.Properties().attributes(
            SwordItem.createAttributes(TRUETIDESWORDITEM_TIER, 3, -2.8f)
        ));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
                        list.add(Component.literal("\u9576\u5D4C\uFF1A\u00A73\u6D77\u6D0B\u4E4B\u5FC3"));
                        list.add(Component.literal("\u6218\u6280\uFF1A\u00A73\u6D8C\u6D41\u5251\u6280"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u5728\u6C34\u4E2D\u5411\u6307\u9488\u65B9\u5411\u51B2\u523A \u51B2\u523A\u671F\u95F4\u514D\u75AB80%\u7684\u4F24\u5BB3"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u653B\u51FB\u7684\u76EE\u6807\u5904\u4E8E\u6C34\u65F6\u89E6\u53D1"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u653B\u51FB\u9020\u62103+\u5F53\u524D\u653B\u51FB\u529B*1.2\u70B9\u4F24\u5BB3"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u51B7\u5374\u65F6\u95F4\uFF1A2.5\u79D2"));
                        list.add(Component.literal("\u88AB\u52A8\uFF1A"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u624B\u6301\u6B66\u5668\u65F6\u83B7\u5F97\u6C34\u4E0B\u547C\u5438\u6548\u679C"));
    }

}