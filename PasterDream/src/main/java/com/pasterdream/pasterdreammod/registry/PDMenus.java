package com.pasterdream.pasterdreammod.registry;

import com.pasterdream.pasterdreammod.api.menu.MenuAPI;
import com.pasterdream.pasterdreammod.menu.DreamCauldronMenu;
import com.pasterdream.pasterdreammod.menu.DyedreamDeskMenu;
import com.pasterdream.pasterdreammod.menu.MeltdreamChestMenu;
import com.pasterdream.pasterdreammod.menu.ShadowChestMenu;
import com.pasterdream.pasterdreammod.menu.TheEndlessBookOfDreamSeekersMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * 菜单类型注册类
 * <p>
 * 使用 {@link MenuAPI} 统一注册所有 AbstractContainerMenu 类型，避免维护独立的 DeferredRegister。
 */
public class PDMenus {

    /**
     * 影之箱 GUI 菜单类型
     * 用于打开 15 格容器的箱子界面
     */
    public static final DeferredHolder<MenuType<?>, MenuType<ShadowChestMenu>> SHADOW_CHEST =
            MenuAPI.<ShadowChestMenu>createMenu("shadow_chest")
                    .factory(ShadowChestMenu::new)
                    .build();

    /**
     * 染梦书桌 GUI 菜单类型
     * 用于打开 1 格展示槽的界面（最大堆叠 1）
     */
    public static final DeferredHolder<MenuType<?>, MenuType<DyedreamDeskMenu>> DYEDREAM_DESK =
            MenuAPI.<DyedreamDeskMenu>createMenu("dyedream_desk")
                    .factory(DyedreamDeskMenu::new)
                    .build();

    /**
     * 融梦水晶箱 GUI 菜单类型
     * 用于打开 9 格容器的箱子界面
     */
    public static final DeferredHolder<MenuType<?>, MenuType<MeltdreamChestMenu>> MELTDREAM_CHEST =
            MenuAPI.<MeltdreamChestMenu>createMenu("meltdream_chest")
                    .factory(MeltdreamChestMenu::new)
                    .build();

    /**
     * 寻梦者的永恒书卷 GUI 菜单类型
     * 1 格展示槽 + 玩家背包
     */
    public static final DeferredHolder<MenuType<?>, MenuType<TheEndlessBookOfDreamSeekersMenu>> THE_ENDLESS_BOOK_OF_DREAM_SEEKERS =
            MenuAPI.<TheEndlessBookOfDreamSeekersMenu>createMenu("the_endless_book_of_dream_seekers")
                    .factory(TheEndlessBookOfDreamSeekersMenu::new)
                    .build();

    /**
     * 梦境炼药锅 GUI 菜单类型
     * 3 输入槽 + 1 输出槽 + 玩家背包
     */
    public static final DeferredHolder<MenuType<?>, MenuType<DreamCauldronMenu>> DREAM_CAULDRON =
            MenuAPI.<DreamCauldronMenu>createMenu("dream_cauldron")
                    .factory(DreamCauldronMenu::new)
                    .build();
}

