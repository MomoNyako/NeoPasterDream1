"""
修复唱片纹理问题：
1. 修复4个群系唱片物品模型的纹理引用（当前错误地引用了sweetdream/snowfalldream）
2. 生成5个缺失的唱片纹理（用不同颜色区分）
3. 创建 dyedream_world 的 jukebox_song JSON
4. 创建 dyedream_world_disc.json 物品模型
"""
import json
from pathlib import Path

# 路径
ASSETS = Path(r"C:\Users\97128\Documents\GitHub\NeoPasterDream1\src\main\resources\assets\pasterdream")
DATA = Path(r"C:\Users\97128\Documents\GitHub\NeoPasterDream1\src\main\resources\data\pasterdream")

MODELS_ITEM = ASSETS / "models" / "item"
TEXTURES_ITEM = ASSETS / "textures" / "item"
JUKEBOX_SONG = DATA / "jukebox_song"

# ============================================================
# 1. 修复4个群系唱片模型的纹理引用
# ============================================================
print("=" * 60)
print("🎵 1. 修复群系唱片物品模型的纹理引用")
print("=" * 60)

FIXED_MODELS = {
    "dream_meadow_disc.json": "pasterdream:item/music_disc_dream_meadow",
    "dream_heath_disc.json": "pasterdream:item/music_disc_dream_heath",
    "dream_taiga_disc.json": "pasterdream:item/music_disc_dream_taiga",
    "dream_delta_disc.json": "pasterdream:item/music_disc_dream_delta",
}

for filename, texture in FIXED_MODELS.items():
    filepath = MODELS_ITEM / filename
    model = {
        "parent": "item/generated",
        "textures": {
            "layer0": texture
        }
    }
    with open(filepath, "w", encoding="utf-8") as f:
        json.dump(model, f, indent=2)
    print(f"  ✅ 修复 {filename} → {texture}")

# ============================================================
# 2. 生成5个缺失的唱片纹理（不同颜色方案）
# ============================================================
print("\n" + "=" * 60)
print("🎨 2. 生成缺失的唱片纹理")
print("=" * 60)

DISC_COLORS = {
    "music_disc_dream_meadow.png": {
        "base": (100, 200, 100),      # 草绿色 - 梦幻草原
        "stripe": (60, 160, 60),
        "label": (80, 180, 80),
        "center": (200, 255, 200),
    },
    "music_disc_dream_heath.png": {
        "base": (200, 180, 100),      # 土黄色 - 梦幻荒原
        "stripe": (160, 140, 60),
        "label": (180, 160, 80),
        "center": (255, 230, 180),
    },
    "music_disc_dream_taiga.png": {
        "base": (100, 160, 200),      # 冰雪蓝 - 梦幻雪林
        "stripe": (60, 120, 180),
        "label": (80, 140, 200),
        "center": (200, 230, 255),
    },
    "music_disc_dream_delta.png": {
        "base": (180, 140, 200),      # 淡紫色 - 梦幻三角洲
        "stripe": (140, 100, 180),
        "label": (160, 120, 200),
        "center": (230, 210, 255),
    },
    "music_disc_dyedream_world.png": {
        "base": (200, 120, 200),      # 粉紫色 - 染梦世界
        "stripe": (160, 80, 160),
        "label": (180, 100, 180),
        "center": (255, 200, 255),
    },
}

