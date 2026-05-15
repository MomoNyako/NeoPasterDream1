package com.pasterdream.pasterdreammod.registry;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 物品注册类
 * 使用 DeferredRegister 模式注册所有物品
 */
public class PDItems {

    /**
     * 物品注册器
     */
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PasterDreamMod.MOD_ID);

    /**
     * 蓄梦池物品 (dream_accumulator)
     * 使用 DreamAccumulatorDisplayItem 实现手持 GeckoLib 动画
     */
    public static final DeferredItem<BlockItem> DREAM_ACCUMULATOR = ITEMS.register("dream_accumulator",
            () -> new DreamAccumulatorDisplayItem(PDBlocks.DREAM_ACCUMULATOR.get(), new Item.Properties()));

    /**
     * 染梦书桌物品 (dyedream_desk)
     * 对应 PDBlocks.DYEDREAM_DESK 方块
     */
    public static final DeferredItem<BlockItem> DYEDREAM_DESK = ITEMS.registerSimpleBlockItem("dyedream_desk",
            PDBlocks.DYEDREAM_DESK);

    /**
     * 生命水晶物品 (life_crystal)
     * 对应 PDBlocks.LIFE_CRYSTAL 方块
     * 使用 LifeCrystalDisplayItem 实现手持 3D 渲染
     * 站在附近可以缓慢恢复生命值
     */
    public static final DeferredItem<LifeCrystalDisplayItem> LIFE_CRYSTAL = ITEMS.register("life_crystal",
            () -> new LifeCrystalDisplayItem(new Item.Properties()));

    /**
     * 影之箱子物品 (shadow_chest)
     * 对应 PDBlocks.SHADOW_CHEST 方块
     * 使用 ShadowChestDisplayItem 实现手持 3D 渲染
     * 装饰性方块，无存储功能
     */
    public static final DeferredItem<ShadowChestDisplayItem> SHADOW_CHEST = ITEMS.register("shadow_chest",
            () -> new ShadowChestDisplayItem(new Item.Properties()));

    // ==================== 染梦世界方块物品 ====================

    public static final DeferredItem<BlockItem> DYEDREAM_BLOCK = ITEMS.registerSimpleBlockItem("dyedream_block", PDBlocks.DYEDREAM_BLOCK);
    public static final DeferredItem<BlockItem> DYEDREAM_DIRT = ITEMS.registerSimpleBlockItem("dyedream_dirt", PDBlocks.DYEDREAM_DIRT);
    public static final DeferredItem<BlockItem> DYEDREAM_SAND = ITEMS.registerSimpleBlockItem("dyedream_sand", PDBlocks.DYEDREAM_SAND);
    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS = ITEMS.registerSimpleBlockItem("dyedream_planks", PDBlocks.DYEDREAM_PLANKS);
    public static final DeferredItem<BlockItem> DYEDREAM_GLASS = ITEMS.registerSimpleBlockItem("dyedream_glass", PDBlocks.DYEDREAM_GLASS);
    public static final DeferredItem<BlockItem> DYEDREAM_ICE = ITEMS.registerSimpleBlockItem("dyedream_ice", PDBlocks.DYEDREAM_ICE);
    public static final DeferredItem<BlockItem> DYEDREAM_PACKED_ICE = ITEMS.registerSimpleBlockItem("dyedream_packed_ice", PDBlocks.DYEDREAM_PACKED_ICE);
    public static final DeferredItem<BlockItem> DYEDREAMQUARTZ_BLOCK = ITEMS.registerSimpleBlockItem("dyedreamquartz_block", PDBlocks.DYEDREAMQUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> SMOOTH_DYEDREAMQUARTZ_BLOCK = ITEMS.registerSimpleBlockItem("smooth_dyedreamquartz_block", PDBlocks.SMOOTH_DYEDREAMQUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> BRICKS_DYEDREAMQUARTZ_BLOCK = ITEMS.registerSimpleBlockItem("bricks_dyedreamquartz_block", PDBlocks.BRICKS_DYEDREAMQUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> CHISELED_DYEDREAMQUARTZ_BLOCK = ITEMS.registerSimpleBlockItem("chiseled_dyedreamquartz_block", PDBlocks.CHISELED_DYEDREAMQUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_BLOCK = ITEMS.registerSimpleBlockItem("dyedream_bud_block", PDBlocks.DYEDREAM_BUD_BLOCK);
    public static final DeferredItem<BlockItem> PINKSLIME_BLOCK = ITEMS.registerSimpleBlockItem("pinkslime_block", PDBlocks.PINKSLIME_BLOCK);
    public static final DeferredItem<BlockItem> ICESTONE = ITEMS.registerSimpleBlockItem("icestone", PDBlocks.ICESTONE);
    public static final DeferredItem<BlockItem> DYEDREAM_LEAVES = ITEMS.registerSimpleBlockItem("dyedream_leaves", PDBlocks.DYEDREAM_LEAVES);
    public static final DeferredItem<BlockItem> DYEDREAM_WORLDTREE_LEAVES = ITEMS.registerSimpleBlockItem("dyedream_worldtree_leaves", PDBlocks.DYEDREAM_WORLDTREE_LEAVES);
    public static final DeferredItem<BlockItem> DYEDREAMQUARTZ_ORE = ITEMS.registerSimpleBlockItem("dyedreamquartz_ore", PDBlocks.DYEDREAMQUARTZ_ORE);
    public static final DeferredItem<BlockItem> DYEDREAMDUST_ORE = ITEMS.registerSimpleBlockItem("dyedreamdust_ore", PDBlocks.DYEDREAMDUST_ORE);
    public static final DeferredItem<BlockItem> AMBER_CANDY_ORE = ITEMS.registerSimpleBlockItem("amber_candy_ore", PDBlocks.AMBER_CANDY_ORE);
    public static final DeferredItem<BlockItem> CARVE_DYEDREAM_GLASS = ITEMS.registerSimpleBlockItem("carve_dyedream_glass", PDBlocks.CARVE_DYEDREAM_GLASS);
    public static final DeferredItem<BlockItem> GOLD_CARVE_DYEDREAM_GLASS = ITEMS.registerSimpleBlockItem("gold_carve_dyedream_glass", PDBlocks.GOLD_CARVE_DYEDREAM_GLASS);
    public static final DeferredItem<BlockItem> DYEDREAM_GRASS = ITEMS.registerSimpleBlockItem("dyedream_grass", PDBlocks.DYEDREAM_GRASS);
    public static final DeferredItem<BlockItem> DYEDREAM_LOG = ITEMS.registerSimpleBlockItem("dyedream_log", PDBlocks.DYEDREAM_LOG);
    public static final DeferredItem<BlockItem> DYEDREAM_WOOD = ITEMS.registerSimpleBlockItem("dyedream_wood", PDBlocks.DYEDREAM_WOOD);
    public static final DeferredItem<BlockItem> PILLAR_DYEDREAMQUARTZ_BLOCK = ITEMS.registerSimpleBlockItem("pillar_dyedreamquartz_block", PDBlocks.PILLAR_DYEDREAMQUARTZ_BLOCK);
    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS_STAIRS = ITEMS.registerSimpleBlockItem("dyedream_planks_stairs", PDBlocks.DYEDREAM_PLANKS_STAIRS);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_STAIRS = ITEMS.registerSimpleBlockItem("dyedream_bud_stairs", PDBlocks.DYEDREAM_BUD_STAIRS);
    public static final DeferredItem<BlockItem> DYEDREAMQUARTZ_BLOCK_STAIRS = ITEMS.registerSimpleBlockItem("dyedreamquartz_block_stairs", PDBlocks.DYEDREAMQUARTZ_BLOCK_STAIRS);
    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS_SLAB = ITEMS.registerSimpleBlockItem("dyedream_planks_slab", PDBlocks.DYEDREAM_PLANKS_SLAB);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_SLAB = ITEMS.registerSimpleBlockItem("dyedream_bud_slab", PDBlocks.DYEDREAM_BUD_SLAB);
    public static final DeferredItem<BlockItem> DYEDREAMQUARTZ_BLOCK_SLAB = ITEMS.registerSimpleBlockItem("dyedreamquartz_block_slab", PDBlocks.DYEDREAMQUARTZ_BLOCK_SLAB);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_WALL = ITEMS.registerSimpleBlockItem("dyedream_bud_wall", PDBlocks.DYEDREAM_BUD_WALL);
    public static final DeferredItem<BlockItem> DYEDREAMQUARTZ_BLOCK_WALL = ITEMS.registerSimpleBlockItem("dyedreamquartz_block_wall", PDBlocks.DYEDREAMQUARTZ_BLOCK_WALL);
    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS_FENCE = ITEMS.registerSimpleBlockItem("dyedream_planks_fence", PDBlocks.DYEDREAM_PLANKS_FENCE);
    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS_FENCEGATE = ITEMS.registerSimpleBlockItem("dyedream_planks_fencegate", PDBlocks.DYEDREAM_PLANKS_FENCEGATE);
    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS_DOOR = ITEMS.registerSimpleBlockItem("dyedream_planks_door", PDBlocks.DYEDREAM_PLANKS_DOOR);
    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS_TRAPDOOR = ITEMS.registerSimpleBlockItem("dyedream_planks_trapdoor", PDBlocks.DYEDREAM_PLANKS_TRAPDOOR);
    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem("dyedream_planks_pressure_plate", PDBlocks.DYEDREAM_PLANKS_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS_BUTTON = ITEMS.registerSimpleBlockItem("dyedream_planks_button", PDBlocks.DYEDREAM_PLANKS_BUTTON);
    public static final DeferredItem<BlockItem> DYEDREAM_GLASSPANE = ITEMS.registerSimpleBlockItem("dyedream_glasspane", PDBlocks.DYEDREAM_GLASSPANE);
    public static final DeferredItem<BlockItem> CARVE_DYEDREAM_GLASSPANE = ITEMS.registerSimpleBlockItem("carve_dyedream_glasspane", PDBlocks.CARVE_DYEDREAM_GLASSPANE);
    public static final DeferredItem<BlockItem> GOLD_CARVE_DYEDREAM_GLASSPANE = ITEMS.registerSimpleBlockItem("gold_carve_dyedream_glasspane", PDBlocks.GOLD_CARVE_DYEDREAM_GLASSPANE);
    public static final DeferredItem<BlockItem> DYEDREAM_LARTERN = ITEMS.registerSimpleBlockItem("dyedream_lartern", PDBlocks.DYEDREAM_LARTERN);

    // ==================== 刷怪蛋 ====================

    /**
     * 暗影魔像刷怪蛋 (shadow_golem_spawn_egg)
     * 主色: 深灰色 (0x171717), 副色: 暗紫色 (0x7A7A7A)
     */
    public static final DeferredItem<SpawnEggItem> SHADOW_GOLEM_SPAWN_EGG = ITEMS.register("shadow_golem_spawn_egg",
            () -> new SpawnEggItem(PDEntities.SHADOW_GOLEM.get(), 0x171717, 0x7A7A7A, new Item.Properties()));

    /**
     * 粉色史莱姆刷怪蛋 (pink_slime_spawn_egg)
     * 主色: 粉色 (0xFFB6C1), 副色: 深粉色 (0xFF69B4)
     */
    public static final DeferredItem<SpawnEggItem> PINK_SLIME_SPAWN_EGG = ITEMS.register("pink_slime_spawn_egg",
            () -> new SpawnEggItem(PDEntities.PINK_SLIME.get(), 0xFFB6C1, 0xFF69B4, new Item.Properties()));

    // ==================== 测试材料物品 ====================

    /**
     * 钛锭 (titanium_ingot)
     * 基础材料，稀有度为 UNCOMMON
     */
    public static final DeferredItem<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new TitaniumIngotItem());

    /**
     * 染梦粉 (dyedream_dust)
     * 基础材料
     */
    public static final DeferredItem<Item> DYEDREAM_DUST = ITEMS.registerSimpleItem("dyedream_dust");

    /**
     * 魔法石 (magic_stone)
     * 基础材料，带有特殊描述文本
     */
    public static final DeferredItem<Item> MAGIC_STONE = ITEMS.register("magic_stone",
            () -> new MagicStoneItem());

    /**
     * 粉色粘液球 (pink_slimeball)
     */
    public static final DeferredItem<Item> PINK_SLIMEBALL = ITEMS.registerSimpleItem("pink_slimeball");

    /**
     * 染梦石英 (dyedreamquartz)
     */
    public static final DeferredItem<Item> DYEDREAMQUARTZ = ITEMS.registerSimpleItem("dyedreamquartz");

    // ==================== 批量移植的材料物品 ====================

    /**
     * 基础材料物品 - COMMON 稀有度
     */
    public static final DeferredItem<Item> BASALT_SNAIL_SHELL = ITEMS.registerSimpleItem("basalt_snail_shell");
    public static final DeferredItem<Item> BLACK_BEETLE_CARAPACE = ITEMS.registerSimpleItem("black_beetle_carapace");
    public static final DeferredItem<Item> BLACK_BEETLE_VOCALCORD = ITEMS.registerSimpleItem("black_beetle_vocalcord");
    public static final DeferredItem<Item> BLACKMETAL_GRAIN = ITEMS.registerSimpleItem("blackmetal_grain");
    public static final DeferredItem<Item> BLACKMETAL_INGOT = ITEMS.registerSimpleItem("blackmetal_ingot");
    public static final DeferredItem<Item> BLACKSTICK = ITEMS.registerSimpleItem("blackstick");
    public static final DeferredItem<Item> BLUE_HEART_OF_THE_SEA = ITEMS.registerSimpleItem("blue_heart_of_the_sea");
    public static final DeferredItem<Item> BROKENNOTES_0 = ITEMS.registerSimpleItem("brokennotes_0");
    public static final DeferredItem<Item> CHARGED_AMETHYST = ITEMS.registerSimpleItem("charged_amethyst");
    public static final DeferredItem<Item> COARSE_SALT = ITEMS.registerSimpleItem("coarse_salt");
    public static final DeferredItem<Item> CONGEAL_WIND = ITEMS.registerSimpleItem("congeal_wind");
    public static final DeferredItem<Item> COTTON = ITEMS.registerSimpleItem("cotton");
    public static final DeferredItem<Item> DREAM_AURORIAN_STEEL = ITEMS.registerSimpleItem("dream_aurorian_steel");
    public static final DeferredItem<Item> DREAM_METER = ITEMS.registerSimpleItem("dream_meter");
    public static final DeferredItem<Item> DREAMWISH = ITEMS.registerSimpleItem("dreamwish");
    public static final DeferredItem<Item> DYEDREAM_BASE = ITEMS.registerSimpleItem("dyedream_base");
    public static final DeferredItem<Item> DYEDREAM_BUD_NUGGET = ITEMS.registerSimpleItem("dyedream_bud_nugget");
    public static final DeferredItem<Item> DYEDREAM_COROLLA = ITEMS.registerSimpleItem("dyedream_corolla");
    public static final DeferredItem<Item> DYEDREAM_DUST_PIECE = ITEMS.registerSimpleItem("dyedream_dust_piece");
    public static final DeferredItem<Item> DYEDREAM_DYE = ITEMS.registerSimpleItem("dyedream_dye");
    public static final DeferredItem<Item> DYEDREAM_NUGGET = ITEMS.registerSimpleItem("dyedream_nugget");
    public static final DeferredItem<Item> DYEDREAM_UPGRADE = ITEMS.registerSimpleItem("dyedream_upgrade", new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> EGGDOUGH = ITEMS.registerSimpleItem("eggdough");
    public static final DeferredItem<Item> ELDER_GUARDIAN_SCALE = ITEMS.registerSimpleItem("elder_guardian_scale");
    public static final DeferredItem<Item> ENHANCE_STONE_0 = ITEMS.registerSimpleItem("enhance_stone_0");
    public static final DeferredItem<Item> ENHANCE_STONE_1 = ITEMS.registerSimpleItem("enhance_stone_1");
    public static final DeferredItem<Item> FABRIC = ITEMS.registerSimpleItem("fabric");
    public static final DeferredItem<Item> FLOUR = ITEMS.registerSimpleItem("flour");
    public static final DeferredItem<Item> ICESHADOW_HAMMER_EMBRYO = ITEMS.registerSimpleItem("iceshadow_hammer_embryo");
    public static final DeferredItem<Item> MANADUST = ITEMS.registerSimpleItem("manadust");
    public static final DeferredItem<Item> MOLTENGOLD_DUST = ITEMS.registerSimpleItem("moltengold_dust");
    public static final DeferredItem<Item> MOLTENGOLD_INGOT = ITEMS.registerSimpleItem("moltengold_ingot");
    public static final DeferredItem<Item> MOLTENGOLD_NUGGET = ITEMS.registerSimpleItem("moltengold_nugget");
    public static final DeferredItem<Item> MORTAR = ITEMS.registerSimpleItem("mortar");
    public static final DeferredItem<Item> NIGHTMARE_FUEL = ITEMS.registerSimpleItem("nightmare_fuel");
    public static final DeferredItem<Item> PEN_AND_INK = ITEMS.registerSimpleItem("pen_and_ink");
    public static final DeferredItem<Item> PERGAMYN = ITEMS.registerSimpleItem("pergamyn");
    public static final DeferredItem<Item> PROTECT_DECK = ITEMS.registerSimpleItem("protect_deck");
    public static final DeferredItem<Item> PULSE_WINDRUNNER_CRYSTAL = ITEMS.registerSimpleItem("pulse_windrunner_crystal");
    public static final DeferredItem<Item> PURE_HORROR = ITEMS.registerSimpleItem("pure_horror");
    public static final DeferredItem<Item> RAW_MOLTENGOLD = ITEMS.registerSimpleItem("raw_moltengold");
    public static final DeferredItem<Item> RAW_TITANIUM = ITEMS.registerSimpleItem("raw_titanium");
    public static final DeferredItem<Item> REEDROD = ITEMS.registerSimpleItem("reedrod");
    public static final DeferredItem<Item> RUST_BLACK_METAL_GRAIN = ITEMS.registerSimpleItem("rust_black_metal_grain");
    public static final DeferredItem<Item> RYESEED = ITEMS.registerSimpleItem("ryeseed");
    public static final DeferredItem<Item> SALT = ITEMS.registerSimpleItem("salt");
    public static final DeferredItem<Item> SCULK_HEART = ITEMS.registerSimpleItem("sculk_heart");
    public static final DeferredItem<Item> SCULK_UPGRADE = ITEMS.registerSimpleItem("sculk_upgrade");
    public static final DeferredItem<Item> SHADOW_DUNGEON_KEY = ITEMS.registerSimpleItem("shadow_dungeon_key");
    public static final DeferredItem<Item> SHADOW_EROSION_AXE_EMBRYO = ITEMS.registerSimpleItem("shadow_erosion_axe_embryo");
    public static final DeferredItem<Item> SHADOW_EROSION_HOE_EMBRYO = ITEMS.registerSimpleItem("shadow_erosion_hoe_embryo");
    public static final DeferredItem<Item> SHADOW_EROSION_PICKAXE_EMBRYO = ITEMS.registerSimpleItem("shadow_erosion_pickaxe_embryo");
    public static final DeferredItem<Item> SHADOW_EROSION_SHOVEL_EMBRYO = ITEMS.registerSimpleItem("shadow_erosion_shovel_embryo");
    public static final DeferredItem<Item> SHADOW_EROSION_SWORD_EMBRYO = ITEMS.registerSimpleItem("shadow_erosion_sword_embryo");
    public static final DeferredItem<Item> SHADOW_HILT = ITEMS.registerSimpleItem("shadow_hilt");
    public static final DeferredItem<Item> SHADOW_SWORD_EMBRYO = ITEMS.registerSimpleItem("shadow_sword_embryo");
    public static final DeferredItem<Item> SILVER_BELL = ITEMS.registerSimpleItem("silver_bell");
    public static final DeferredItem<Item> SORBENT = ITEMS.registerSimpleItem("sorbent");
    public static final DeferredItem<Item> SOUL_DUST = ITEMS.registerSimpleItem("soul_dust");
    public static final DeferredItem<Item> SOUL_ESSENCE = ITEMS.registerSimpleItem("soul_essence");
    public static final DeferredItem<Item> SPOOL = ITEMS.registerSimpleItem("spool");
    public static final DeferredItem<Item> STAR_WISH_ROD_EMBRYO = ITEMS.registerSimpleItem("star_wish_rod_embryo");
    public static final DeferredItem<Item> SWORD_EMBRYO_0 = ITEMS.registerSimpleItem("sword_embryo_0");
    public static final DeferredItem<Item> TERRASWORD_EMBRYO = ITEMS.registerSimpleItem("terrasword_embryo");
    public static final DeferredItem<Item> TITANIUM_NUGGET = ITEMS.registerSimpleItem("titanium_nugget");
    public static final DeferredItem<Item> TITANIUM_UPGRADE = ITEMS.registerSimpleItem("titanium_upgrade");
    public static final DeferredItem<Item> UNKNOWNNOTES_0 = ITEMS.registerSimpleItem("unknownnotes_0");
    public static final DeferredItem<Item> WHITE_COROLLA = ITEMS.registerSimpleItem("white_corolla");
    public static final DeferredItem<Item> WHITE_CRYSTAL = ITEMS.registerSimpleItem("white_crystal");
    public static final DeferredItem<Item> WHITE_SWORD_EMBRYO = ITEMS.registerSimpleItem("white_sword_embryo");
    public static final DeferredItem<Item> WIND_IRON_INGOT = ITEMS.registerSimpleItem("wind_iron_ingot");
    public static final DeferredItem<Item> WIND_PLANT_EXTRACT = ITEMS.registerSimpleItem("wind_plant_extract");
    public static final DeferredItem<Item> WINDRUNNER_CRYSTAL = ITEMS.registerSimpleItem("windrunner_crystal");
    public static final DeferredItem<Item> YEAST = ITEMS.registerSimpleItem("yeast");

    /**
     * 基础材料物品 - UNCOMMON 稀有度
     */
    public static final DeferredItem<Item> DYEDREAM_INGOT = ITEMS.registerSimpleItem("dyedream_ingot", new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
    // ==================== 剑类武器 ====================

    public static final DeferredItem<BrokenHeroSwordItem> BROKEN_HERO_SWORD = ITEMS.register("broken_hero_sword",
            () -> new BrokenHeroSwordItem());
    public static final DeferredItem<CopperSwordItem> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new CopperSwordItem());
    public static final DeferredItem<CreativeSwordItem> CREATIVE_SWORD = ITEMS.register("creative_sword",
            () -> new CreativeSwordItem());
    public static final DeferredItem<DesertSwordItem> DESERT_SWORD = ITEMS.register("desert_sword",
            () -> new DesertSwordItem());
    public static final DeferredItem<DyedreamSword0Item> DYEDREAM_SWORD_0 = ITEMS.register("dyedream_sword_0",
            () -> new DyedreamSword0Item());
    public static final DeferredItem<DyedreamSwordItem> DYEDREAM_SWORD = ITEMS.register("dyedream_sword",
            () -> new DyedreamSwordItem());
    public static final DeferredItem<GrassSwordItem> GRASS_SWORD = ITEMS.register("grass_sword",
            () -> new GrassSwordItem());
    public static final DeferredItem<IceshadowHammerItem> ICESHADOW_HAMMER = ITEMS.register("iceshadow_hammer",
            () -> new IceshadowHammerItem());
    public static final DeferredItem<MoltengoldSwordItem> MOLTENGOLD_SWORD = ITEMS.register("moltengold_sword",
            () -> new MoltengoldSwordItem());
    public static final DeferredItem<ShadowErosionSwordItem> SHADOW_EROSION_SWORD = ITEMS.register("shadow_erosion_sword",
            () -> new ShadowErosionSwordItem());
    public static final DeferredItem<ShadowSwordItem> SHADOW_SWORD = ITEMS.register("shadow_sword",
            () -> new ShadowSwordItem());
    public static final DeferredItem<TerraSwordItem> TERRA_SWORD = ITEMS.register("terra_sword",
            () -> new TerraSwordItem());
    public static final DeferredItem<ThermalDaggerItem> THERMAL_DAGGER = ITEMS.register("thermal_dagger",
            () -> new ThermalDaggerItem());
    public static final DeferredItem<TideSwordItem> TIDE_SWORD = ITEMS.register("tide_sword",
            () -> new TideSwordItem());
    public static final DeferredItem<TitaniumSwordItem> TITANIUM_SWORD = ITEMS.register("titanium_sword",
            () -> new TitaniumSwordItem());
    public static final DeferredItem<TrueDesertSwordItem> TRUE_DESERT_SWORD = ITEMS.register("true_desert_sword",
            () -> new TrueDesertSwordItem());
    public static final DeferredItem<TrueGrassSwordItem> TRUE_GRASS_SWORD = ITEMS.register("true_grass_sword",
            () -> new TrueGrassSwordItem());
    public static final DeferredItem<TrueMoltengoldSwordItem> TRUE_MOLTENGOLD_SWORD = ITEMS.register("true_moltengold_sword",
            () -> new TrueMoltengoldSwordItem());
    public static final DeferredItem<TrueTideSwordItem> TRUE_TIDE_SWORD = ITEMS.register("true_tide_sword",
            () -> new TrueTideSwordItem());
    public static final DeferredItem<TruestMoltengoldSwordItem> TRUEST_MOLTENGOLD_SWORD = ITEMS.register("truest_moltengold_sword",
            () -> new TruestMoltengoldSwordItem());
    public static final DeferredItem<WhiteSwordItem> WHITE_SWORD = ITEMS.register("white_sword",
            () -> new WhiteSwordItem());

    // ==================== 镐类/锤类工具 ====================

    public static final DeferredItem<CopperPickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new CopperPickaxeItem());
    public static final DeferredItem<DyedreamHammerItem> DYEDREAM_HAMMER = ITEMS.register("dyedream_hammer",
            () -> new DyedreamHammerItem());
    public static final DeferredItem<DyedreamPickaxeItem> DYEDREAM_PICKAXE = ITEMS.register("dyedream_pickaxe",
            () -> new DyedreamPickaxeItem());
    public static final DeferredItem<MeltdreamPickaxeItem> MELTDREAM_PICKAXE = ITEMS.register("meltdream_pickaxe",
            () -> new MeltdreamPickaxeItem());
    public static final DeferredItem<MoltengoldPickaxeItem> MOLTENGOLD_PICKAXE = ITEMS.register("moltengold_pickaxe",
            () -> new MoltengoldPickaxeItem());
    public static final DeferredItem<ShadowErosionPickaxeItem> SHADOW_EROSION_PICKAXE = ITEMS.register("shadow_erosion_pickaxe",
            () -> new ShadowErosionPickaxeItem());
    public static final DeferredItem<TitaniumPickaxeItem> TITANIUM_PICKAXE = ITEMS.register("titanium_pickaxe",
            () -> new TitaniumPickaxeItem());
    public static final DeferredItem<TrueMoltengoldPickaxeItem> TRUE_MOLTENGOLD_PICKAXE = ITEMS.register("true_moltengold_pickaxe",
            () -> new TrueMoltengoldPickaxeItem());

    // ==================== 食物类物品 ====================

    public static final DeferredItem<Item> APPLE_JUICE = ITEMS.registerSimpleItem("apple_juice",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.2f).alwaysEdible().build()));
    public static final DeferredItem<Item> BACONE_EGG = ITEMS.registerSimpleItem("bacone_egg",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(1.2f).build()));
    public static final DeferredItem<Item> BERRY_BUNCAKE = ITEMS.registerSimpleItem("berry_buncake",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).alwaysEdible().fast().build()));
    public static final DeferredItem<Item> BUBBLE_GUM = ITEMS.registerSimpleItem("bubble_gum",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0f).alwaysEdible().fast().build()));
    public static final DeferredItem<Item> CANDY_CANE = ITEMS.registerSimpleItem("candy_cane",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> CHOCOLATE = ITEMS.registerSimpleItem("chocolate",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.375f).build()));
    public static final DeferredItem<Item> CHOCOLATE_MATCHA_CAKE = ITEMS.registerSimpleItem("chocolate_matcha_cake",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.0f).build()));
    public static final DeferredItem<Item> CREAM_BUNCAKE = ITEMS.registerSimpleItem("cream_buncake",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).alwaysEdible().fast().build()));
    public static final DeferredItem<Item> DREAM_COTTON_CANDY = ITEMS.registerSimpleItem("dream_cotton_candy",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.625f).alwaysEdible().build()));
    public static final DeferredItem<Item> DYEDREAM_FLOWER_TEA = ITEMS.registerSimpleItem("dyedream_flower_tea",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build()));
    public static final DeferredItem<Item> DYEDREAM_FRUIT_BUNCAKE = ITEMS.registerSimpleItem("dyedream_fruit_buncake",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).alwaysEdible().fast().build()));
    public static final DeferredItem<Item> DYEDREAM_JUICE = ITEMS.registerSimpleItem("dyedream_juice",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.2f).alwaysEdible().build()));
    public static final DeferredItem<Item> DYEDREAM_POPSICLE = ITEMS.registerSimpleItem("dyedream_popsicle",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> FRIED_EGG = ITEMS.registerSimpleItem("fried_egg",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build()));
    public static final DeferredItem<Item> GINGERBREAD_MAN = ITEMS.registerSimpleItem("gingerbread_man",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build()));
    public static final DeferredItem<Item> GLOW_BERRY_BUNCAKE = ITEMS.registerSimpleItem("glow_berry_buncake",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).alwaysEdible().fast().build()));
    public static final DeferredItem<Item> GOLDENROD_TEA = ITEMS.registerSimpleItem("goldenrod_tea",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0f).alwaysEdible().build()));
    public static final DeferredItem<Item> HONEY_JUICE = ITEMS.registerSimpleItem("honey_juice",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).alwaysEdible().build()));
    public static final DeferredItem<Item> JELLYFISH_JELLO = ITEMS.registerSimpleItem("jellyfish_jello",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).alwaysEdible().build()));
    public static final DeferredItem<Item> JELLYFISH_MUD = ITEMS.registerSimpleItem("jellyfish_mud",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0f).build()));
    public static final DeferredItem<Item> LEGEND_DRAGON_HORN_ICE_CREAM = ITEMS.registerSimpleItem("legend_dragon_horn_ice_cream",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(1.2f).alwaysEdible().build()));
    public static final DeferredItem<Item> LIGHT_ORGAN = ITEMS.registerSimpleItem("light_organ",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0f).build()));
    public static final DeferredItem<Item> MELON_BUNCAKE = ITEMS.registerSimpleItem("melon_buncake",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).alwaysEdible().fast().build()));
    public static final DeferredItem<Item> MELTDREAM_ELIXIR_BOTTLE = ITEMS.registerSimpleItem("meltdream_elixir_bottle",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.25f).alwaysEdible().build()));
    public static final DeferredItem<Item> MILK_GLASSJAR = ITEMS.registerSimpleItem("milk_glassjar",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build()));
    public static final DeferredItem<Item> ODD_BACONE_EGG = ITEMS.registerSimpleItem("odd_bacone_egg",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(11).saturationModifier(1.5f).build()));
    public static final DeferredItem<Item> PINEAPPLE_LOVE_SEA = ITEMS.registerSimpleItem("pineapple_love_sea",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> POTATO_BUNCAKE = ITEMS.registerSimpleItem("potato_buncake",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).alwaysEdible().fast().build()));
    public static final DeferredItem<Item> PUMPKIN_BUNCAKE = ITEMS.registerSimpleItem("pumpkin_buncake",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).alwaysEdible().fast().build()));
    public static final DeferredItem<Item> QUEER_SOUP = ITEMS.registerSimpleItem("queer_soup",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build()));
    public static final DeferredItem<Item> RAGE_ELIXIR_0 = ITEMS.registerSimpleItem("rage_elixir_0",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build()));
    public static final DeferredItem<Item> RICECAKE = ITEMS.registerSimpleItem("ricecake",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.4f).build()));
    public static final DeferredItem<Item> SANDWICH = ITEMS.registerSimpleItem("sandwich",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.9f).build()));
    public static final DeferredItem<Item> STUFFED_WAFER_COOKIES = ITEMS.registerSimpleItem("stuffed_wafer_cookies",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationModifier(1.0f).build()));
    public static final DeferredItem<Item> SWISS_ROLL = ITEMS.registerSimpleItem("swiss_roll",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8f).build()));
    public static final DeferredItem<Item> UNCOOKED_DYEDREAM_FLOWER_TEA = ITEMS.registerSimpleItem("uncooked_dyedream_flower_tea",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build()));
    public static final DeferredItem<Item> WATER_GLASSJAR = ITEMS.registerSimpleItem("water_glassjar",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build()));
    public static final DeferredItem<Item> WATERMELON_JUICE = ITEMS.registerSimpleItem("watermelon_juice",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).alwaysEdible().build()));
    public static final DeferredItem<Item> YINHUL_COTTON_CANDY = ITEMS.registerSimpleItem("yinhul_cotton_candy",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.75f).alwaysEdible().build()));

    // ==================== 需要自定义类的物品（tooltip/交互） ====================

    public static final DeferredItem<Item> AMBER_CANDY = ITEMS.register("amber_candy",
            () -> new AmberCandyItem(new Item.Properties()));
    public static final DeferredItem<Item> BLUE_DEW = ITEMS.register("blue_dew",
            () -> new BlueDewItem(new Item.Properties()));
    public static final DeferredItem<Item> BREAD_SLICE = ITEMS.register("bread_slice",
            () -> new BreadSliceItem(new Item.Properties()));
    public static final DeferredItem<Item> BUBBLE_TEA = ITEMS.register("bubble_tea",
            () -> new BubbleTeaItem(new Item.Properties()));
    public static final DeferredItem<Item> CAKE_BASE = ITEMS.register("cake_base",
            () -> new CakeBaseItem(new Item.Properties()));
    public static final DeferredItem<Item> CRADLE_IN_ONES_ARMS = ITEMS.register("cradle_in_ones_arms",
            () -> new CradleInOnesArmsItem(new Item.Properties()));
    public static final DeferredItem<Item> DREAM_COIN_0 = ITEMS.register("dream_coin_0",
            () -> new DreamCoin0Item(new Item.Properties()));
    public static final DeferredItem<Item> DREAM_COIN_1 = ITEMS.register("dream_coin_1",
            () -> new DreamCoin1Item(new Item.Properties()));
    public static final DeferredItem<DreamFertilizerItem> DREAM_FERTILIZER = ITEMS.register("dream_fertilizer",
            () -> new DreamFertilizerItem(new Item.Properties(), PDParticles.DREAMFERTILITER_PARTICLE));
    public static final DeferredItem<Item> DYEDREAM_FRUIT = ITEMS.register("dyedream_fruit",
            () -> new DyedreamFruitItem(new Item.Properties()));
    public static final DeferredItem<Item> DYEDREAM_TELEPORT_CRYSTAL = ITEMS.register("dyedream_teleport_crystal",
            () -> new DyedreamTeleportCrystal(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> DYEDREAM_PERFUME = ITEMS.register("dyedream_perfume",
            () -> new DyedreamPerfumeItem(new Item.Properties()));
    public static final DeferredItem<Item> ELIXIR_BOTTLE = ITEMS.register("elixir_bottle",
            () -> new ElixirBottleItem(new Item.Properties()));
    public static final DeferredItem<Item> FIG = ITEMS.register("fig",
            () -> new FigItem(new Item.Properties()));
    public static final DeferredItem<Item> GLASSJAR = ITEMS.register("glassjar",
            () -> new GlassjarItem(new Item.Properties()));
    public static final DeferredItem<Item> GUIDING_DRUG = ITEMS.register("guiding_drug",
            () -> new GuidingDrugItem(new Item.Properties()));
    public static final DeferredItem<Item> HEART_CHOCOLATE_0 = ITEMS.register("heart_chocolate_0",
            () -> new HeartChocolate0Item(new Item.Properties()));
    public static final DeferredItem<Item> HEART_CHOCOLATE_1 = ITEMS.register("heart_chocolate_1",
            () -> new HeartChocolate1Item(new Item.Properties()));
    public static final DeferredItem<Item> HEART_CHOCOLATE_2 = ITEMS.register("heart_chocolate_2",
            () -> new HeartChocolate2Item(new Item.Properties()));
    public static final DeferredItem<Item> LIGHT_MOSS_PHANTOM_MEMBRANE = ITEMS.register("light_moss_phantom_membrane",
            () -> new LightMossPhantomMembraneItem(new Item.Properties()));
    public static final DeferredItem<Item> MELTDREAM_CRYSTAL_0 = ITEMS.register("meltdream_crystal_0",
            () -> new MeltdreamCrystal0Item(new Item.Properties()));
    public static final DeferredItem<Item> MEMENTO_ITEM_01 = ITEMS.register("memento_item_01",
            () -> new MementoItem01Item(new Item.Properties()));
    public static final DeferredItem<Item> MEMORY_GEM_0 = ITEMS.register("memory_gem_0",
            () -> new MemoryGem0Item(new Item.Properties()));
    public static final DeferredItem<Item> MOSS_PHANTOM_MEMBRANE = ITEMS.register("moss_phantom_membrane",
            () -> new MossPhantomMembraneItem(new Item.Properties()));
    public static final DeferredItem<Item> POPPING_CANDY = ITEMS.register("popping_candy",
            () -> new PoppingCandyItem(new Item.Properties()));
    public static final DeferredItem<Item> RED_DEW_0 = ITEMS.register("red_dew_0",
            () -> new RedDew0Item(new Item.Properties()));
    public static final DeferredItem<Item> SHADOW_BREATH = ITEMS.register("shadow_breath",
            () -> new ShadowBreathItem(new Item.Properties()));
    public static final DeferredItem<Item> SQUEAL_WAVE = ITEMS.register("squeal_wave",
            () -> new SquealWaveItem(new Item.Properties()));
    public static final DeferredItem<Item> STRAWBERRY_HEART = ITEMS.register("strawberry_heart",
            () -> new StrawberryHeartItem(new Item.Properties()));
    public static final DeferredItem<Item> WAFER_BISCUIT = ITEMS.register("wafer_biscuit",
            () -> new WaferBiscuitItem(new Item.Properties()));

    // ==================== Curio饰品/特殊物品 ====================

    public static final DeferredItem<Item> ANGEL_WING = ITEMS.registerSimpleItem("angel_wing");
    public static final DeferredItem<CalaisSpiceBottleCurioItem> CALAIS_SPICE_BOTTLE_CURIO = ITEMS.register("calais_spice_bottle_curio",
            () -> new CalaisSpiceBottleCurioItem());
    public static final DeferredItem<CarapaxCharmItem> CARAPAX_CHARM = ITEMS.register("carapax_charm",
            () -> new CarapaxCharmItem());
    public static final DeferredItem<CeciliacareCharmItem> CECILIACARE_CHARM = ITEMS.register("ceciliacare_charm",
            () -> new CeciliacareCharmItem());
    public static final DeferredItem<CounterRingItem> COUNTER_RING = ITEMS.register("counter_ring",
            () -> new CounterRingItem());
    public static final DeferredItem<DarkAlllegoryCurioItem> DARK_ALLLEGORY_CURIO = ITEMS.register("dark_alllegory_curio",
            () -> new DarkAlllegoryCurioItem());
    public static final DeferredItem<EmbryoBeltItem> EMBRYO_BELT = ITEMS.register("embryo_belt",
            () -> new EmbryoBeltItem());
    public static final DeferredItem<EmbryoCharmItem> EMBRYO_CHARM = ITEMS.register("embryo_charm",
            () -> new EmbryoCharmItem());
    public static final DeferredItem<EmbryoNecklaceItem> EMBRYO_NECKLACE = ITEMS.register("embryo_necklace",
            () -> new EmbryoNecklaceItem());
    public static final DeferredItem<EmbryoRingItem> EMBRYO_RING = ITEMS.register("embryo_ring",
            () -> new EmbryoRingItem());
    public static final DeferredItem<Item> FORSAKENS_WING = ITEMS.registerSimpleItem("forsakens_wing");
    public static final DeferredItem<GhostFaceHeadItem> GHOST_FACE_HEAD = ITEMS.register("ghost_face_head",
            () -> new GhostFaceHeadItem());
    public static final DeferredItem<Item> GROUND_WING = ITEMS.registerSimpleItem("ground_wing");
    public static final DeferredItem<Hithard0RingItem> HITHARD_0_RING = ITEMS.register("hithard_0_ring",
            () -> new Hithard0RingItem());
    public static final DeferredItem<Hithard1RingItem> HITHARD_1_RING = ITEMS.register("hithard_1_ring",
            () -> new Hithard1RingItem());
    public static final DeferredItem<Item> MACHINE_WING = ITEMS.registerSimpleItem("machine_wing");
    public static final DeferredItem<MeltdreamEnergy0RingItem> MELTDREAM_ENERGY_0_RING = ITEMS.register("meltdream_energy_0_ring",
            () -> new MeltdreamEnergy0RingItem());
    public static final DeferredItem<Item> PALE_BONENEEDLE = ITEMS.registerSimpleItem("pale_boneneedle");
    public static final DeferredItem<RedDew0RingItem> RED_DEW_0_RING = ITEMS.register("red_dew_0_ring",
            () -> new RedDew0RingItem());
    public static final DeferredItem<RedDew1RingItem> RED_DEW_1_RING = ITEMS.register("red_dew_1_ring",
            () -> new RedDew1RingItem());
    public static final DeferredItem<RedDew2RingItem> RED_DEW_2_RING = ITEMS.register("red_dew_2_ring",
            () -> new RedDew2RingItem());
    public static final DeferredItem<RedDew3RingItem> RED_DEW_3_RING = ITEMS.register("red_dew_3_ring",
            () -> new RedDew3RingItem());
    public static final DeferredItem<SeaCharmItem> SEA_CHARM = ITEMS.register("sea_charm",
            () -> new SeaCharmItem());
    public static final DeferredItem<WindKnightFlagItem> WIND_KNIGHT_FLAG = ITEMS.register("wind_knight_flag",
            () -> new WindKnightFlagItem());
    public static final DeferredItem<Item> WINGS_OF_FANG = ITEMS.registerSimpleItem("wings_of_fang");

    // ==================== 自定义模型方块 BlockItem ====================

    public static final DeferredItem<BlockItem> DYEDREAM_PLANKS_PANE = ITEMS.registerSimpleBlockItem("dyedream_planks_pane", PDBlocks.DYEDREAM_PLANKS_PANE);
    public static final DeferredItem<BlockItem> PINKAGARIC_0 = ITEMS.registerSimpleBlockItem("pinkagaric_0", PDBlocks.PINKAGARIC_0);
    public static final DeferredItem<BlockItem> PINKAGARIC_1 = ITEMS.registerSimpleBlockItem("pinkagaric_1", PDBlocks.PINKAGARIC_1);
    public static final DeferredItem<BlockItem> PINKAGARIC_2 = ITEMS.registerSimpleBlockItem("pinkagaric_2", PDBlocks.PINKAGARIC_2);
    public static final DeferredItem<BlockItem> PINKAGARIC_3 = ITEMS.registerSimpleBlockItem("pinkagaric_3", PDBlocks.PINKAGARIC_3);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_0 = ITEMS.registerSimpleBlockItem("dyedream_bud_0", PDBlocks.DYEDREAM_BUD_0);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_1 = ITEMS.registerSimpleBlockItem("dyedream_bud_1", PDBlocks.DYEDREAM_BUD_1);
    public static final DeferredItem<BlockItem> DYEDREAM_BUD_2 = ITEMS.registerSimpleBlockItem("dyedream_bud_2", PDBlocks.DYEDREAM_BUD_2);
    public static final DeferredItem<BlockItem> ICE_BUD_0 = ITEMS.registerSimpleBlockItem("ice_bud_0", PDBlocks.ICE_BUD_0);
    public static final DeferredItem<BlockItem> DYEDREAM_LILY_PAD = ITEMS.registerSimpleBlockItem("dyedream_lily_pad", PDBlocks.DYEDREAM_LILY_PAD);
    public static final DeferredItem<BlockItem> DYEDREAM_LOTUS = ITEMS.registerSimpleBlockItem("dyedream_lotus", PDBlocks.DYEDREAM_LOTUS);
    public static final DeferredItem<BlockItem> DYEDREAM_SEAGRASS = ITEMS.registerSimpleBlockItem("dyedream_seagrass", PDBlocks.DYEDREAM_SEAGRASS);
    public static final DeferredItem<BlockItem> DYEDREAM_SAPLING = ITEMS.registerSimpleBlockItem("dyedream_sapling", PDBlocks.DYEDREAM_SAPLING);
    public static final DeferredItem<BlockItem> DYEDREAM_CRACK = ITEMS.registerSimpleBlockItem("dyedream_crack", PDBlocks.DYEDREAM_CRACK);
}
