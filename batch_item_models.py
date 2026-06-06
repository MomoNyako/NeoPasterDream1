#!/usr/bin/env python3
"""批量创建缺失的方块物品模型"""
import os

items_dir = r"src/main/resources/assets/pasterdream/models/item"

# 需要创建的模型列表
# 格式：(注册名, 父模型类型, 纹理路径)
# 父模型类型: "block" → 引用 block/<name> ; "generated" → 使用 item/generated
models = [
    # 方解石系列楼梯/台阶
    ("calcite_tiles_stairs", "block", None),
    ("calcite_tiles_slab", "block", None),
    ("polished_calcite_stairs", "block", None),
    ("polished_calcite_slab", "block", None),
    # 去皮原木
    ("stripped_dyedream_log", "block", None),
    ("stripped_dyedream_wood", "block", None),
    # 农作物
    ("crop_2a", "generated", "pasterdream:block/crop_2a"),
]

for name, model_type, texture in models:
    path = os.path.join(items_dir, f"{name}.json")
    if os.path.exists(path):
        print(f"  ⏭️ 已存在: {name}")
        continue
    
    if model_type == "block":
        content = f'''{{
  "parent": "pasterdream:block/{name}"
}}'''
    elif model_type == "generated":
        content = f'''{{
  "parent": "item/generated",
  "textures": {{
    "layer0": "{texture}"
  }}
}}'''
    
    with open(path, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f"  ✅ 创建: {name}")

print("\n🎉 所有缺失的物品模型已创建！")
