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
 * TrueGrass Sword
 */
public class TrueGrassSwordItem extends SwordItem {

    private static final Tier TRUEGRASSSWORDITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_STONE_TOOL,
        131, 4.0f, 1.0f, 5,
        () -> Ingredient.of(Items.COBBLESTONE)
    );

    public TrueGrassSwordItem() {
        super(TRUEGRASSSWORDITEM_TIER, new Item.Properties().attributes(
            SwordItem.createAttributes(TRUEGRASSSWORDITEM_TIER, 3, -2.5f)
        ));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
                        list.add(Component.literal("\u9576\u5D4C\uFF1A\u00A77200\u4EA1\u9B42"));
                        list.add(Component.literal("\u6218\u6280\uFF1A\u00A7b\u6781\u81F4\u950B\u5229"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u9996\u6B21\u653B\u51FB\u76EE\u6807\u65F6\u89E6\u53D1"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u653B\u51FB\u9020\u62107+\u6B66\u5668\u950B\u5229\u7B49\u7EA7*\u6B66\u5668\u57FA\u7840\u9762\u677F/2\u70B9\u4F24\u5BB3"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u51B7\u5374\u65F6\u95F4\uFF1A4\u79D2"));
                        list.add(Component.literal("\u88AB\u52A8\uFF1A"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u653B\u51FB\u65BD\u52A0\u66F4\u957F\u65F6\u95F4\u7684\u4E2D\u6BD2\u6548\u679C"));
    }

}