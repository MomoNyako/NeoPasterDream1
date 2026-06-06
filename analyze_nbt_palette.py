#!/usr/bin/env python3
"""精准分析NBT结构文件的方块调色板，找出真正未注册的方块"""
import nbtlib
import os
import glob

# libs 目录下的结构文件路径（旧版模组的完整结构集）
LIBS_STRUCTURE_DIR = r"libs/FixPasterDream-main/src/main/resources/data/pasterdream/structures"
# 主模组结构文件路径
MAIN_STRUCTURE_DIR = r"src/main/resources/data/pasterdream/structures"


def read_nbt_palette(filepath):
    """读取NBT结构文件的方块调色板"""
    try:
        nbt = nbtlib.load(filepath)
        palette = nbt.get("palette", [])
        blocks_data = nbt.get("blocks", [])

        # 提取调色板中的方块名称
        block_names = set()
        for entry in palette:
            name = str(entry.get("Name", ""))
            if name.startswith("pasterdream:"):
                block_names.add(name)

        # 提取方块实体中的物品ID（这些是物品，不是方块）
        item_ids = set()
        for block in blocks_data:
            block_nbt = block.get("nbt", {})
            if block_nbt:
                # 检查物品实体（Item）
                item_id = block_nbt.get("Item", None)
                if item_id:
                    item_ids.add(str(item_id))
                # 检查箱子中的物品
                items = block_nbt.get("Items", [])
                for item in items:
                    item_id = item.get("id", "")
                    if item_id and str(item_id).startswith("pasterdream:"):
                        item_ids.add(str(item_id))

        return block_names, item_ids
    except Exception as e:
        print(f"  [ERROR] 读取 {os.path.basename(filepath)} 失败: {e}")
        return set(), set()


def compare_structure_files():
    """对比 libs 和主模组之间的结构文件"""
    libs_files = set()
    if os.path.isdir(LIBS_STRUCTURE_DIR):
        libs_files = {os.path.basename(f) for f in glob.glob(os.path.join(LIBS_STRUCTURE_DIR, "*.nbt"))}

    main_files = set()
    if os.path.isdir(MAIN_STRUCTURE_DIR):
        main_files = {os.path.basename(f) for f in glob.glob(os.path.join(MAIN_STRUCTURE_DIR, "*.nbt"))}

    missing_in_main = sorted(libs_files - main_files)
    extra_in_main = sorted(main_files - libs_files)

    print(f"{'='*60}")
    print(f"结构文件对比：libs vs 主模组")
    print(f"{'='*60}")
    print(f"libs 结构文件总数: {len(libs_files)}")
    print(f"主模组结构文件总数: {len(main_files)}")

    if missing_in_main:
        print(f"\n🔄 libs 中有但主模组中缺失的结构文件 ({len(missing_in_main)} 个):")
        # 只显示前30个，避免刷屏
        for f in missing_in_main[:30]:
            print(f"   ➕ {f}")
        if len(missing_in_main) > 30:
            print(f"   ... 还有 {len(missing_in_main)-30} 个")

    return missing_in_main


