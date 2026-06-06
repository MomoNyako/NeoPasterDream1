#!/usr/bin/env python3
"""补全所有缺失的方块Blockstate、Model和Texture文件"""
import os
import shutil

assets_dir = r"src/main/resources/assets/pasterdream"
libs_dir = r"libs/FixPasterDream-main/src/main/resources/assets/pasterdream"

blockstates_dir = os.path.join(assets_dir, "blockstates")
models_block_dir = os.path.join(assets_dir, "models", "block")
textures_block_dir = os.path.join(assets_dir, "textures", "block")


def write_json(path, content):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f"  ✅ 创建: {os.path.relpath(path, assets_dir)}")


def copy_texture(src, dst):
    """从libs复制纹理"""
    src_path = os.path.join(libs_dir, "textures", "block", src)
    dst_path = os.path.join(textures_block_dir, dst)
    if os.path.exists(src_path) and not os.path.exists(dst_path):
        shutil.copy2(src_path, dst_path)
        print(f"  ✅ 复制纹理: {src} → {dst}")
        return True
    elif os.path.exists(dst_path):
        print(f"  ⏭️ 纹理已存在: {dst}")
        return True
    else:
        print(f"  ❌ 源纹理不存在: {src}")
        return False


print("=" * 60)
print("🔧 补全缺失的方块资源文件 (第2版)")
print("=" * 60)

# ======================== 1. 🌐 复制拼音纹理到英文名 ========================
print("\n📦 第1步: 复制拼音纹理到英文名")
print("-" * 40)

texture_mappings = {
    # 矿石
    "tai_kuang_shi_.png": "titanium_ore.png",
    "feng_xing_zhe_shui_jing_kuang_shi_.png": "windrunner_crystal_ore.png",
    "ning_jie_zhi_feng_yun_kuang_.png": "congeal_wind_ore.png",
    # 建筑方块
    "mo_zhi_fang_jie_shi_.png": "polished_calcite.png",
    "fang_jie_shi_wa_.png": "calcite_tiles.png",
    # 农作物
    "meng_ran_cha_hua_.png": "crop_0a.png",
    "cang_bai_xue_lian_.png": "crop_1a.png",
    "liu_ming_jin_.png": "crop_2a.png",
    "ling_yun_hua_.png": "crop_3a.png",
    "mian_hua_.png": "crop_4a.png",
}

for src, dst in texture_mappings.items():
    copy_texture(src, dst)

# ======================== 2. 📄 创建缺失的 Blockstate JSON ========================
print("\n📦 第2步: 创建缺失的 Blockstate JSON")
print("-" * 40)

# 简单方块 blockstate
simple_blockstates = [
    "titanium_ore", "windrunner_crystal_ore", "congeal_wind_ore",
    "polished_calcite", "calcite_tiles",
    "stripped_dyedream_log", "stripped_dyedream_wood",
]
for name in simple_blockstates:
    path = os.path.join(blockstates_dir, f"{name}.json")
    if not os.path.exists(path):
        write_json(path, f'''{{
  "variants": {{
    "": {{
      "model": "pasterdream:block/{name}"
    }}
  }}
}}''')
    else:
        print(f"  ⏭️ 已存在: {name}.json")

# crop 方块 blockstate
for crop_name in ["crop_0a", "crop_1a", "crop_2a", "crop_3a", "crop_4a"]:
    path = os.path.join(blockstates_dir, f"{crop_name}.json")
    if not os.path.exists(path):
        write_json(path, f'''{{
  "variants": {{
    "": {{
      "model": "pasterdream:block/{crop_name}"
    }}
  }}
}}''')
    else:
        print(f"  ⏭️ 已存在: {crop_name}.json")

