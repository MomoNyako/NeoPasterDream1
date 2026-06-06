#!/usr/bin/env python3
"""全面扫描栅栏/墙/屏风类方块的模型文件完整性"""
import os

blocks_dir = "src/main/resources/assets/pasterdream/models/block"
items_dir = "src/main/resources/assets/pasterdream/models/item"

# 墙类方块需要：post, side, side_tall, inventory 4个模型
wall_groups = {
    "dyedream_bud_wall": {"post", "side", "side_tail"},  # 旧的可能有 typo
    "dyedreamquartz_block_wall": {"post", "side", "side_tail"},
    "polished_calcite_wall": {"post", "side", "side_tail"},
    "calcite_tiles_wall": {"post", "side", "side_tail"},
}

# 栅栏类需要：post, side, inventory 3个模型
fence_groups = {
    "dyedream_planks_fence": {"post", "side", "inventory"},
}

# 屏风/玻璃板需要：post, side, side_alt, noside, noside_alt 5个模型
pane_groups = {
    "dyedream_planks_pane": {"post", "side", "side_alt", "noside", "noside_alt"},
    "dyedream_glasspane": {"post", "side", "side_alt", "noside", "noside_alt"},
    "carve_dyedream_glasspane": {"post", "side", "side_alt", "noside", "noside_alt"},
    "gold_carve_dyedream_glasspane": {"post", "side", "side_alt", "noside", "noside_alt"},
}

def check_files(base_name, variants):
    missing = []
    for v in variants:
        # 处理特殊命名
        filename = f"{base_name}_{v}.json"
        # side_tail -> side_tall (真实文件名)
        actual = filename.replace("side_tail", "side_tall")
        fpath = os.path.join(blocks_dir, actual)
        if not os.path.exists(fpath):
            missing.append(actual)
    return missing

print("🔍 === 墙壁模型检查 === 🔍")
for group, variants in wall_groups.items():
    missing = check_files(group, variants)
    if missing:
        print(f"  ❌ {group}: 缺失 {missing}")
    else:
        print(f"  ✅ {group}: 齐全")

print("\n🔍 === 栅栏模型检查 === 🔍")
for group, variants in fence_groups.items():
    missing = check_files(group, variants)
    if missing:
        print(f"  ❌ {group}: 缺失 {missing}")
    else:
        print(f"  ✅ {group}: 齐全")

print("\n🔍 === 屏风/玻璃板模型检查 === 🔍")
for group, variants in pane_groups.items():
    missing = check_files(group, variants)
    if missing:
        print(f"  ❌ {group}: 缺失 {missing}")
    else:
        print(f"  ✅ {group}: 齐全")

# 检查blockstate
print("\n🔍 === BlockState 文件检查 === 🔍")
bs_dir = "src/main/resources/assets/pasterdream/blockstates"
all_bs_blocks = ["dyedream_bud_wall", "dyedreamquartz_block_wall", "polished_calcite_wall", 
                 "calcite_tiles_wall", "dyedream_planks_fence", "dyedream_planks_fencegate",
                 "dyedream_planks_pane", "dyedream_glasspane", "carve_dyedream_glasspane",
                 "gold_carve_dyedream_glasspane"]
for name in all_bs_blocks:
    path = os.path.join(bs_dir, f"{name}.json")
    if os.path.exists(path):
        print(f"  ✅ {name}.json")
    else:
        print(f"  ❌ {name}.json 缺失！")