def main():
    print("=" * 60)
    print("🧩 NBT结构文件方块调色板分析 (扫描 libs 目录)")
    print("=" * 60)

    # 先对比结构文件
    missing_structs = compare_structure_files()

    if not os.path.isdir(LIBS_STRUCTURE_DIR):
        print(f"\n[ERROR] libs 结构目录不存在: {LIBS_STRUCTURE_DIR}")
        return

    # 获取 libs 中所有 .nbt 文件
    target_files = sorted(glob.glob(os.path.join(LIBS_STRUCTURE_DIR, "*.nbt")))
    print(f"\n{'='*60}")
    print(f"📋 开始分析 {len(target_files)} 个结构文件的调色板...")
    print(f"{'='*60}")

    all_palette_blocks = set()
    all_item_ids = set()

    for filepath in target_files:
        filename = os.path.basename(filepath)
        palette_blocks, item_ids = read_nbt_palette(filepath)
        all_palette_blocks.update(palette_blocks)
        all_item_ids.update(item_ids)

    print(f"\n{'='*60}")
    print(f"📊 汇总统计")
    print(f"{'='*60}")
    print(f"总分析文件数: {len(target_files)}")
    print(f"所有结构文件中出现的独特 pasterdream 方块调色板引用: {len(all_palette_blocks)}")
    print(f"物品引用种类: {len(all_item_ids)}")
    print("=" * 60)

    # ===== 当前已注册的方块完整列表 =====
    registered_blocks = {
        "pasterdream:dream_accumulator", "pasterdream:dyedream_desk",
        "pasterdream:dream_train_structure", "pasterdream:life_crystal",
        "pasterdream:shadow_chest", "pasterdream:the_endless_book_of_dream_seekers",
        "pasterdream:dream_cauldron", "pasterdream:meltdream_chest",
        "pasterdream:meltdream_chest_open", "pasterdream:meltdream_liquid",

        "pasterdream:dyedream_dirt", "pasterdream:dyedream_sand",
        "pasterdream:dyedream_planks", "pasterdream:dyedream_glass",
        "pasterdream:dyedream_ice", "pasterdream:dyedream_packed_ice",
        "pasterdream:pinkslime_block", "pasterdream:dyedream_block",
        "pasterdream:dyedreamquartz_block", "pasterdream:smooth_dyedreamquartz_block",
        "pasterdream:bricks_dyedreamquartz_block", "pasterdream:chiseled_dyedreamquartz_block",
        "pasterdream:dyedream_bud_block", "pasterdream:icestone",
        "pasterdream:dyedream_worldtree_leaves", "pasterdream:dyedreamquartz_ore",
        "pasterdream:dyedreamdust_ore", "pasterdream:amber_candy_ore",
        "pasterdream:titanium_ore", "pasterdream:windrunner_crystal_ore",
        "pasterdream:congeal_wind_ore", "pasterdream:carve_dyedream_glass",
        "pasterdream:gold_carve_dyedream_glass", "pasterdream:polished_calcite",
        "pasterdream:calcite_tiles",

        "pasterdream:dyedream_leaves", "pasterdream:dyedream_grass",
        "pasterdream:dyedream_log", "pasterdream:dyedream_wood",
        "pasterdream:stripped_dyedream_log", "pasterdream:stripped_dyedream_wood",
        "pasterdream:pillar_dyedreamquartz_block",

        "pasterdream:dyedream_planks_stairs", "pasterdream:dyedream_planks_slab",
        "pasterdream:dyedream_planks_fence", "pasterdream:dyedream_planks_fencegate",
        "pasterdream:dyedream_planks_door", "pasterdream:dyedream_planks_trapdoor",
        "pasterdream:dyedream_planks_pressure_plate", "pasterdream:dyedream_planks_button",

        "pasterdream:dyedream_bud_stairs", "pasterdream:dyedream_bud_slab",
        "pasterdream:dyedream_bud_wall", "pasterdream:dyedreamquartz_block_stairs",
        "pasterdream:dyedreamquartz_block_slab", "pasterdream:dyedreamquartz_block_wall",

        "pasterdream:calcite_tiles_stairs", "pasterdream:calcite_tiles_slab",
        "pasterdream:polished_calcite_slab", "pasterdream:polished_calcite_wall",
        "pasterdream:calcite_tiles_wall", "pasterdream:polished_calcite_stairs",

        "pasterdream:dyedream_glasspane", "pasterdream:carve_dyedream_glasspane",
        "pasterdream:gold_carve_dyedream_glasspane", "pasterdream:dyedream_lartern",
        "pasterdream:dyedream_planks_pane",

        "pasterdream:pinkagaric_0", "pasterdream:pinkagaric_1",
        "pasterdream:pinkagaric_2", "pasterdream:pinkagaric_3",
        "pasterdream:dyedream_bud_0", "pasterdream:dyedream_bud_1",
        "pasterdream:dyedream_bud_2", "pasterdream:ice_bud_0",
        "pasterdream:dyedream_lily_pad", "pasterdream:dyedream_lotus",
        "pasterdream:dyedream_seagrass", "pasterdream:dyedream_sapling",
        "pasterdream:dyedream_crack",

        "pasterdream:cloud", "pasterdream:dark_cloud", "pasterdream:thick_cloud",

        "pasterdream:titanium_block", "pasterdream:raw_titanium_block",
        "pasterdream:moltengold_block", "pasterdream:blackmetal_block",
        "pasterdream:charged_amethyst_block", "pasterdream:wind_iron_block",
        "pasterdream:deepslate_titanium_ore", "pasterdream:moltengold_ore",
        "pasterdream:soul_ore", "pasterdream:pebble_0", "pasterdream:shadow_light_0",
        "pasterdream:vine_0", "pasterdream:goldenrod",
        "pasterdream:crop_0a", "pasterdream:crop_1a", "pasterdream:crop_2a",
        "pasterdream:crop_3a", "pasterdream:crop_4a",

        # 花草
        "pasterdream:flower_1", "pasterdream:flower_2", "pasterdream:flower_3",
        "pasterdream:flower_5", "pasterdream:flower_6", "pasterdream:flower_7",
        "pasterdream:flower_8", "pasterdream:flower_9", "pasterdream:flower_10",
        "pasterdream:flower_11", "pasterdream:flower_12", "pasterdream:flower_13",
        "pasterdream:flower_14", "pasterdream:flower_15", "pasterdream:flower_16",
        "pasterdream:flower_17", "pasterdream:flower_18",

        # 草
        "pasterdream:grass_1", "pasterdream:grass_2", "pasterdream:grass_3",
        "pasterdream:grass_4", "pasterdream:grass_5", "pasterdream:grass_6",
        "pasterdream:grass_7", "pasterdream:grass_8", "pasterdream:grass_9",
        "pasterdream:grass_10", "pasterdream:grass_11", "pasterdream:grass_12",
        "pasterdream:grass_13", "pasterdream:grass_14", "pasterdream:grass_15",
    }

    print(f"\n已注册方块数: {len(registered_blocks)}")

    if all_palette_blocks:
        unregistered_palette = sorted(all_palette_blocks - registered_blocks)
        if unregistered_palette:
            print(f"\n❌ 以下 {len(unregistered_palette)} 个方块在调色板中但未注册（导致紫黑块）：")
            for b in unregistered_palette:
                print(f"   🟪 {b}")
        else:
            print("\n✅ 所有调色板中的方块都已注册！")
    else:
        print("\n(未发现 pasterdream 命名空间的方块引用)")

    if all_item_ids:
        print(f"\n\n📦 结构中的物品引用（这些是物品，不影响方块显示）：")
        for i in sorted(all_item_ids):
            print(f"   📦 {i}")

    # 检查已知缺少的物品
    missing_items = []
    known_items = {
        "pasterdream:bacone_egg", "pasterdream:cake_base", "pasterdream:chocolate",
        "pasterdream:dough", "pasterdream:dream_coin_0", "pasterdream:dyedream_dust_piece",
        "pasterdream:dyedream_nugget", "pasterdream:dyedreamquartz", "pasterdream:flour",
        "pasterdream:fried_egg", "pasterdream:meltdream_crystal_0", "pasterdream:mortar",
        "pasterdream:pale_boneneedle", "pasterdream:pink_slimeball", "pasterdream:pliers",
        "pasterdream:sorbent", "pasterdream:water_glassjar", "pasterdream:worldtree_seedpod",
        "pasterdream:yeast", "pasterdream:embryo_belt",
    }

    # 检查新增的需要确认的物品
    recheck_items = {"pasterdream:dreamharp_of_wanderer", "pasterdream:sandwich_0"}
    missing_from_known = all_item_ids - known_items - recheck_items
    if missing_from_known:
        print(f"\n\n⚠️ 以下物品引用状态未知（可能需要检查是否已注册为物品）：")
        for i in sorted(missing_from_known):
            print(f"   ❓ {i}")


if __name__ == "__main__":
    main()
