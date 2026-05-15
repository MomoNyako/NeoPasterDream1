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
 * DyedreamSword0
 */
public class DyedreamSword0Item extends SwordItem {

    private static final Tier DYEDREAMSWORD0ITEM_TIER = new SimpleTier(
        BlockTags.INCORRECT_FOR_STONE_TOOL,
        131, 4.0f, 1.0f, 5,
        () -> Ingredient.of(Items.COBBLESTONE)
    );

    public DyedreamSword0Item() {
        super(DYEDREAMSWORD0ITEM_TIER, new Item.Properties().attributes(
            SwordItem.createAttributes(DYEDREAMSWORD0ITEM_TIER, 3, -2.4f)
        ));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
                        list.add(Component.literal("\u9576\u5D4C\uFF1A\u00A7d\u878D\u68A6\u6C34\u6676\u788E\u7247"));
                        list.add(Component.literal("\u6218\u6280\uFF1A\u00A7b\u51CC\u7A7A\u65A9"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u65A9\u51FB\u5E76\u5C06\u654C\u4EBA\u5411\u4E0A\u51FB\u98DE"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u9020\u62102+\u5F53\u524D\u653B\u51FB\u529B*1.2\u70B9\u4F24\u5BB3"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u51B7\u5374\u65F6\u95F4\uFF1A5\u79D2"));
                        list.add(Component.literal("\u00A77\u25AA \u00A79\u62E5\u6709\u67D3\u68A6\u5B88\u62A4\u6548\u679C\u65F6 \u51B7\u5374\u65F6\u95F4\u7F29\u77ED\u4E3A3\u79D2"));
                        list.add(Component.literal("\u53F3\u952E\u4EE5\u5F3A\u5316\u4E0B\u6B21\u653B\u51FB\u4E3A\u7279\u6B8A\u653B\u51FB"));
    }

}