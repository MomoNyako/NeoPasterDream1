#!/usr/bin/env python3
"""检查所有PDBlocks注册的方块是否缺少item模型"""
import re
import os

# 读取PDBlocks
with open(r"src/main/java/com/pasterdream/pasterdreammod/registry/PDBlocks.java", 'r', encoding='utf-8') as f:
    content = f.read()

# 提取所有 registerBlock 的注册名
registry_pattern = r'registerBlock\("([^"]+)"'
all_blocks = set(re.findall(registry_pattern, content))

# 也包括 registerSimpleBlock
simple_pattern = r'registerSimpleBlock\("([^"]+)"'
all_blocks.update(re.findall(simple_pattern, content))

print(f"📦 PDBlocks 中注册的方块总数: {len(all_blocks)}")

# 检查 models/item 目录
items_dir = r"src/main/resources/assets/pasterdream/models/item"
if os.path.isdir(items_dir):
    existing_models = {f[:-5] for f in os.listdir(items_dir) if f.endswith('.json')}
else:
    existing_models = set()

print(f"📄 已有物品模型数: {len(existing_models)}")

# 找出缺失的物品模型
missing_models = sorted(all_blocks - existing_models)
print(f"\n❌ 缺少物品模型的方块 ({len(missing_models)} 个):")
for b in missing_models:
    # 跳过流体
    if "liquid" in b:
        continue
    print(f"   ❌ {b}")

# 特别检查墙、楼梯、台阶
wall_stair_slab = [b for b in missing_models if any(x in b for x in ["_wall", "_stairs", "_slab", "_fence", "_door", "_trapdoor", "_button", "_pane"])]
if wall_stair_slab:
    print(f"\n⚠️ 其中墙/楼梯/台阶/门相关 ({len(wall_stair_slab)} 个):")
    for b in wall_stair_slab:
        print(f"   🧱 {b}")

# 检查纹理是否存在
print("\n📸 检查 blockstate 文件...")
blockstates_dir = r"src/main/resources/assets/pasterdream/blockstates"
if os.path.isdir(blockstates_dir):
    existing_blockstates = {f[:-5] for f in os.listdir(blockstates_dir) if f.endswith('.json')}
    missing_bs = sorted(all_blocks - existing_blockstates)
    if missing_bs:
        print(f"❌ 缺少 blockstate 的方块 ({len(missing_bs)} 个):")
        for b in missing_bs:
            print(f"   🟪 {b}")
    else:
        print("✅ 所有方块都有 blockstate 文件！")
