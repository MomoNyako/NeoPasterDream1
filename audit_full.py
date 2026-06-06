#!/usr/bin/env python3
"""全方位审计：检查物品注册、创造标签、语言文件的完整性"""
import re
import os
import json
import glob
import nbtlib

# ========== 文件路径 ==========
PDItems_path = r"src/main/java/com/pasterdream/pasterdreammod/registry/PDItems.java"
PDCreativeTabs_path = r"src/main/java/com/pasterdream/pasterdreammod/registry/PDCreativeTabs.java"
zh_cn_path = r"src/main/resources/assets/pasterdream/lang/zh_cn.json"
en_us_path = r"src/main/resources/assets/pasterdream/lang/en_us.json"

# ========== 1. 从 PDItems.java 提取所有已注册物品名 ==========
def extract_registered_items(java_path):
    """从 PDItems.java 提取所有注册的物品ID"""
    items = set()
    with open(java_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 匹配: registerSimpleItem("xxx", ...
    # 匹配: registerSimpleBlockItem("xxx", ...
    # 匹配: register("xxx", ...
    # 匹配: registerItem("xxx", ...
    patterns = [
        r'registerSimpleItem\("([^"]+)"',
        r'registerSimpleBlockItem\("([^"]+)"',
        r'\bregister\("([^"]+)"',
        r'registerItem\("([^"]+)"',
        r'quickItem\("([^"]+)"',
        r'quickFood\("([^"]+)"',
        r'quickTool\("([^"]+)"',
        r'quickCurio\("([^"]+)"',
        r'simpleItem\("([^"]+)"',
        r'foodItem\("([^"]+)"',
        r'toolItem\("([^"]+)"',
        r'curioItem\("([^"]+)"',
        r'registerCustom\("([^"]+)"',
    ]
    for p in patterns:
        items.update(re.findall(p, content))
    
    return items

# ========== 2. 从 PDCreativeTabs.java 提取所有已添加的物品 ==========
def extract_creative_tab_items(java_path):
    """从 PDCreativeTabs.java 提取所有添加到创造标签页的物品"""
    items = set()
    with open(java_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 匹配 output.accept(PDItems.XXX.get()) 或 output.accept(PDBlocks.XXX.get())
    patterns = [
        r'output\.accept\(PDItems\.(\w+)\.get\(\)\)',
        r'output\.accept\(PDBlocks\.(\w+)\.get\(\)\)',
    ]
    for p in patterns:
        items.update(re.findall(p, content))
    
    return items

# ========== 3. 从语言文件提取所有条目 ==========
def extract_lang_entries(lang_path):
    """从语言文件提取所有key"""
    with open(lang_path, 'r', encoding='utf-8') as f:
        data = json.load(f)
    return set(data.keys())

# ========== 4. 从 PDItems.java 提取所有字段名 ==========
def extract_pditems_fields(java_path):
    """提取 PDItems 中的所有字段名和对应的注册ID"""
    fields = {}  # field_name -> registry_id
    with open(java_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 匹配: public static final XXX FIELD_NAME = ITEMS.register("registry_id", ...
    # 关键：找到所有 DeferredItem 字段 -> 注册名
    pattern = r'public static final DeferredItem<[^>]+> (\w+)\s*=\s*(?:ITEMS\.register\("([^"]+)"|ItemMigrationAPI\.\w+Item\("([^"]+)"|ITEMS\.registerSimpleItem\("([^"]+)"|ITEMS\.registerSimpleBlockItem\("([^"]+)")'
    
    for match in re.finditer(pattern, content):
        field_name = match.group(1)
        # 取第一个非 None 的组
        reg_id = match.group(2) or match.group(3) or match.group(4) or match.group(5)
        fields[field_name] = reg_id
    
    return fields

# ========== 5. 从 NBT 结构中提取物品引用 ==========
def extract_nbt_item_refs(libs_dir, main_dir):
    """从NBT结构文件中提取所有pasterdream:物品引用"""
    item_refs = set()
    
    # 检查libs和main的结构文件
    for base_dir in [libs_dir, main_dir]:
        if not os.path.isdir(base_dir):
            continue
        for fpath in glob.glob(os.path.join(base_dir, "*.nbt")):
            try:
                nbt = nbtlib.load(fpath)
                blocks_data = nbt.get("blocks", [])
                for block in blocks_data:
                    block_nbt = block.get("nbt", {})
                    if block_nbt:
                        # 检查物品实体
                        item_id = block_nbt.get("Item", None)
                        if item_id:
                            item_refs.add(str(item_id))
                        # 检查箱子中的物品
                        items = block_nbt.get("Items", [])
                        for item in items:
                            item_id = item.get("id", "")
                            if item_id:
                                item_refs.add(str(item_id))
            except:
                pass
    
    return item_refs

# ========== 主函数 ==========
print("=" * 70)
print("🧩 PasterDream 模组完整审计报告")
print("=" * 70)

# 提取数据
print("\n📥 正在提取数据...")
registered_items = extract_registered_items(PDItems_path)
print(f"  PDItems 注册物品数: {len(registered_items)}")

fields_map = extract_pditems_fields(PDItems_path)
print(f"  PDItems 字段数: {len(fields_map)}")

tab_items = extract_creative_tab_items(PDCreativeTabs_path)
print(f"  创造标签页物品数: {len(tab_items)}")

zh_lang = extract_lang_entries(zh_cn_path)
print(f"  中文语言条目数: {len(zh_lang)}")

en_lang = extract_lang_entries(en_us_path)
print(f"  英文语言条目数: {len(en_lang)}")

# ========== A) 检查物品引用 ==========
print("\n" + "=" * 70)
print("A) 结构文件中的物品引用（已注册 vs 未注册）")
print("=" * 70)

libs_struct_dir = r"libs/FixPasterDream-main/src/main/resources/data/pasterdream/structures"
main_struct_dir = r"src/main/resources/data/pasterdream/structures"

nbt_item_refs = extract_nbt_item_refs(libs_struct_dir, main_struct_dir)
pasterdream_refs = {r for r in nbt_item_refs if r.startswith("pasterdream:")}
ref_ids = {r.split(":", 1)[1] for r in pasterdream_refs}

unregistered_refs = ref_ids - registered_items
if unregistered_refs:
    print(f"❌ 以下 {len(unregistered_refs)} 个物品在结构中被引用但未注册：")
    for item in sorted(unregistered_refs):
        print(f"   📦 {item}")
else:
    print("✅ 所有结构引用的物品都已注册！")

# ========== B) 检查创造标签页 ==========
print("\n" + "=" * 70)
print("B) 已注册物品 vs 创造标签页覆盖")
print("=" * 70)

# 转换字段名为注册ID，然后对比
field_to_regid = {v: k for k, v in fields_map.items()}
# tab_items 存的是字段名，我们要找对应关系
# 对于 tab_items 中的字段名，找到对应的注册ID
tab_registered_ids = set()
for field_name in tab_items:
    if field_name in field_to_regid:
        tab_registered_ids.add(field_to_regid[field_name])
    else:
        # 字段名可能就是注册ID（对于简单情况）
        tab_registered_ids.add(field_name)

# 现在找出已注册但不在创造标签页中的物品
missing_from_tab = registered_items - tab_registered_ids - {"meltdream_liquid"}  # 流体特殊处理
if missing_from_tab:
    print(f"⚠️ 以下 {len(missing_from_tab)} 个物品已注册但未添加到创造标签页：")
    for item in sorted(missing_from_tab):
        print(f"   🏷️ {item}")
else:
    print("✅ 所有已注册物品都已添加到创造标签页！")

# ========== C) 检查语言文件 ==========
print("\n" + "=" * 70)
print("C) 已注册物品 vs 语言文件覆盖")
print("=" * 70)

missing_zh = []
missing_en = []
for item in sorted(registered_items):
    zh_key = f"item.pasterdream.{item}"
    en_key = f"item.pasterdream.{item}"
    if zh_key not in zh_lang:
        missing_zh.append(item)
    if en_key not in en_lang:
        missing_en.append(item)

if missing_zh:
    print(f"⚠️ 以下 {len(missing_zh)} 个物品缺少中文翻译：")
    for item in missing_zh:
        print(f"   🇨🇳 {item}")
else:
    print("✅ 所有注册物品都有中文翻译！")

if missing_en:
    # 可能有些是用 snake_to_english 自动生成的，但最好还是列出来
    print(f"⚠️ 以下 {len(missing_en)} 个物品缺少英文翻译：")
    for item in missing_en:
        print(f"   🇬🇧 {item}")
else:
    print("✅ 所有注册物品都有英文翻译！")

# ========== D) 检查缺少纹理的物品（关键！） ==========
print("\n" + "=" * 70)
print("D) 物品纹理缺失检查")
print("=" * 70)

textures_item_dir = r"src/main/resources/assets/pasterdream/textures/item"
models_item_dir = r"src/main/resources/assets/pasterdream/models/item"

existing_textures = set()
if os.path.isdir(textures_item_dir):
    for f in os.listdir(textures_item_dir):
        if f.endswith(".png"):
            existing_textures.add(f[:-4])

existing_models = set()
if os.path.isdir(models_item_dir):
    for f in os.listdir(models_item_dir):
        if f.endswith(".json"):
            existing_models.add(f[:-4])

# 简单物品通常需要有 model/item/<id>.json 和 textures/item/<id>.png
# 但很多物品使用 item/generated 父模型，所以只需要纹理
# BlockItem 只需要方块纹理

missing_textures = []
for item in sorted(registered_items):
    # 跳过 BlockItem（它们用方块纹理）
    is_block_item = any(item.endswith(suffix) for suffix in [
        "_block", "_ore", "_planks", "_slab", "_stairs", "_wall", "_fence",
        "_door", "_trapdoor", "_button", "_pane", "_glass", "_ice",
        "icestone", "dyedream_log", "dyedream_wood", "dyedream_grass",
        "dyedream_dirt", "dyedream_sand", "dyedream_leaves", "cloud",
        "dyedream_lartern", "dyedream_sapling", "dyedream_crack",
        "flower_", "grass_", "pinkagaric_", "dyedream_bud_", "ice_bud_",
        "dyedream_lily", "dyedream_lotus", "dyedream_seagrass",
        "dream_accumulator", "dyedream_desk", "shadow_chest",
        "meltdream_chest", "dream_cauldron", "the_endless_book",
        "dream_train_structure", "life_crystal",
    ]) or any(item.startswith(prefix) for prefix in [
        "crop_", "dyedream_planks_", "dyedream_bud_", "dyedreamquartz_",
        "polished_", "calcite_", "carve_", "gold_", "stripped_",
        "pillar_", "bricks_", "chiseled_", "smooth_", "pinkslime",
        "dyedream_worldtree", "dyedreamdust", "dyedreamquartz",
        "amber_candy", "titanium_", "windrunner", "congeal",
        "deepslate_", "moltengold_", "blackmetal_", "charged_",
        "wind_iron_", "pebble_", "shadow_light_", "vine_", "goldenrod",
        "dream_accumulator", "life_crystal",
    ])
    
    if is_block_item:
        continue  # BlockItem 不需要单独的物品纹理
    
    if item not in existing_textures and item not in existing_models:
        # 特殊物品可能有自定义纹理路径
        # 检查是否有模型文件
        if item not in existing_models:
            missing_textures.append(item)

if missing_textures:
    print(f"⚠️ 以下 {len(missing_textures)} 个物品可能缺少纹理/模型：")
    # 只显示前20个，太多了
    for item in missing_textures[:20]:
        print(f"   🖼️ {item}")
    if len(missing_textures) > 20:
        print(f"   ... 还有 {len(missing_textures) - 20} 个")
else:
    print("✅ 所有物品纹理检查通过！")

# ========== 结果总结 ==========
print("\n" + "=" * 70)
print("📊 审计总结")
print("=" * 70)
print(f"  📦 已注册物品: {len(registered_items)}")
print(f"  🏷️ 已进创造标签: {len(tab_items)}")
print(f"  🇨🇳 中文翻译条目: {len(zh_lang)}")
print(f"  🇬🇧 英文翻译条目: {len(en_lang)}")
print(f"\n  待处理问题：")
if unregistered_refs:
    print(f"  ❌ 结构引用但未注册: {len(unregistered_refs)}")
if missing_from_tab:
    print(f"  ⚠️ 已注册但未进创造标签: {len(missing_from_tab)}")
if missing_zh:
    print(f"  ⚠️ 缺中文翻译: {len(missing_zh)}")
if missing_en:
    print(f"  ⚠️ 缺英文翻译: {len(missing_en)}")
if missing_textures:
    print(f"  ⚠️ 可能缺纹理/模型: {len(missing_textures)}")
print("=" * 70)
