#!/usr/bin/env python3
"""
补全语言文件：从旧模组导入缺失的翻译
"""
import json
import os

# 文件路径
zh_path = r"src/main/resources/assets/pasterdream/lang/zh_cn.json"
en_path = r"src/main/resources/assets/pasterdream/lang/en_us.json"
old_zh_path = r"libs/FixPasterDream-main/src/main/resources/assets/pasterdream/lang/zh_cn.json"
old_en_path = r"libs/FixPasterDream-main/src/main/resources/assets/pasterdream/lang/en_us.json"

# 读取当前语言文件
with open(zh_path, 'r', encoding='utf-8') as f:
    zh_data = json.load(f)
with open(en_path, 'r', encoding='utf-8') as f:
    en_data = json.load(f)

# 读取旧模组语言文件
with open(old_zh_path, 'r', encoding='utf-8') as f:
    old_zh = json.load(f)
with open(old_en_path, 'r', encoding='utf-8') as f:
    old_en = json.load(f)

# 需要补全的物品列表（已注册但缺翻译）
missing_items = [
    # 建筑方块
    "amber_candy_ore", "blackmetal_block", "bricks_dyedreamquartz_block",
    "calcite_tiles", "calcite_tiles_slab", "calcite_tiles_stairs", "calcite_tiles_wall",
    "carve_dyedream_glass", "carve_dyedream_glasspane",
    "charged_amethyst_block", "chiseled_dyedreamquartz_block",
    "cloud", "congeal_wind_ore", "crop_0a", "crop_1a", "crop_2a", "crop_3a", "crop_4a",
    "dark_cloud", "deepslate_titanium_ore", "dream_accumulator",
    "dream_cauldron", "dream_train_structure",
    "dyedream_block", "dyedream_bud_0", "dyedream_bud_1", "dyedream_bud_2",
    "dyedream_bud_block", "dyedream_bud_slab", "dyedream_bud_stairs", "dyedream_bud_wall",
    "dyedream_crack", "dyedream_desk", "dyedream_dirt", "dyedream_glass",
    "dyedream_glasspane", "dyedream_grass", "dyedream_ice", "dyedream_lartern",
    "dyedream_leaves", "dyedream_lily_pad", "dyedream_log", "dyedream_lotus",
    "dyedream_packed_ice", "dyedream_planks", "dyedream_planks_button",
    "dyedream_planks_door", "dyedream_planks_fence", "dyedream_planks_fencegate",
    "dyedream_planks_pane", "dyedream_planks_pressure_plate",
    "dyedream_planks_slab", "dyedream_planks_stairs", "dyedream_planks_trapdoor",
    "dyedream_sand", "dyedream_sapling", "dyedream_seagrass", "dyedream_wood",
    "dyedream_worldtree_leaves", "dyedreamdust_ore", "dyedreamquartz_block",
    "dyedreamquartz_block_slab", "dyedreamquartz_block_stairs",
    "dyedreamquartz_block_wall", "dyedreamquartz_ore",
    "flower_1", "flower_2", "flower_3", "flower_5", "flower_6", "flower_7",
    "flower_8", "flower_9", "flower_10", "flower_11", "flower_12", "flower_13",
    "flower_14", "flower_15", "flower_16", "flower_17", "flower_18",
    "gold_carve_dyedream_glass", "gold_carve_dyedream_glasspane",
    "goldenrod", "grass_1", "grass_2", "grass_3", "grass_4", "grass_5",
    "grass_6", "grass_7", "grass_8", "grass_9", "grass_10", "grass_11",
    "grass_12", "grass_13", "grass_14", "grass_15",
    "ice_bud_0", "icestone", "jungle_spore", "life_crystal",
    "meltdream_chest", "meltdream_chest_open",
    "moltengold_block", "moltengold_ore",
    "pebble_0", "pillar_dyedreamquartz_block",
    "pinkagaric_0", "pinkagaric_1", "pinkagaric_2", "pinkagaric_3",
    "pinkegg", "pinkslime_block", "pliers",
    "polished_calcite", "polished_calcite_slab",
    "polished_calcite_stairs", "polished_calcite_wall",
    "raw_titanium_block", "shadow_chest", "shadow_light_0",
    "smooth_dyedreamquartz_block", "soul_ore",
    "stripped_dyedream_log", "stripped_dyedream_wood",
    "the_endless_book_of_dream_seekers", "thick_cloud",
    "titanium_block", "titanium_ore",
    "vine_0", "wind_iron_block", "windrunner_crystal_ore",
    "worldtree_seedpod",

    # 物品类（Curio/饰品/特殊）
    "allkinds_ring", "boboji_curio", "bright_butterfly_curio",
    "cross_necklace", "degenerate_bodys", "dream_traveler_belt",
    "duke_coin_curio", "endeye_charm", "evasion_cloak",
    "feather_necklace", "fire_0_necklace", "garland",
    "gold_charm", "health_0_necklace", "hiyori_head",
    "iceshadow_curio", "light_butterfly_curio",
    "nature_belt", "paper_plane",
    "qym_head", "rabbit_0_necklace",
    "snow_vow_head", "terra_charm", "test_curio",
    "traveler_belt", "turnback_cloak",
    "white_flower_body",

    # 调试法杖
    "debug_wand_dream_train", "debug_wand_pinkagaric_0",
    "debug_wand_pinkagaric_1", "debug_wand_pinkagaric_2",
    "debug_wand_pinkagaric_3", "debug_wand_worldtree",
]

# ========== 补全中文 ==========
zh_additions = {}
for item in missing_items:
    # 尝试 item key
    key = f"item.pasterdream.{item}"
    if key not in zh_data:
        # 从旧模组找
        val = old_zh.get(key, old_zh.get(f"block.pasterdream.{item}", ""))
        if val:
            zh_additions[key] = val

# 还要补全某些 blocks 有而 items 没有的
for item in missing_items:
    block_key = f"block.pasterdream.{item}"
    if block_key not in zh_data:
        val = old_zh.get(block_key, old_zh.get(f"item.pasterdream.{item}", ""))
        if val:
            zh_additions[block_key] = val

print(f"🇨🇳 中文补全: {len(zh_additions)} 条")
for k, v in sorted(zh_additions.items()):
    print(f'  "{k}": "{v}",')

# ========== 补全英文 ==========
en_additions = {}
for item in missing_items:
    key = f"item.pasterdream.{item}"
    if key not in en_data:
        val = old_en.get(key, old_en.get(f"block.pasterdream.{item}", ""))
        if val:
            en_additions[key] = val

for item in missing_items:
    block_key = f"block.pasterdream.{item}"
    if block_key not in en_data:
        val = old_en.get(block_key, old_en.get(f"item.pasterdream.{item}", ""))
        if val:
            en_additions[block_key] = val

print(f"\n🇬🇧 英文补全: {len(en_additions)} 条")

# ========== 写入文件 ==========
zh_data.update(zh_additions)
en_data.update(en_additions)

with open(zh_path, 'w', encoding='utf-8') as f:
    json.dump(zh_data, f, ensure_ascii=False, indent=4)
print(f"\n✅ 已写入 zh_cn.json ({len(zh_data)} 条)")

with open(en_path, 'w', encoding='utf-8') as f:
    json.dump(en_data, f, ensure_ascii=False, indent=4)
print(f"✅ 已写入 en_us.json ({len(en_data)} 条)")
