#!/usr/bin/env python3
"""检查所有新增模型JSON的语法和纹理引用"""
import json
import os
import glob

# 检查所有block模型
model_files = glob.glob("src/main/resources/assets/pasterdream/models/block/*.json")
model_files += glob.glob("src/main/resources/assets/pasterdream/models/item/*.json")

print(f"检查 {len(model_files)} 个模型文件...\n")

errors = []
missing_textures = {}

# 查找新增的方块模型
new_blocks = [
    "polished_calcite", "calcite_tiles",
    "calcite_tiles_stairs", "calcite_tiles_slab",
    "polished_calcite_stairs", "polished_calcite_slab",
    "calcite_tiles_wall", "polished_calcite_wall",
    "crop_2a",
]

for model in model_files:
    name = os.path.basename(model)
    try:
        with open(model, 'r', encoding='utf-8') as f:
            data = json.load(f)
        
        # 检查纹理引用
        if "textures" in data:
            for key, tex in data["textures"].items():
                if tex.startswith("pasterdream:block/"):
                    tex_name = tex.split("/")[-1]
                    tex_path = f"src/main/resources/assets/pasterdream/textures/block/{tex_name}.png"
                    if not os.path.exists(tex_path):
                        errors.append(f"[❌ 纹理缺失] {name}: textures.{key} -> {tex}")
                    # 只对新的方块显示
                    if any(b in name for b in new_blocks) or name == "crop_2a.json":
                        marked = "✅" if os.path.exists(tex_path) else "❌"
                        if name not in missing_textures:
                            missing_textures[name] = []
                        missing_textures[name].append((tex, marked))
        
        # 检查父模型引用
        if "parent" in data:
            parent = data["parent"]
            if parent.startswith("pasterdream:block/"):
                parent_name = parent.split("/")[-1]
                parent_path = f"src/main/resources/assets/pasterdream/models/block/{parent_name}.json"
                if not os.path.exists(parent_path):
                    errors.append(f"[❌ 父模型缺失] {name}: parent -> {parent}")
        
    except json.JSONDecodeError as e:
        errors.append(f"[❌ JSON语法错误] {name}: {e}")

if missing_textures:
    print("📊 新增方块的纹理检查:")
    for name, texs in sorted(missing_textures.items()):
        print(f"\n  {name}:")
        for tex, status in texs:
            print(f"    {status} {tex}")

if errors:
    print(f"\n\n❌ 发现 {len(errors)} 个错误:")
    for e in errors:
        print(f"  {e}")
else:
    print("\n✅ 所有JSON文件语法正确，纹理引用均存在！")