# 楼梯 blockstate
stair_blocks = ["calcite_tiles_stairs", "polished_calcite_stairs"]
for name in stair_blocks:
    path = os.path.join(blockstates_dir, f"{name}.json")
    if not os.path.exists(path):
        write_json(path, f'''{{
  "variants": {{
    "facing=east,half=bottom,shape=straight": {{ "model": "pasterdream:block/{name}" }},
    "facing=west,half=bottom,shape=straight": {{ "model": "pasterdream:block/{name}", "y": 180 }},
    "facing=south,half=bottom,shape=straight": {{ "model": "pasterdream:block/{name}", "y": 90 }},
    "facing=north,half=bottom,shape=straight": {{ "model": "pasterdream:block/{name}", "y": 270 }},
    "facing=east,half=bottom,shape=outer_right": {{ "model": "pasterdream:block/{name}_outer" }},
    "facing=west,half=bottom,shape=outer_right": {{ "model": "pasterdream:block/{name}_outer", "y": 180 }},
    "facing=south,half=bottom,shape=outer_right": {{ "model": "pasterdream:block/{name}_outer", "y": 90 }},
    "facing=north,half=bottom,shape=outer_right": {{ "model": "pasterdream:block/{name}_outer", "y": 270 }},
    "facing=east,half=bottom,shape=outer_left": {{ "model": "pasterdream:block/{name}_outer", "y": 270 }},
    "facing=west,half=bottom,shape=outer_left": {{ "model": "pasterdream:block/{name}_outer", "y": 90 }},
    "facing=south,half=bottom,shape=outer_left": {{ "model": "pasterdream:block/{name}_outer" }},
    "facing=north,half=bottom,shape=outer_left": {{ "model": "pasterdream:block/{name}_outer", "y": 180 }},
    "facing=east,half=bottom,shape=inner_right": {{ "model": "pasterdream:block/{name}_inner" }},
    "facing=west,half=bottom,shape=inner_right": {{ "model": "pasterdream:block/{name}_inner", "y": 180 }},
    "facing=south,half=bottom,shape=inner_right": {{ "model": "pasterdream:block/{name}_inner", "y": 90 }},
    "facing=north,half=bottom,shape=inner_right": {{ "model": "pasterdream:block/{name}_inner", "y": 270 }},
    "facing=east,half=bottom,shape=inner_left": {{ "model": "pasterdream:block/{name}_inner", "y": 270 }},
    "facing=west,half=bottom,shape=inner_left": {{ "model": "pasterdream:block/{name}_inner", "y": 90 }},
    "facing=south,half=bottom,shape=inner_left": {{ "model": "pasterdream:block/{name}_inner" }},
    "facing=north,half=bottom,shape=inner_left": {{ "model": "pasterdream:block/{name}_inner", "y": 180 }},
    "facing=east,half=top,shape=straight": {{ "model": "pasterdream:block/{name}", "x": 180 }},
    "facing=west,half=top,shape=straight": {{ "model": "pasterdream:block/{name}", "x": 180, "y": 180 }},
    "facing=south,half=top,shape=straight": {{ "model": "pasterdream:block/{name}", "x": 180, "y": 90 }},
    "facing=north,half=top,shape=straight": {{ "model": "pasterdream:block/{name}", "x": 180, "y": 270 }},
    "facing=east,half=top,shape=outer_right": {{ "model": "pasterdream:block/{name}_outer", "x": 180, "y": 90 }},
    "facing=west,half=top,shape=outer_right": {{ "model": "pasterdream:block/{name}_outer", "x": 180, "y": 270 }},
    "facing=south,half=top,shape=outer_right": {{ "model": "pasterdream:block/{name}_outer", "x": 180, "y": 180 }},
    "facing=north,half=top,shape=outer_right": {{ "model": "pasterdream:block/{name}_outer", "x": 180 }},
    "facing=east,half=top,shape=outer_left": {{ "model": "pasterdream:block/{name}_outer", "x": 180, "y": 180 }},
    "facing=west,half=top,shape=outer_left": {{ "model": "pasterdream:block/{name}_outer", "x": 180 }},
    "facing=south,half=top,shape=outer_left": {{ "model": "pasterdream:block/{name}_outer", "x": 180, "y": 90 }},
    "facing=north,half=top,shape=outer_left": {{ "model": "pasterdream:block/{name}_outer", "x": 180, "y": 270 }},
    "facing=east,half=top,shape=inner_right": {{ "model": "pasterdream:block/{name}_inner", "x": 180, "y": 90 }},
    "facing=west,half=top,shape=inner_right": {{ "model": "pasterdream:block/{name}_inner", "x": 180, "y": 270 }},
    "facing=south,half=top,shape=inner_right": {{ "model": "pasterdream:block/{name}_inner", "x": 180, "y": 180 }},
    "facing=north,half=top,shape=inner_right": {{ "model": "pasterdream:block/{name}_inner", "x": 180 }},
    "facing=east,half=top,shape=inner_left": {{ "model": "pasterdream:block/{name}_inner", "x": 180, "y": 180 }},
    "facing=west,half=top,shape=inner_left": {{ "model": "pasterdream:block/{name}_inner", "x": 180 }},
    "facing=south,half=top,shape=inner_left": {{ "model": "pasterdream:block/{name}_inner", "x": 180, "y": 90 }},
    "facing=north,half=top,shape=inner_left": {{ "model": "pasterdream:block/{name}_inner", "x": 180, "y": 270 }}
  }}
}}''')
    else:
        print(f"  ⏭️ 已存在: {name}.json")

