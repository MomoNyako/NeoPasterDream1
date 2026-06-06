#!/usr/bin/env python3
"""
终极修复脚本：
1. 识别所有结构引用中未注册的物品
2. 生成注册代码、创造标签代码、语言文件
3. 自动补全所有遗漏
"""
import re
import os
import json
import glob
import nbtlib

# 路径
PDItems_path = r"src/main/java/com/pasterdream/pasterdreammod/registry/PDItems.java"
PDCreativeTabs_path = r"src/main/java/com/pasterdream/pasterdreammod/registry/PDCreativeTabs.java"
zh_cn_path = r"src/main/resources/assets/pasterdream/lang/zh_cn.json"
en_us_path = r"src/main/resources/assets/pasterdream/lang/en_us.json"

libs_struct_dir = r"libs/FixPasterDream-main/src/main/resources/data/pasterdream/structures"
main_struct_dir = r"src/main/resources/data/pasterdream/structures"

# ========== 1) 提取已注册物品 ==========
def extract_registered_items(java_path):
    items = set()
    with open(java_path, 'r', encoding='utf-8') as f:
        content = f.read()
    patterns = [
        r'registerSimpleItem\("([^"]+)"',
        r'registerSimpleBlockItem\("([^"]+)"',
        r'registerCustom\("([^"]+)"',
        r'\bregister\("([^"]+)"',
        r'quickItem\("([^"]+)"',
        r'quickFood\("([^"]+)"',
        r'quickTool\("([^"]+)"',
        r'quickCurio\("([^"]+)"',
        r'simpleItem\("([^"]+)"',
        r'foodItem\("([^"]+)"',
        r'toolItem\("([^"]+)"',
        r'curioItem\("([^"]+)"',
    ]
    for p in patterns:
        items.update(re.findall(p, content))
    return items

# ========== 2) 从NBT结构中提取物品引用 ==========
def extract_nbt_item_refs():
    item_refs = set()
    for base_dir in [libs_struct_dir, main_struct_dir]:
        if not os.path.isdir(base_dir):
            continue
        for fpath in glob.glob(os.path.join(base_dir, "*.nbt")):
            try:
                nbt = nbtlib.load(fpath)
                blocks_data = nbt.get("blocks", [])
                for block in blocks_data:
                    block_nbt = block.get("nbt", {})
                    if block_nbt:
                        item_id = block_nbt.get("Item", None)
                        if item_id:
                            item_refs.add(str(item_id))
                        items = block_nbt.get("Items", [])
                        for item in items:
                            item_id = item.get("id", "")
                            if item_id:
                                item_refs.add(str(item_id))
            except:
                pass
    return item_refs

# ========== 3) 从语言文件提取所有key ==========
def extract_lang_entries(lang_path):
    with open(lang_path, 'r', encoding='utf-8') as f:
        data = json.load(f)
    return data, set(data.keys())

# ========== 4) 从原模组旧语言文件获取翻译 ==========
def get_old_translations():
    """从原模组的语言文件获取所有翻译"""
    old_zh = {}
    old_en = {}
    
    old_zh_path = r"libs/FixPasterDream-main/src/main/resources/assets/pasterdream/lang/zh_cn.json"
    old_en_path = r"libs/FixPasterDream-main/src/main/resources/assets/pasterdream/lang/en_us.json"
    
    for lang_path, lang_dict in [(old_zh_path, old_zh), (old_en_path, old_en)]:
        if os.path.exists(lang_path):
            with open(lang_path, 'r', encoding='utf-8') as f:
                lang_dict.update(json.load(f))
    
    return old_zh, old_en

# ========== 主流程 ==========
print("=" * 70)
print("🛠️  PasterDream 终极修复脚本")
print("=" * 70)

# 提取数据
print("\n📥 正在分析...")
registered_items = extract_registered_items(PDItems_path)
print(f"  ✅ 已注册物品数: {len(registered_items)}")

nbt_refs = extract_nbt_item_refs()
pasterdream_refs = {r for r in nbt_refs if r.startswith("pasterdream:")}
ref_ids = {r.split(":", 1)[1] for r in pasterdream_refs}
print(f"  📦 结构文件中引用的 pasterdream 物品: {len(ref_ids)}")

# 找出未注册的物品
unregistered_refs = sorted(ref_ids - registered_items)
print(f"\n❌ 结构引用但未注册 ({len(unregistered_refs)} 个):")
for item in unregistered_refs:
    print(f"   📦 {item}")

# 获取旧翻译
old_zh, old_en = get_old_translations()
print(f"\n📖 原模组中文翻译条目: {len(old_zh)}")
print(f"📖 原模组英文翻译条目: {len(old_en)}")

# ========== 检查语言文件 ==========
zh_data, zh_keys = extract_lang_entries(zh_cn_path)
en_data, en_keys = extract_lang_entries(en_us_path)

missing_zh_items = []
missing_en_items = []
for item in sorted(registered_items):
    key = f"item.pasterdream.{item}"
    if key not in zh_keys:
        missing_zh_items.append(item)
    if key not in en_keys:
        missing_en_items.append(item)

print(f"\n📝 缺中文翻译的物品: {len(missing_zh_items)}")
print(f"📝 缺英文翻译的物品: {len(missing_en_items)}")

# ========== 检查创造标签页 ==========
with open(PDCreativeTabs_path, 'r', encoding='utf-8') as f:
    tabs_content = f.read()

# 获取所有已添加的 register id
tab_patterns = [
    r'output\.accept\(PDItems\.(\w+)\.get\(\)\)',
    r'output\.accept\(PDBlocks\.(\w+)\.get\(\)\)',
]
tab_field_names = set()
for p in tab_patterns:
    tab_field_names.update(re.findall(p, tabs_content))

