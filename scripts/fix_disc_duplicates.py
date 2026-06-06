"""
修复唱片音频文件和描述重复问题
"""
import shutil
from pathlib import Path

OLD = Path(r"C:\Users\97128\Documents\GitHub\NeoPasterDream1\libs\FixPasterDream-main\src\main\resources\assets\pasterdream\sounds")
NEW = Path(r"C:\Users\97128\Documents\GitHub\NeoPasterDream1\src\main\resources\assets\pasterdream\sounds")
DATA = Path(r"C:\Users\97128\Documents\GitHub\NeoPasterDream1\src\main\resources\data\pasterdream\jukebox_song")
MUSIC_DIR = NEW / "music"

print("=" * 60)
print("🔍 诊断报告")
print("=" * 60)

# 检查新旧文件的正确版本
files_to_check = [
    ("sweetdream_music.ogg", "旧模组版本"),
    ("snowfall_dream_music.ogg", "旧模组版本"),
]

old_sizes = {
    "sweetdream_music.ogg": (OLD / "sweetdream_music.ogg").stat().st_size,
    "snowfall_dream_music.ogg": (OLD / "snowfall_dream_music.ogg").stat().st_size,
}
new_sizes = {
    "sweetdream_music.ogg": (NEW / "sweetdream_music.ogg").stat().st_size,
    "snowfall_dream_music.ogg": (NEW / "snowfall_dream_music.ogg").stat().st_size,
}

print(f"\n旧模版 sweetdream_music.ogg = {old_sizes['sweetdream_music.ogg']:,} B")
print(f"新模版 sweetdream_music.ogg = {new_sizes['sweetdream_music.ogg']:,} B  ← ❌ 错误！")
print(f"新模版 music/dyedream_world.ogg = {(MUSIC_DIR / 'dyedream_world.ogg').stat().st_size:,} B  ← 这个是旧的 sweetdream！")

print(f"\n旧模版 snowfall_dream_music.ogg = {old_sizes['snowfall_dream_music.ogg']:,} B")
print(f"新模版 snowfall_dream_music.ogg = {new_sizes['snowfall_dream_music.ogg']:,} B  ← ❌ 错误！")

print("\n" + "=" * 60)
print("📦 1. 恢复正确的音频文件")
print("=" * 60)

# 从旧模组复制正确的文件
for filename in ["sweetdream_music.ogg", "snowfall_dream_music.ogg"]:
    src = OLD / filename
    dst = NEW / filename
    shutil.copy2(src, dst)
    print(f"  ✅ 恢复 {filename} ({src.stat().st_size:,} B)")

print("\n" + "=" * 60)
print("📝 2. 修复重复的描述")
print("=" * 60)

import json

# 更新各唱片的描述，确保每个都唯一
descriptions = {
    "sweetdream.json": "PasterDream - Sweet Dream",
    "snowfalldream.json": "PasterDream - Snowfall Dream",
    "aaroncos.json": "PasterDream - 亚伦柯斯之触",
    "wind_journey.json": "PasterDream - Wind Journey",
    "dream_meadow.json": "PasterDream - 梦幻草原",
    "dream_heath.json": "PasterDream - 梦幻荒原",
    "dream_taiga.json": "PasterDream - 梦幻雪林",
    "dream_delta.json": "PasterDream - 梦幻三角洲",
    "dyedream_world.json": "PasterDream - DyeDream World",
}

for filename, desc in descriptions.items():
    filepath = DATA / filename
    with open(filepath, "r", encoding="utf-8") as f:
        data = json.load(f)
    data["description"] = desc
    with open(filepath, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=2, ensure_ascii=False)
    print(f"  ✅ {filename} → \"{desc}\"")

print("\n" + "=" * 60)
print("🎉 全部修复完成！")
print("=" * 60)