# 台阶 blockstate
slab_blocks = ["calcite_tiles_slab", "polished_calcite_slab"]
for name in slab_blocks:
    path = os.path.join(blockstates_dir, f"{name}.json")
    if not os.path.exists(path):
        write_json(path, f'''{{
  "variants": {{
    "type=bottom": {{ "model": "pasterdream:block/{name}" }},
    "type=double": {{ "model": "pasterdream:block/{name}_full" }},
    "type=top": {{ "model": "pasterdream:block/{name}_top" }}
  }}
}}''')
    else:
        print(f"  ⏭️ 已存在: {name}.json")

# 墙 blockstate
wall_blocks = ["calcite_tiles_wall", "polished_calcite_wall"]
for name in wall_blocks:
    path = os.path.join(blockstates_dir, f"{name}.json")
    if not os.path.exists(path):
        write_json(path, f'''{{
  "multipart": [
    {{ "when": {{ "up": "true" }}, "apply": {{ "model": "pasterdream:block/{name}_post" }} }},
    {{ "when": {{ "north": "low" }}, "apply": {{ "model": "pasterdream:block/{name}_side", "uvlock": true }} }},
    {{ "when": {{ "east": "low" }}, "apply": {{ "model": "pasterdream:block/{name}_side", "y": 90, "uvlock": true }} }},
    {{ "when": {{ "south": "low" }}, "apply": {{ "model": "pasterdream:block/{name}_side", "y": 180, "uvlock": true }} }},
    {{ "when": {{ "west": "low" }}, "apply": {{ "model": "pasterdream:block/{name}_side", "y": 270, "uvlock": true }} }},
    {{ "when": {{ "north": "tall" }}, "apply": {{ "model": "pasterdream:block/{name}_side_tall", "uvlock": true }} }},
    {{ "when": {{ "east": "tall" }}, "apply": {{ "model": "pasterdream:block/{name}_side_tall", "y": 90, "uvlock": true }} }},
    {{ "when": {{ "south": "tall" }}, "apply": {{ "model": "pasterdream:block/{name}_side_tall", "y": 180, "uvlock": true }} }},
    {{ "when": {{ "west": "tall" }}, "apply": {{ "model": "pasterdream:block/{name}_side_tall", "y": 270, "uvlock": true }} }}
  ]
}}''')
    else:
        print(f"  ⏭️ 已存在: {name}.json")

# ======================== 3. 📄 创建缺失的 Model JSON ========================
print("\n📦 第3步: 创建缺失的 Model JSON")
print("-" * 40)

# 简单方块 model (cube_all)
simple_models = {
    "titanium_ore": "pasterdream:block/titanium_ore",
    "windrunner_crystal_ore": "pasterdream:block/windrunner_crystal_ore",
    "congeal_wind_ore": "pasterdream:block/congeal_wind_ore",
    "polished_calcite": "pasterdream:block/polished_calcite",
    "calcite_tiles": "pasterdream:block/calcite_tiles",
}
for name, tex in simple_models.items():
    path = os.path.join(models_block_dir, f"{name}.json")
    if not os.path.exists(path):
        write_json(path, f'''{{
  "parent": "minecraft:block/cube_all",
  "textures": {{
    "all": "{tex}"
  }}
}}''')
    else:
        print(f"  ⏭️ 已存在: {name}.json")

# 原木模型 (cube_column)
log_models = {
    "stripped_dyedream_log": ("pasterdream:block/dyedream_log_top", "pasterdream:block/dyedream_log"),
    "stripped_dyedream_wood": ("pasterdream:block/dyedream_log", "pasterdream:block/dyedream_log"),
}
for name, (end_tex, side_tex) in log_models.items():
    path = os.path.join(models_block_dir, f"{name}.json")
    if not os.path.exists(path):
        write_json(path, f'''{{
  "parent": "minecraft:block/cube_column",
  "textures": {{
    "end": "{end_tex}",
    "side": "{side_tex}"
  }}
}}''')
    else:
        print(f"  ⏭️ 已存在: {name}.json")

