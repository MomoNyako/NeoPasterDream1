import shutil, os, json

base = r"C:\Users\97128\Documents\GitHub\NeoPasterDream1"
src_textures = os.path.join(base, r"libs\FixPasterDream-main\src\main\resources\assets\pasterdream\textures\item")
dst_textures = os.path.join(base, r"src\main\resources\assets\pasterdream\textures\item")
dst_models = os.path.join(base, r"src\main\resources\assets\pasterdream\models\item")

# Mapping: English name -> source pinyin filename
items = {
    "titanium_pickaxe": "tai_hao_",
    "dyedream_hammer": "ran_meng_he_jin_chui_",
    "moltengold_sword": "zhi_yan_jin_jian_",
    "true_tide_sword": "yin_chao_jian_",
    "true_moltengold_pickaxe": "yu_yan_hao_",
    "desert_sword": "shuo_mo_da_jian_",
    "dyedream_pickaxe": "ran_meng_he_jin_hao_",
    "moltengold_pickaxe": "zhi_yan_jin_hao_",
    "iceshadow_hammer": "bing_ying_zhan_chui_",
    "tide_sword": "yin_chao_jian_",
    "true_moltengold_sword": "yu_yan_jian_",
    "creative_sword": "diao_shi_zhi_jian_",
    "true_grass_sword": "cao_ti_",
    "truest_moltengold_sword": "yu_yan_jian_true",
    "shadow_erosion_pickaxe": "ying_shi_hao_",
    "shadow_erosion_sword": "ying_shi_bi_shou_",
    "titanium_sword": "tai_jian_",
    "dyedream_sword_0": "ji_feng_ran_meng_he_jin_jian_",
    "true_desert_sword": "shuo_mo_da_jian_",
    "dyedream_sword": "ran_meng_he_jin_jian_",
    "thermal_dagger": "re_neng_bi_shou_",
    "shadow_sword": "ying_ren_",
    "copper_pickaxe": "tong_hao_",
    "terra_sword": "da_di_zhi_ren_",
    "copper_sword": "tong_jian_",
    "meltdream_pickaxe": "rong_meng_shui_jing_hao_",
    "white_sword": "bai_e_jian_",
    "grass_sword": "cao_ti_",
    "broken_hero_sword": "duan_lie_ying_xiong_jian_",
}

# Check source directory
if not os.path.exists(src_textures):
    print(f"[ERROR] Source directory not found: {src_textures}")
    exit(1)

for eng, pinyin in items.items():
    src_png = os.path.join(src_textures, pinyin + ".png")
    dst_png = os.path.join(dst_textures, eng + ".png")
    dst_json = os.path.join(dst_models, eng + ".json")

    # Copy texture
    if os.path.exists(src_png):
        shutil.copy2(src_png, dst_png)
        print(f"[OK] Copied texture: {pinyin}.png -> {eng}.png")
    else:
        print(f"[WARN] Source texture not found: {src_png}")

    # Create item model JSON
    if not os.path.exists(dst_json):
        model = {
            "parent": "item/handheld",
            "textures": {
                "layer0": f"pasterdream:item/{eng}"
            }
        }
        with open(dst_json, "w", encoding="utf-8") as f:
            json.dump(model, f, indent=2)
        print(f"[OK] Created model: {eng}.json")
    else:
        print(f"[SKIP] Model already exists: {eng}.json")

total = len(items)
print(f"\n[DONE] Processed {total} items.")
print(f"       Textures copied to: {dst_textures}")
print(f"       Models created at: {dst_models}")