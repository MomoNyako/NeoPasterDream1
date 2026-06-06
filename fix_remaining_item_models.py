#!/usr/bin/env python3
"""修复所有剩下缺失的物品模型"""
import json, os

items_dir = "src/main/resources/assets/pasterdream/models/item"
blockstates_dir = "src/main/resources/assets/pasterdream/blockstates"
blocks_dir = "src/main/resources/assets/pasterdream/models/block"

# 需要检查的方块列表（刚加入创造标签页的）
new_tab_blocks = [
    "calcite_tiles", "polished_calcite",
    "calcite_tiles_stairs", "calcite_tiles_slab", "calcite_tiles_wall",
    "polished_calcite_stairs", "polished_calcite_slab", "polished_calcite_wall",
    "stripped_dyedream_log", "stripped_dyedream_wood",
    "crop_2a",
]

print("检查新加入创造标签页的方块物品模型：")
for name in new_tab_blocks:
    ipath = os.path.join(items_dir, f"{name}.json")
    if os.path.exists(ipath):
        print(f"  ✅ {name} 已有物品模型")
    else:
        print(f"  ❌ {name} 缺失物品模型 - 即将创建")
        # 对于简单方块，引用 block 模型
        content = f'{{\n  "parent": "pasterdream:block/{name}"\n}}'
        with open(ipath, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"  ✅ {name} 物品模型已创建")

# 修复 dream_train_structure 的拼音纹理引用
print("\n修复 dream_train_structure 物品模型：")
dt_path = os.path.join(items_dir, "dream_train_structure.json")
if os.path.exists(dt_path):
    with open(dt_path, 'r', encoding='utf-8') as f:
        data = json.load(f)
    old_tex = data.get("textures", {}).get("layer0", "")
    if "chuang_zao_lan" in old_tex:
        # 替换为英文名纹理
        data["textures"]["layer0"] = "pasterdream:item/dream_train_structure"
        with open(dt_path, 'w', encoding='utf-8') as f:
            json.dump(data, f, indent=2)
        print(f"  ✅ 修复纹理: {old_tex} → pasterdream:item/dream_train_structure")
        
        # 检查纹理是否存在
        tex_path = "src/main/resources/assets/pasterdream/textures/item/dream_train_structure.png"
        if not os.path.exists(tex_path):
            # 从libs复制拼音纹理
            lib_tex = "libs/FixPasterDream-main/src/main/resources/assets/pasterdream/textures/item"
            # 查找对应的拼音纹理
            import glob as g
            pinyin_files = g.glob(f"{lib_tex}/chuang_zao_lan*.png")
            if pinyin_files:
                import shutil
                shutil.copy2(pinyin_files[0], tex_path)
                print(f"  ✅ 复制纹理: {os.path.basename(pinyin_files[0])} → dream_train_structure.png")
    else:
        print(f"  ⏭️ 纹理已正确 ({old_tex})")

# 还要检查有没有 'pinkagaric' 的无用物品模型
pink_path = os.path.join(items_dir, "pinkagaric.json")
print(f"\n检查 pinkagaric 无主物品模型：")
if os.path.exists(pink_path):
    os.remove(pink_path)
    print(f"  🗑️ 已删除无用文件: pinkagaric.json")
else:
    print(f"  ⏭️ 不存在或已删除")

print("\n✅ 全部修复完成！")