# crop 模型 (cross)
for crop_name in ["crop_0a", "crop_1a", "crop_2a", "crop_3a", "crop_4a"]:
    path = os.path.join(models_block_dir, f"{crop_name}.json")
    if not os.path.exists(path):
        write_json(path, f'''{{
  "parent": "minecraft:block/cross",
  "textures": {{
    "cross": "pasterdream:block/{crop_name}",
    "particle": "pasterdream:block/{crop_name}"
  }},
  "render_type": "cutout"
}}''')
    else:
        print(f"  ⏭️ 已存在: {crop_name}.json")

# 楼梯模型
stair_textures = {
    "calcite_tiles_stairs": "pasterdream:block/calcite_tiles",
    "polished_calcite_stairs": "pasterdream:block/polished_calcite",
}
for base_name, tex in stair_textures.items():
    for suffix in ["", "_inner", "_outer"]:
        path = os.path.join(models_block_dir, f"{base_name}{suffix}.json")
        if not os.path.exists(path):
            parent_map = {"": "minecraft:block/stairs", "_inner": "minecraft:block/inner_stairs", "_outer": "minecraft:block/outer_stairs"}
            write_json(path, f'''{{
  "parent": "{parent_map[suffix]}",
  "textures": {{
    "bottom": "{tex}",
    "top": "{tex}",
    "side": "{tex}"
  }}
}}''')
        else:
            print(f"  ⏭️ 已存在: {base_name}{suffix}.json")

# 台阶模型
slab_textures = {
    "calcite_tiles_slab": "pasterdream:block/calcite_tiles",
    "polished_calcite_slab": "pasterdream:block/polished_calcite",
}
for base_name, tex in slab_textures.items():
    for suffix in ["", "_top", "_full"]:
        path = os.path.join(models_block_dir, f"{base_name}{suffix}.json")
        if not os.path.exists(path):
            if suffix == "_full":
                write_json(path, f'''{{
  "parent": "minecraft:block/cube_all",
  "textures": {{
    "all": "{tex}"
  }}
}}''')
            else:
                write_json(path, f'''{{
  "parent": "minecraft:block/slab{"_top" if suffix == "_top" else ""}",
  "textures": {{
    "bottom": "{tex}",
    "top": "{tex}",
    "side": "{tex}"
  }}
}}''')
        else:
            print(f"  ⏭️ 已存在: {base_name}{suffix}.json")

# 墙模型
wall_textures = {
    "calcite_tiles_wall": "pasterdream:block/calcite_tiles",
    "polished_calcite_wall": "pasterdream:block/polished_calcite",
}
for base_name, tex in wall_textures.items():
    for suffix in ["_inventory", "_post", "_side", "_side_tall"]:
        path = os.path.join(models_block_dir, f"{base_name}{suffix}.json")
        if not os.path.exists(path):
            parent_map = {
                "_inventory": "minecraft:block/wall_inventory",
                "_post": "minecraft:block/wall_post",
                "_side": "minecraft:block/wall_side",
                "_side_tall": "minecraft:block/wall_side_tall",
            }
            write_json(path, f'''{{
  "parent": "{parent_map[suffix]}",
  "textures": {{
    "wall": "{tex}"
  }}
}}''')
        else:
            print(f"  ⏭️ 已存在: {base_name}{suffix}.json")

# ======================== 4. 🎨 检查并统计结果 ========================
print("\n📦 第4步: 最终检查")
print("-" * 40)

# 检查所有PDBlocks中的方块是否都有blockstate
all_blocks = [
    "titanium_ore", "windrunner_crystal_ore", "congeal_wind_ore",
    "polished_calcite", "calcite_tiles", "crop_0a", "crop_1a", "crop_2a", "crop_3a", "crop_4a",
    "stripped_dyedream_log", "stripped_dyedream_wood",
    "calcite_tiles_stairs", "calcite_tiles_slab", "calcite_tiles_wall",
    "polished_calcite_slab", "polished_calcite_stairs", "polished_calcite_wall",
]

missing = []
for name in all_blocks:
    bs_path = os.path.join(blockstates_dir, f"{name}.json")
    model_path = os.path.join(models_block_dir, f"{name}.json")
    if not os.path.exists(bs_path):
        missing.append(f"  ❌ 缺少 blockstate: {name}")
    if not os.path.exists(model_path) and name in simple_blockstates + ["crop_0a", "crop_1a", "crop_2a", "crop_3a", "crop_4a"]:
        missing.append(f"  ❌ 缺少 model: {name}")

if missing:
    print("\n仍有缺失的文件:")
    for m in missing:
        print(m)
else:
    print("\n✅ 所有必要的资源文件都已就绪！")

print("\n" + "=" * 60)
print("✅ 补全完成！请重新编译并启动游戏测试")
print("=" * 60)
