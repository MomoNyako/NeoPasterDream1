"""
对比 PDItems.java / PDBlocks.java 的注册名与 zh_cn.json 的翻译键，
找出缺失的翻译条目。
"""
import re, json, sys

# 1. 读取 zh_cn.json
with open(r"src/main/resources/assets/pasterdream/lang/zh_cn.json", "r", encoding="utf-8") as f:
    lang = json.load(f)

# 2. 从 PDItems.java 提取所有注册名
with open(r"src/main/java/com/pasterdream/pasterdreammod/registry/PDItems.java", "r", encoding="utf-8") as f:
    items_text = f.read()

# 匹配 register("xxx")、registerSimpleItem("xxx")、registerSimpleBlockItem("xxx") 等
item_names = set()
for m in re.finditer(r'register(?:SimpleItem|SimpleBlockItem|Item|Custom)?\s*\(\s*"([^"]+)"', items_text):
    item_names.add(m.group(1))

# 匹配 ItemMigrationAPI.xxx("xxx")
for m in re.finditer(r'ItemMigrationAPI\.\w+\(?\s*"([^"]+)"', items_text):
    item_names.add(m.group(1))

# 3. 从 PDBlocks.java 提取所有方块注册名（只关注非物品的 block 键）
with open(r"src/main/java/com/pasterdream/pasterdreammod/registry/PDBlocks.java", "r", encoding="utf-8") as f:
    blocks_text = f.read()

block_names = set()
for m in re.finditer(r'register(?:Block)?\s*\(\s*"([^"]+)"', blocks_text):
    block_names.add(m.group(1))
# 也匹配 add("xxx" 和 addCustom("xxx" (SimpleBlockBuilder)
for m in re.finditer(r'\.add(?:Custom)?\s*\(\s*"([^"]+)"', blocks_text):
    block_names.add(m.group(1))
# putConfig("xxx"
for m in re.finditer(r'putConfig\s*\(\s*"([^"]+)"', blocks_text):
    block_names.add(m.group(1))

print(f"=== 统计 ===")
print(f"zh_cn.json 中现有翻译键: {len(lang)} 个")
print(f"PDItems.java 中提取的注册名: {len(item_names)} 个")
print(f"PDBlocks.java 中提取的方块名: {len(block_names)} 个")

# 4. 检查物品翻译缺失
missing_items = []
for name in sorted(item_names):
    key = f"item.pasterdream.{name}"
    if key not in lang:
        missing_items.append((name, key))

# 5. 检查方块翻译缺失
missing_blocks = []
for name in sorted(block_names):
    key = f"block.pasterdream.{name}"
    if key not in lang:
        missing_blocks.append((name, key))

print(f"\n=== 缺失的 item 翻译 ({len(missing_items)} 个) ===")
for name, key in missing_items:
    print(f"  {key}")

print(f"\n=== 缺失的 block 翻译 ({len(missing_blocks)} 个) ===")
for name, key in missing_blocks:
    print(f"  {key}")

# 6. 检查是否有多余的翻译键（不在任何注册中）
all_registered = item_names | block_names
# 还要加上其他类型的键（effect, container, subtitle, fluid_type, message, itemGroup等）
extra_keys = []
for key in lang:
    parts = key.split(".", 2)
    if len(parts) >= 3:
        reg_name = parts[2]
        if parts[0] == "item" and reg_name not in item_names:
            extra_keys.append(key)
        elif parts[0] == "block" and reg_name not in block_names:
            extra_keys.append(key)

if extra_keys:
    print(f"\n=== 可能多余的翻译键（注册表中找不到对应）({len(extra_keys)} 个) ===")
    for k in extra_keys:
        print(f"  {k}")

print("\n✅ 分析完成")