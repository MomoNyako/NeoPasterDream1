package com.pasterdream.pasterdreammod.registry;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 创造模式物品栏注册类
 * 配置所有创造模式标签页和物品显示
 */
public class PDCreativeTabs {

    /**
     * 创造模式物品栏注册器
     */
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
            BuiltInRegistries.CREATIVE_MODE_TAB, PasterDreamMod.MOD_ID);

    /**
     * 基础材料与功能方块标签页
     * 包含核心功能方块如蓄梦池、染梦书桌等
     */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTER_TAB_0 = TABS.register("paster_tab_0",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.paster_tab_0"))
                    .icon(() -> new ItemStack(PDItems.DREAM_ACCUMULATOR.get()))
                    .displayItems((parameters, output) -> {
                        // 添加试点方块到物品栏
                        output.accept(PDItems.DREAM_ACCUMULATOR.get());
                        output.accept(PDItems.DYEDREAM_DESK.get());
                        output.accept(PDItems.LIFE_CRYSTAL.get());
                        output.accept(PDItems.SHADOW_CHEST.get());
                    })
                    .build());

    /**
     * 刷怪蛋标签页
     * 包含所有生物刷怪蛋
     */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SPAWN_EGGS_TAB = TABS.register("spawn_eggs_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.spawn_eggs_tab"))
                    .icon(() -> new ItemStack(PDItems.SHADOW_GOLEM_SPAWN_EGG.get()))
                    .withTabsBefore(PASTER_TAB_0.getKey())
                    .displayItems((parameters, output) -> {
                        // 添加刷怪蛋到物品栏
                        output.accept(PDItems.SHADOW_GOLEM_SPAWN_EGG.get());
                        output.accept(PDItems.PINK_SLIME_SPAWN_EGG.get());
                    })
                    .build());

    /**
     * PasterDream 物品标签页
     * 包含所有已移植的材料、食物、饰品等物品
     */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTER_TAB_1 = TABS.register("paster_tab_1",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.paster_tab_1"))
                    .icon(() -> new ItemStack(PDItems.TITANIUM_INGOT.get()))
                    .withTabsBefore(PASTER_TAB_0.getKey())
                    .displayItems((parameters, output) -> {
                        // 基础材料
                        output.accept(PDItems.TITANIUM_INGOT.get());
                        output.accept(PDItems.TITANIUM_NUGGET.get());
                        output.accept(PDItems.RAW_TITANIUM.get());
                        output.accept(PDItems.MOLTENGOLD_INGOT.get());
                        output.accept(PDItems.MOLTENGOLD_NUGGET.get());
                        output.accept(PDItems.MOLTENGOLD_DUST.get());
                        output.accept(PDItems.RAW_MOLTENGOLD.get());
                        output.accept(PDItems.DYEDREAM_INGOT.get());
                        output.accept(PDItems.DYEDREAM_NUGGET.get());
                        output.accept(PDItems.DYEDREAM_DUST.get());
                        output.accept(PDItems.DYEDREAM_DUST_PIECE.get());
                        output.accept(PDItems.DYEDREAM_BASE.get());
                        output.accept(PDItems.DYEDREAM_DYE.get());
                        output.accept(PDItems.DYEDREAM_BUD_NUGGET.get());
                        output.accept(PDItems.DYEDREAMQUARTZ.get());
                        output.accept(PDItems.WIND_IRON_INGOT.get());
                        output.accept(PDItems.BLACKMETAL_INGOT.get());
                        output.accept(PDItems.BLACKMETAL_GRAIN.get());
                        output.accept(PDItems.RUST_BLACK_METAL_GRAIN.get());
                        output.accept(PDItems.DREAM_AURORIAN_STEEL.get());
                        output.accept(PDItems.SOUL_DUST.get());
                        output.accept(PDItems.SOUL_ESSENCE.get());
                        output.accept(PDItems.MANADUST.get());
                        output.accept(PDItems.CHARGED_AMETHYST.get());
                        output.accept(PDItems.MAGIC_STONE.get());
                        output.accept(PDItems.PINK_SLIMEBALL.get());
                        output.accept(PDItems.SCULK_HEART.get());
                        output.accept(PDItems.BLACKSTICK.get());
                        output.accept(PDItems.PURE_HORROR.get());
                        output.accept(PDItems.WHITE_CRYSTAL.get());
                        output.accept(PDItems.BLUE_HEART_OF_THE_SEA.get());
                        output.accept(PDItems.ELDER_GUARDIAN_SCALE.get());
                        output.accept(PDItems.BLACK_BEETLE_CARAPACE.get());
                        output.accept(PDItems.BLACK_BEETLE_VOCALCORD.get());
                        output.accept(PDItems.BASALT_SNAIL_SHELL.get());
                        output.accept(PDItems.CONGEAL_WIND.get());
                        output.accept(PDItems.WINDRUNNER_CRYSTAL.get());
                        output.accept(PDItems.PULSE_WINDRUNNER_CRYSTAL.get());
                        output.accept(PDItems.WIND_PLANT_EXTRACT.get());
                        output.accept(PDItems.SHADOW_BREATH.get());
                        output.accept(PDItems.MOSS_PHANTOM_MEMBRANE.get());
                        output.accept(PDItems.LIGHT_MOSS_PHANTOM_MEMBRANE.get());
                        output.accept(PDItems.STRAWBERRY_HEART.get());
                        output.accept(PDItems.COARSE_SALT.get());
                        output.accept(PDItems.SALT.get());
                        output.accept(PDItems.FLOUR.get());
                        output.accept(PDItems.YEAST.get());
                        output.accept(PDItems.EGGDOUGH.get());
                        output.accept(PDItems.COTTON.get());
                        output.accept(PDItems.SPOOL.get());
                        output.accept(PDItems.FABRIC.get());
                        output.accept(PDItems.PERGAMYN.get());
                        output.accept(PDItems.PEN_AND_INK.get());
                        output.accept(PDItems.REEDROD.get());
                        output.accept(PDItems.RYESEED.get());
                        output.accept(PDItems.SORBENT.get());
                        output.accept(PDItems.MORTAR.get());
                        output.accept(PDItems.SILVER_BELL.get());
                        output.accept(PDItems.SHADOW_HILT.get());
                        output.accept(PDItems.SHADOW_DUNGEON_KEY.get());
                        output.accept(PDItems.NIGHTMARE_FUEL.get());
                        output.accept(PDItems.PROTECT_DECK.get());
                        output.accept(PDItems.ENHANCE_STONE_0.get());
                        output.accept(PDItems.ENHANCE_STONE_1.get());
                        output.accept(PDItems.DREAMWISH.get());
                        output.accept(PDItems.DREAM_METER.get());
                        output.accept(PDItems.MELTDREAM_CRYSTAL_0.get());

                        // 武器原胚
                        output.accept(PDItems.SWORD_EMBRYO_0.get());
                        output.accept(PDItems.SHADOW_SWORD_EMBRYO.get());
                        output.accept(PDItems.SHADOW_EROSION_SWORD_EMBRYO.get());
                        output.accept(PDItems.SHADOW_EROSION_AXE_EMBRYO.get());
                        output.accept(PDItems.SHADOW_EROSION_PICKAXE_EMBRYO.get());
                        output.accept(PDItems.SHADOW_EROSION_HOE_EMBRYO.get());
                        output.accept(PDItems.SHADOW_EROSION_SHOVEL_EMBRYO.get());
                        output.accept(PDItems.ICESHADOW_HAMMER_EMBRYO.get());
                        output.accept(PDItems.WHITE_SWORD_EMBRYO.get());
                        output.accept(PDItems.TERRASWORD_EMBRYO.get());
                        output.accept(PDItems.STAR_WISH_ROD_EMBRYO.get());
                        output.accept(PDItems.TITANIUM_UPGRADE.get());
                        output.accept(PDItems.DYEDREAM_UPGRADE.get());
                        output.accept(PDItems.SCULK_UPGRADE.get());

                        // 食物类
                        output.accept(PDItems.DYEDREAM_FRUIT.get());
                        output.accept(PDItems.DYEDREAM_JUICE.get());
                        output.accept(PDItems.DYEDREAM_FLOWER_TEA.get());
                        output.accept(PDItems.UNCOOKED_DYEDREAM_FLOWER_TEA.get());
                        output.accept(PDItems.DYEDREAM_POPSICLE.get());
                        output.accept(PDItems.DYEDREAM_FRUIT_BUNCAKE.get());
                        output.accept(PDItems.BUBBLE_TEA.get());
                        output.accept(PDItems.APPLE_JUICE.get());
                        output.accept(PDItems.HONEY_JUICE.get());
                        output.accept(PDItems.WATERMELON_JUICE.get());
                        output.accept(PDItems.BREAD_SLICE.get());
                        output.accept(PDItems.CAKE_BASE.get());
                        output.accept(PDItems.WAFER_BISCUIT.get());
                        output.accept(PDItems.CHOCOLATE.get());
                        output.accept(PDItems.CHOCOLATE_MATCHA_CAKE.get());
                        output.accept(PDItems.SWISS_ROLL.get());
                        output.accept(PDItems.STUFFED_WAFER_COOKIES.get());
                        output.accept(PDItems.CREAM_BUNCAKE.get());
                        output.accept(PDItems.BERRY_BUNCAKE.get());
                        output.accept(PDItems.POTATO_BUNCAKE.get());
                        output.accept(PDItems.MELON_BUNCAKE.get());
                        output.accept(PDItems.PUMPKIN_BUNCAKE.get());
                        output.accept(PDItems.GLOW_BERRY_BUNCAKE.get());
                        output.accept(PDItems.FIG.get());
                        output.accept(PDItems.BACONE_EGG.get());
                        output.accept(PDItems.ODD_BACONE_EGG.get());
                        output.accept(PDItems.FRIED_EGG.get());
                        output.accept(PDItems.SANDWICH.get());
                        output.accept(PDItems.RICECAKE.get());
                        output.accept(PDItems.GINGERBREAD_MAN.get());
                        output.accept(PDItems.CANDY_CANE.get());
                        output.accept(PDItems.AMBER_CANDY.get());
                        output.accept(PDItems.POPPING_CANDY.get());
                        output.accept(PDItems.BUBBLE_GUM.get());
                        output.accept(PDItems.DREAM_COTTON_CANDY.get());
                        output.accept(PDItems.YINHUL_COTTON_CANDY.get());
                        output.accept(PDItems.HEART_CHOCOLATE_0.get());
                        output.accept(PDItems.HEART_CHOCOLATE_1.get());
                        output.accept(PDItems.HEART_CHOCOLATE_2.get());
                        output.accept(PDItems.JELLYFISH_MUD.get());
                        output.accept(PDItems.JELLYFISH_JELLO.get());
                        output.accept(PDItems.PINEAPPLE_LOVE_SEA.get());
                        output.accept(PDItems.GOLDENROD_TEA.get());
                        output.accept(PDItems.LEGEND_DRAGON_HORN_ICE_CREAM.get());
                        output.accept(PDItems.LIGHT_ORGAN.get());
                        output.accept(PDItems.QUEER_SOUP.get());
                        output.accept(PDItems.MELTDREAM_ELIXIR_BOTTLE.get());
                        output.accept(PDItems.RAGE_ELIXIR_0.get());
                        output.accept(PDItems.ELIXIR_BOTTLE.get());
                        output.accept(PDItems.DYEDREAM_PERFUME.get());
                        output.accept(PDItems.WATER_GLASSJAR.get());
                        output.accept(PDItems.MILK_GLASSJAR.get());
                        output.accept(PDItems.GLASSJAR.get());

                        // 特殊物品
                        output.accept(PDItems.BLUE_DEW.get());
                        output.accept(PDItems.RED_DEW_0.get());
                        output.accept(PDItems.DREAM_COIN_0.get());
                        output.accept(PDItems.DREAM_COIN_1.get());
                        output.accept(PDItems.BROKENNOTES_0.get());
                        output.accept(PDItems.UNKNOWNNOTES_0.get());
                        output.accept(PDItems.MEMENTO_ITEM_01.get());
                        output.accept(PDItems.MEMORY_GEM_0.get());
                        output.accept(PDItems.DREAM_FERTILIZER.get());
                        output.accept(PDItems.GUIDING_DRUG.get());
                        output.accept(PDItems.SQUEAL_WAVE.get());
                        output.accept(PDItems.CRADLE_IN_ONES_ARMS.get());
                        output.accept(PDItems.PALE_BONENEEDLE.get());
                        output.accept(PDItems.WHITE_COROLLA.get());
                        output.accept(PDItems.DYEDREAM_COROLLA.get());
                        output.accept(PDItems.SCULK_HEART.get());
                        output.accept(PDItems.BLACK_BEETLE_CARAPACE.get());
                        output.accept(PDItems.BLACK_BEETLE_VOCALCORD.get());
                        output.accept(PDItems.CHARGED_AMETHYST.get());

                        // 维度传送
                        output.accept(PDItems.DYEDREAM_TELEPORT_CRYSTAL.get());

                        // 饰品/Curio
                        output.accept(PDItems.EMBRYO_CHARM.get());
                        output.accept(PDItems.EMBRYO_RING.get());
                        output.accept(PDItems.EMBRYO_NECKLACE.get());
                        output.accept(PDItems.EMBRYO_BELT.get());
                        output.accept(PDItems.HITHARD_0_RING.get());
                        output.accept(PDItems.HITHARD_1_RING.get());
                        output.accept(PDItems.RED_DEW_0_RING.get());
                        output.accept(PDItems.RED_DEW_1_RING.get());
                        output.accept(PDItems.RED_DEW_2_RING.get());
                        output.accept(PDItems.RED_DEW_3_RING.get());
                        output.accept(PDItems.MELTDREAM_ENERGY_0_RING.get());
                        output.accept(PDItems.COUNTER_RING.get());
                        output.accept(PDItems.DARK_ALLLEGORY_CURIO.get());
                        output.accept(PDItems.CECILIACARE_CHARM.get());
                        output.accept(PDItems.CARAPAX_CHARM.get());
                        output.accept(PDItems.SEA_CHARM.get());
                        output.accept(PDItems.CALAIS_SPICE_BOTTLE_CURIO.get());
                        output.accept(PDItems.ANGEL_WING.get());
                        output.accept(PDItems.MACHINE_WING.get());
                        output.accept(PDItems.FORSAKENS_WING.get());
                        output.accept(PDItems.GROUND_WING.get());
                        output.accept(PDItems.WINGS_OF_FANG.get());
                        output.accept(PDItems.WIND_KNIGHT_FLAG.get());
                        output.accept(PDItems.GHOST_FACE_HEAD.get());
                    })
                    .build());

    /**
     * 染梦世界方块标签页
     * 包含染梦世界维度中的所有原生方块
     */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTER_TAB_DYEDREAM = TABS.register("paster_tab_dyedream",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.paster_tab_dyedream"))
                    .icon(() -> new ItemStack(PDBlocks.DYEDREAM_BLOCK.get()))
                    .withTabsBefore(PASTER_TAB_1.getKey())
                    .displayItems((parameters, output) -> {
                        // 天然方块
                        output.accept(PDBlocks.DYEDREAM_GRASS.get());
                        output.accept(PDBlocks.DYEDREAM_DIRT.get());
                        output.accept(PDBlocks.DYEDREAM_SAND.get());
                        output.accept(PDBlocks.DYEDREAM_BLOCK.get());
                        output.accept(PDBlocks.ICESTONE.get());
                        output.accept(PDBlocks.DYEDREAM_ICE.get());
                        output.accept(PDBlocks.DYEDREAM_PACKED_ICE.get());
                        output.accept(PDBlocks.PINKSLIME_BLOCK.get());
                        output.accept(PDBlocks.DYEDREAMQUARTZ_ORE.get());
                        output.accept(PDBlocks.DYEDREAMDUST_ORE.get());
                        output.accept(PDBlocks.AMBER_CANDY_ORE.get());
                        output.accept(PDBlocks.DYEDREAMQUARTZ_BLOCK.get());
                        output.accept(PDBlocks.SMOOTH_DYEDREAMQUARTZ_BLOCK.get());
                        output.accept(PDBlocks.BRICKS_DYEDREAMQUARTZ_BLOCK.get());
                        output.accept(PDBlocks.CHISELED_DYEDREAMQUARTZ_BLOCK.get());
                        output.accept(PDBlocks.PILLAR_DYEDREAMQUARTZ_BLOCK.get());
                        output.accept(PDBlocks.DYEDREAMQUARTZ_BLOCK_STAIRS.get());
                        output.accept(PDBlocks.DYEDREAMQUARTZ_BLOCK_SLAB.get());
                        output.accept(PDBlocks.DYEDREAMQUARTZ_BLOCK_WALL.get());

                        // 树木与木板
                        output.accept(PDBlocks.DYEDREAM_LOG.get());
                        output.accept(PDBlocks.DYEDREAM_WOOD.get());
                        output.accept(PDBlocks.DYEDREAM_LEAVES.get());
                        output.accept(PDBlocks.DYEDREAM_WORLDTREE_LEAVES.get());
                        output.accept(PDBlocks.DYEDREAM_PLANKS.get());
                        output.accept(PDBlocks.DYEDREAM_PLANKS_STAIRS.get());
                        output.accept(PDBlocks.DYEDREAM_PLANKS_SLAB.get());
                        output.accept(PDBlocks.DYEDREAM_PLANKS_FENCE.get());
                        output.accept(PDBlocks.DYEDREAM_PLANKS_FENCEGATE.get());
                        output.accept(PDBlocks.DYEDREAM_PLANKS_DOOR.get());
                        output.accept(PDBlocks.DYEDREAM_PLANKS_TRAPDOOR.get());
                        output.accept(PDBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE.get());
                        output.accept(PDBlocks.DYEDREAM_PLANKS_BUTTON.get());
                        output.accept(PDBlocks.DYEDREAM_PLANKS_PANE.get());

                        // 花蕾系列
                        output.accept(PDBlocks.DYEDREAM_BUD_BLOCK.get());
                        output.accept(PDBlocks.DYEDREAM_BUD_STAIRS.get());
                        output.accept(PDBlocks.DYEDREAM_BUD_SLAB.get());
                        output.accept(PDBlocks.DYEDREAM_BUD_WALL.get());
                        output.accept(PDBlocks.DYEDREAM_BUD_0.get());
                        output.accept(PDBlocks.DYEDREAM_BUD_1.get());
                        output.accept(PDBlocks.DYEDREAM_BUD_2.get());

                        // 冰蕾
                        output.accept(PDBlocks.ICE_BUD_0.get());

                        // 粉丁菇
                        output.accept(PDBlocks.PINKAGARIC_0.get());
                        output.accept(PDBlocks.PINKAGARIC_1.get());
                        output.accept(PDBlocks.PINKAGARIC_2.get());
                        output.accept(PDBlocks.PINKAGARIC_3.get());

                        // 水面植物
                        output.accept(PDBlocks.DYEDREAM_LILY_PAD.get());
                        output.accept(PDBlocks.DYEDREAM_LOTUS.get());
                        output.accept(PDBlocks.DYEDREAM_SEAGRASS.get());

                        // 树苗与裂纹
                        output.accept(PDBlocks.DYEDREAM_SAPLING.get());
                        output.accept(PDBlocks.DYEDREAM_CRACK.get());

                        // 装饰方块
                        output.accept(PDBlocks.DYEDREAM_GLASS.get());
                        output.accept(PDBlocks.DYEDREAM_GLASSPANE.get());
                        output.accept(PDBlocks.CARVE_DYEDREAM_GLASS.get());
                        output.accept(PDBlocks.CARVE_DYEDREAM_GLASSPANE.get());
                        output.accept(PDBlocks.GOLD_CARVE_DYEDREAM_GLASS.get());
                        output.accept(PDBlocks.GOLD_CARVE_DYEDREAM_GLASSPANE.get());
                        output.accept(PDBlocks.DYEDREAM_LARTERN.get());
                    })
                    .build());

}