# 从PDItems提取字段名到注册名的映射
field_to_regid = {}
with open(PDItems_path, 'r', encoding='utf-8') as f:
    content = f.read()
# 匹配字段声明
pattern = r'public static final DeferredItem<[^>]+>\s+(\w+)\s*=\s*(?:ITEMS\.register\("([^"]+)"|ItemMigrationAPI\.\w+Item\("([^"]+)"|ITEMS\.registerSimpleItem\("([^"]+)"|ITEMS\.registerSimpleBlockItem\("([^"]+)")'
for match in re.finditer(pattern, content):
    field = match.group(1)
    reg_id = match.group(2) or match.group(3) or match.group(4) or match.group(5)
    field_to_regid[field] = reg_id

# 找出未添加到标签页的物品
tab_reg_ids = set(field_to_regid.values())
# 还有从PDBlocks添加的
block_field_pattern = r'output\.accept\(PDBlocks\.(\w+)\.get\(\)\)'
for match in re.finditer(block_field_pattern, tabs_content):
    block_field = match.group(1)
    # 检查PDBlocks的对应注册名 - 暂不处理

# 找出已注册但未在标签页中的物品
# (需要也检查lang key里的block item)
all_tab_keys = set()
for field, reg_id in field_to_regid.items():
    if field in tab_field_names:
        all_tab_keys.add(reg_id)

# 检查那些注册了但不在tab里的物品（排除流体和特殊物品）
missing_tab = []
for item in sorted(registered_items):
    if item not in all_tab_keys and item not in ["meltdream_liquid"]:
        missing_tab.append(item)

print(f"\n🏷️ 已注册但未进创造标签: {len(missing_tab)}")
for item in missing_tab[:10]:
    print(f"   🏷️ {item}")
if len(missing_tab) > 10:
    print(f"   ... 还有 {len(missing_tab) - 10} 个")

# ========== 用户要处理的内容 ==========
print("\n" + "=" * 70)
print("📋 待处理清单")
print("=" * 70)

print(f"\n1️⃣  注册 {len(unregistered_refs)} 个新物品（结构引用）")
for item in unregistered_refs:
    print(f"   ➕ {item}")

print(f"\n2️⃣  补全 {len(missing_zh_items)} 个中文翻译")
print(f"\n3️⃣  补全 {len(missing_en_items)} 个英文翻译")
print(f"\n4️⃣  补全 {len(missing_tab)} 个创造标签条目")

# ========== 生成注册代码 ==========
print("\n" + "=" * 70)
print("🔧 生成注册代码")
print("=" * 70)

for item in unregistered_refs:
    # 查找旧翻译
    old_zh_name = old_zh.get(f"item.pasterdream.{item}", old_zh.get(f"block.pasterdream.{item}", ""))
    old_en_name = old_en.get(f"item.pasterdream.{item}", old_en.get(f"block.pasterdream.{item}", ""))
    
    print(f"\n// {item} ({old_zh_name or '未知中文名'} / {old_en_name or 'Unknown'})")
    print(f'public static final DeferredItem<Item> {item.upper()} = ITEMS.registerSimpleItem("{item}", new Item.Properties());')

# ========== 生成语言文件条目 ==========
print("\n" + "=" * 70)
print("🔧 生成缺失的语言文件条目")
print("=" * 70)

# 需要生成语言条目的所有物品（已注册 + 新注册的）
all_items_needing_lang = sorted(set(registered_items) | set(unregistered_refs))

zh_additions = {}
en_additions = {}

for item in all_items_needing_lang:
    item_key = f"item.pasterdream.{item}"
    
    if item_key not in zh_keys:
        # 从原模组翻译找
        old_trans = old_zh.get(item_key, old_zh.get(f"block.pasterdream.{item}", ""))
        if old_trans:
            zh_additions[item_key] = old_trans
        else:
            # 如果方块有block.翻译，用那个
            block_key = f"block.pasterdream.{item}"
            if block_key in old_zh:
                zh_additions[item_key] = old_zh[block_key]
            else:
                zh_additions[item_key] = item.replace("_", " ").title()
    
    if item_key not in en_keys:
        old_trans = old_en.get(item_key, old_en.get(f"block.pasterdream.{item}", ""))
        if old_trans:
            en_additions[item_key] = old_trans
        else:
            block_key = f"block.pasterdream.{item}"
            if block_key in old_en:
                en_additions[item_key] = old_en[block_key]
            else:
                en_additions[item_key] = " ".join(w.capitalize() for w in item.replace("_", " ").split())

if zh_additions:
    print(f"\n🇨🇳 需要添加 {len(zh_additions)} 个中文翻译:")
    for k, v in zh_additions.items():
        print(f'   "{k}": "{v}",')

if en_additions:
    print(f"\n🇬🇧 需要添加 {len(en_additions)} 个英文翻译:")
    for k, v in en_additions.items():
        print(f'   "{k}": "{v}",')

# ========== 生成创造标签代码 ==========
print("\n" + "=" * 70)
print("🔧 生成创造标签页添加代码")
print("=" * 70)

# 需要添加的字段名
fields_to_add = []
for item in missing_tab:
    for field, reg_id in field_to_regid.items():
        if reg_id == item:
            fields_to_add.append(field)
            break
    else:
        # 也可能是BlockItem
        fields_to_add.append(item.upper())

if fields_to_add:
    print(f"\n🏷️ 需要添加 {len(fields_to_add)} 个到创造标签页:")
    for field in sorted(fields_to_add):
        print(f'                        output.accept(PDItems.{field}.get());')

print("\n" + "=" * 70)
print("✅ 分析完成！已将修复方案产出如上")
print("=" * 70)
