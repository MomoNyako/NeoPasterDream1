"""
批量复制原模组资源文件到新项目
包括：纹理、模型、动画、声音、blockstates、displaysettings
"""
import os, shutil

SRC = r"libs\FixPasterDream-main\src\main\resources\assets\pasterdream"
DST = r"src\main\resources\assets\pasterdream"

copied = []

def copy_file(src_rel):
    """复制相对路径的文件"""
    src_path = os.path.join(SRC, src_rel)
    dst_path = os.path.join(DST, src_rel)
    os.makedirs(os.path.dirname(dst_path), exist_ok=True)
    if os.path.exists(src_path):
        shutil.copy2(src_path, dst_path)
        copied.append(src_rel)
        print(f"  ✅ {src_rel}")
    else:
        print(f"  ❌ MISSING: {src_rel}")

# ==================== meltdream_liquid (流体) ====================
print("=== meltdream_liquid 流体纹理 ===")
for f in ["meltdream_liquid_still.png", "meltdream_liquid_still.png.mcmeta",
           "meltdream_liquid_flowing.png", "meltdream_liquid_flowing.png.mcmeta"]:
    copy_file(f"textures/block/{f}")

print("=== meltdream_liquid blockstate/model ===")
copy_file("blockstates/meltdream_liquid.json")
copy_file("models/block/meltdream_liquid.json")
copy_file("models/item/meltdream_liquid_bucket.json")

# ==================== Meltdream Chest (水晶箱) ====================
print("=== meltdream_chest 纹理 ===")
for f in ["meltdream_chest_0.png"]:
    copy_file(f"textures/block/{f}")

print("=== meltdream_chest GUI 纹理 ===")
copy_file("textures/screens/meltdream_chest_0.png")

print("=== meltdream_chest Geo模型 ===")
for f in ["meltdream_chest_0.geo.json", "meltdream_chest_1.geo.json"]:
    copy_file(f"geo/{f}")

print("=== meltdream_chest 动画 ===")
for f in ["meltdream_chest_0.animation.json", "meltdream_chest_1.animation.json"]:
    copy_file(f"animations/{f}")

print("=== meltdream_chest blockstate/model ===")
for f in ["meltdream_chest.json", "meltdream_chest_open.json"]:
    copy_file(f"blockstates/{f}")
for f in ["meltdream_chest_0.json", "meltdream_chest_1.json", "meltdream_chest_particle.json", "meltdream_chest_open_particle.json"]:
    copy_file(f"models/custom/{f}")
for f in ["meltdream_chest.json", "meltdream_chest_open.json"]:
    copy_file(f"models/item/{f}")
for f in ["meltdream_chest_0.item.json", "meltdream_chest_1.item.json"]:
    copy_file(f"models/displaysettings/{f}")

print("=== meltdream_chest 声音 ===")
os.makedirs(os.path.join(DST, "sounds"), exist_ok=True)
for f in ["meltdream_chest.ogg", "meltdream_chest_0.ogg"]:
    copy_file(f"sounds/{f}")

# ==================== The Endless Book (永恒书卷) ====================
print("=== the_endless_book 纹理 ===")
copy_file("textures/block/the_endless_book_of_dream_seekers.png")
copy_file("textures/screens/the_endless_book_of_dream_seekers_gui.png")

print("=== the_endless_book Geo/动画 ===")
copy_file("geo/the_endless_book_of_dream_seekers.geo.json")
copy_file("animations/the_endless_book_of_dream_seekers.animation.json")

print("=== the_endless_book blockstate/model ===")
copy_file("blockstates/the_endless_book_of_dream_seekers.json")
copy_file("models/item/the_endless_book_of_dream_seekers.json")
copy_file("models/custom/the_endless_book_of_dream_seekers_particle.json")
copy_file("models/displaysettings/the_endless_book_of_dream_seekers.item.json")

# ==================== Dream Cauldron (炼药锅) ====================
print("=== dream_cauldron 纹理 ===")
for f in ["dream_cauldron.png"]:
    copy_file(f"textures/block/{f}")
for f in ["dream_cauldron_gui.png", "dream_cauldron_gui_jei.png",
          "dream_cauldron_gui_button0.png", "dream_cauldron_gui_button1.png"]:
    copy_file(f"textures/screens/{f}")
copy_file("textures/screens/atlas/imagebutton_dream_cauldron_gui_button0.png")

print("=== dream_cauldron Geo/动画 ===")
copy_file("geo/dream_cauldron.geo.json")
copy_file("animations/dream_cauldron.animation.json")

print("=== dream_cauldron blockstate/model ===")
copy_file("blockstates/dream_cauldron.json")
copy_file("models/item/dream_cauldron.json")
copy_file("models/custom/dream_cauldron_particle.json")
copy_file("models/displaysettings/dream_cauldron.item.json")

# ==================== Dream Train Structure (列车结构) ====================
print("=== dream_train_structure blockstate/model ===")
copy_file("blockstates/dream_train_structure.json")
copy_file("models/block/dream_train_structure.json")
copy_file("models/item/dream_train_structure.json")

print(f"\n🎉 全部完成！共复制 {len(copied)} 个文件")