try:
    from PIL import Image, ImageDraw

    for filename, colors in DISC_COLORS.items():
        filepath = TEXTURES_ITEM / filename
        if filepath.exists():
            print(f"  ⏩ {filename} 已存在，跳过")
            continue

        # 创建16x16的唱片纹理
        img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
        draw = ImageDraw.Draw(img)

        base = colors["base"]
        stripe = colors["stripe"]
        label = colors["label"]
        center = colors["center"]

        # 唱片主体 - 圆形带阴影
        for y in range(16):
            for x in range(16):
                dx, dy = x - 7.5, y - 7.5
                dist = (dx*dx + dy*dy) ** 0.5
                if dist <= 7.5:
                    # 边缘渐变
                    if dist > 6.5:
                        c = tuple(int(base[i] * 0.7) for i in range(3))
                    elif dist > 5.5:
                        c = base
                    elif dist > 3.5:
                        c = label
                    elif dist > 2.0:
                        c = stripe
                    else:
                        c = center
                    img.putpixel((x, y), (*c, 255))
                elif dist <= 8.0:
                    # 半透明边缘光晕
                    alpha = int(255 * (1 - (dist - 7.5) / 0.5))
                    img.putpixel((x, y), (*base, alpha))

        # 中心孔
        for y in range(16):
            for x in range(16):
                dx, dy = x - 7.5, y - 7.5
                dist = (dx*dx + dy*dy) ** 0.5
                if dist <= 1.2:
                    img.putpixel((x, y), (0, 0, 0, 0))

        # 纹理条纹
        for i in range(3):
            angle = i * 1.047  # 60度
            for y in range(4, 12):
                for x in range(4, 12):
                    dx, dy = x - 7.5, y - 7.5
                    # 旋转坐标
                    rx = dx * 0.866 + dy * 0.5  # cos(60), sin(60)
                    if abs(rx) < 0.5 and 3.5 < (dx*dx + dy*dy) ** 0.5 < 6.5:
                        r, g, b, a = img.getpixel((x, y))
                        img.putpixel((x, y), (max(0, r-30), max(0, g-30), max(0, b-30), a))

        img.save(filepath)
        print(f"  ✅ 生成 {filename}（颜色: {base}）")

except ImportError:
    print("  ⚠️ PIL 未安装，生成简化纹理...")
    # 生成简化纹理
    for filename, colors in DISC_COLORS.items():
        filepath = TEXTURES_ITEM / filename
        if filepath.exists():
            print(f"  ⏩ {filename} 已存在，跳过")
            continue

        import struct
        import zlib

        def create_png(width, height, r, g, b):
            raw = b""
            for y in range(height):
                raw += b"\x00"
                for x in range(width):
                    dx, dy = x - 7.5, y - 7.5
                    dist = (dx*dx + dy*dy) ** 0.5
                    if dist <= 7.5:
                        raw += struct.pack("BBBB", r, g, b, 255)
                    else:
                        raw += struct.pack("BBBB", 0, 0, 0, 0)
            def chunk(chunk_type, data):
                c = chunk_type + data
                return struct.pack(">I", len(data)) + c + struct.pack(">I", zlib.crc32(c) & 0xFFFFFFFF)
            ihdr = struct.pack(">IIBBBBB", width, height, 8, 6, 0, 0, 0)
            return (b"\x89PNG\r\n\x1a\n"
                    + chunk(b"IHDR", ihdr)
                    + chunk(b"IDAT", zlib.compress(raw))
                    + chunk(b"IEND", b""))

        base = colors["base"]
        data = create_png(16, 16, *base)
        with open(filepath, "wb") as f:
            f.write(data)
        print(f"  ✅ 生成简化纹理 {filename}")

# ============================================================
# 3. 创建 dyedream_world 的 jukebox_song JSON
# ============================================================
print("\n" + "=" * 60)
print("📀 3. 创建 dyedream_world jukebox_song 数据文件")
print("=" * 60)

dyedream_song = {
    "sound_event": {
        "sound_id": "pasterdream:music.dyedream_world"
    },
    "description": "PasterDream - DyeDream World",
    "length_in_seconds": 120.0,
    "comparator_output": 8
}

song_path = JUKEBOX_SONG / "dyedream_world.json"
JUKEBOX_SONG.mkdir(parents=True, exist_ok=True)
with open(song_path, "w", encoding="utf-8") as f:
    json.dump(dyedream_song, f, indent=2)
print(f"  ✅ 创建 {song_path}")
print(f"      sound_id: pasterdream:music.dyedream_world")
print(f"      description: PasterDream - DyeDream World")

# ============================================================
# 4. 创建 dyedream_world_disc.json 物品模型
# ============================================================
print("\n" + "=" * 60)
print("💿 4. 创建 dyedream_world_disc.json 物品模型")
print("=" * 60)

disc_model = {
    "parent": "item/generated",
    "textures": {
        "layer0": "pasterdream:item/music_disc_dyedream_world"
    }
}

model_path = MODELS_ITEM / "dyedream_world_disc.json"
with open(model_path, "w", encoding="utf-8") as f:
    json.dump(disc_model, f, indent=2)
print(f"  ✅ 创建 {model_path}")

print("\n" + "=" * 60)
print("🎉 全部完成！")
print("=" * 60)