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
 * TruestMoltengold Sword
 */
public class TruestMoltengoldSwordItem extends SwordItem {

    private static final Tier TRUESTMOLTENGOLDSWORDITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_STONE_TOOL,
        131, 4.0f, 1.0f, 5,
        () -> Ingredient.of(Items.COBBLESTONE)
    );

    public TruestMoltengoldSwordItem() {
        super(TRUESTMOLTENGOLDSWORDITEM_TIER, new Item.Properties().attributes(
            SwordItem.createAttributes(TRUESTMOLTENGOLDSWORDITEM_TIER, 3, -2.15f)
        ));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
                        list.add(Component.literal("\u9576\u5D4C\uFF1A\u00A7b\u4E0B\u754C\u4E4B\u661F"));
                        list.add(Component.literal("\u6218\u6280\uFF1A\u00A76\u878D\u9AB8\u65A9\u51FB"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u653B\u51FB\u4F24\u5BB3\u7C7B\u578B\u8F6C\u6362\u4E3A\u7194\u5CA9"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u9020\u62102+\u5F53\u524D\u653B\u51FB\u529B*1.1+\u76EE\u6807\u5F15\u71C3\u6301\u7EED\u79D2\u6570*0.6\u70B9\u4F24\u5BB3"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u5982\u76EE\u6807\u5F15\u71C3\u65F6\u957F\u5927\u4E8E10\u79D2 \u5219\u51CF\u5C11\u76EE\u6807\u79FB\u901F30%"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u5E76\u6E05\u9664\u76EE\u6807\u7684\u5F15\u71C3\u6548\u679C"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u51B7\u5374\u65F6\u95F4\uFF1A10\u79D2"));
                        list.add(Component.literal("\u00A77\u53F3\u952E\u4EE5\u5F3A\u5316\u4E0B\u6B21\u653B\u51FB\u4E3A\u7279\u6B8A\u653B\u51FB"));
                        list.add(Component.literal("\u88AB\u52A8\uFF1A"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u653B\u51FB\u5C06\u4F1A\u4F7F\u76EE\u6807\u88AB\u5F15\u71C34\u79D2"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u5982\u76EE\u6807\u5DF2\u88AB\u5F15\u71C3 \u5219\u4F1A\u5728\u76EE\u6807\u5F15\u71C3\u7684\u6301\u7EED\u65F6\u95F4\u4E0A\u589E\u52A03\u79D2 \u53EF\u53E0\u52A0"));
    }

}