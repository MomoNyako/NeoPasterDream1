#!/usr/bin/env python3
import gzip
import os
import re
from collections import OrderedDict

STRUCTURE_DIR = r"src/main/resources/data/pasterdream/structures"
TARGET_FILES = [
    "dyedream_worldtree_true.nbt",
    "dream_train.nbt",
    "dream_train_platform.nbt",
    "pinkagaric_house_3.nbt",
    "pinkagaric_house_0.nbt",
    "pinkagaric_house_1.nbt",
    "pinkagaric_house_2.nbt",
]

def decompress_nbt(filepath):
    with gzip.open(filepath, 'rb') as f:
        return f.read()

def extract_pasterdream_blocks(data):
    pattern = rb'pasterdream:[a-z0-9_/]+'
    matches = re.findall(pattern, data)
    decoded = [m.decode('utf-8') for m in matches]
    seen = OrderedDict()
    for item in decoded:
        seen[item] = None
    return list(seen.keys())

def main():
    all_pasterdream_blocks = OrderedDict()
    file_results = {}

    for filename in TARGET_FILES:
        filepath = os.path.join(STRUCTURE_DIR, filename)
        if not os.path.exists(filepath):
            continue
        try:
            data = decompress_nbt(filepath)
            blocks = extract_pasterdream_blocks(data)
            file_results[filename] = blocks
            for b in blocks:
                all_pasterdream_blocks[b] = None
        except Exception as e:
            print(f"[ERROR] {filename}: {e}")

    all_blocks = list(all_pasterdream_blocks.keys())
    print(f"Total unique pasterdream block references: {len(all_blocks)}")
    print()
    for i, block in enumerate(all_blocks, 1):
        appeared_in = [f for f, b in file_results.items() if block in b]
        print(f"  {i}. {block}")

    print("\n--- Block Registration Check ---")
    registered = set()
    for filename, blocks in file_results.items():
        for b in blocks:
            registered.add(b)
    
    # Check if all are registered - read PDBlocks for actual registrations
    # From our search, the registered pasterdream blocks include:
    registered_blocks = {
        "pasterdream:dream_accumulator", "pasterdream:dyedream_desk",
        "pasterdream:dream_train_structure", "pasterdream:life_crystal",
        "pasterdream:shadow_chest", "pasterdream:the_endless_book_of_dream_seekers",
        "pasterdream:dream_cauldron", "pasterdream:meltdream_chest",
        "pasterdream:meltdream_chest_open", "pasterdream:meltdream_liquid",
        "pasterdream:dyedream_dirt", "pasterdream:dyedream_sand",
        "pasterdream:dyedream_planks", "pasterdream:dyedream_glass",
        "pasterdream:dyedream_ice", "pasterdream:dyedream_packed_ice",
        "pasterdream:pinkslime_block", "pasterdream:dyedream_block",
        "pasterdream:dyedreamquartz_block", "pasterdream:smooth_dyedreamquartz_block",
        "pasterdream:bricks_dyedreamquartz_block", "pasterdream:chiseled_dyedreamquartz_block",
        "pasterdream:dyedream_bud_block", "pasterdream:icestone",
        "pasterdream:dyedream_worldtree_leaves", "pasterdream:dyedreamquartz_ore",
        "pasterdream:dyedreamdust_ore", "pasterdream:amber_candy_ore",
        "pasterdream:titanium_ore", "pasterdream:windrunner_crystal_ore",
        "pasterdream:congeal_wind_ore", "pasterdream:carve_dyedream_glass",
        "pasterdream:gold_carve_dyedream_glass", "pasterdream:polished_calcite",
        "pasterdream:calcite_tiles",
        "pasterdream:dyedream_leaves", "pasterdream:dyedream_grass",
        "pasterdream:dyedream_log", "pasterdream:dyedream_wood",
        "pasterdream:stripped_dyedream_log", "pasterdream:stripped_dyedream_wood",
        "pasterdream:pillar_dyedreamquartz_block",
        "pasterdream:dyedream_planks_stairs", "pasterdream:dyedream_planks_slab",
        "pasterdream:dyedream_planks_fence", "pasterdream:dyedream_planks_fencegate",
        "pasterdream:dyedream_planks_door", "pasterdream:dyedream_planks_trapdoor",
        "pasterdream:dyedream_planks_pressure_plate", "pasterdream:dyedream_planks_button",
        "pasterdream:dyedream_bud_stairs", "pasterdream:dyedream_bud_slab",
        "pasterdream:dyedream_bud_wall", "pasterdream:dyedreamquartz_block_stairs",
        "pasterdream:dyedreamquartz_block_slab", "pasterdream:dyedreamquartz_block_wall",
        "pasterdream:calcite_tiles_stairs", "pasterdream:calcite_tiles_slab",
        "pasterdream:polished_calcite_slab", "pasterdream:polished_calcite_wall",
        "pasterdream:calcite_tiles_wall", "pasterdream:polished_calcite_stairs",
        "pasterdream:dyedream_glasspane", "pasterdream:carve_dyedream_glasspane",
        "pasterdream:gold_carve_dyedream_glasspane", "pasterdream:dyedream_lartern",
        "pasterdream:dyedream_planks_pane",
        "pasterdream:pinkagaric_0", "pasterdream:pinkagaric_1",
        "pasterdream:pinkagaric_2", "pasterdream:pinkagaric_3",
        "pasterdream:dyedream_bud_0", "pasterdream:dyedream_bud_1",
        "pasterdream:dyedream_bud_2", "pasterdream:ice_bud_0",
        "pasterdream:dyedream_lily_pad", "pasterdream:dyedream_lotus",
        "pasterdream:dyedream_seagrass",
        "pasterdream:dyedream_sapling", "pasterdream:dyedream_crack",
        "pasterdream:cloud", "pasterdream:dark_cloud", "pasterdream:thick_cloud",
        # 我们把 recent 注册的也加上
        "pasterdream:titanium_block", "pasterdream:raw_titanium_block",
        "pasterdream:moltengold_block", "pasterdream:blackmetal_block",
        "pasterdream:charged_amethyst_block", "pasterdream:wind_iron_block",
        "pasterdream:deepslate_titanium_ore", "pasterdream:moltengold_ore",
        "pasterdream:soul_ore",
        "pasterdream:pebble_0", "pasterdream:shadow_light_0", "pasterdream:vine_0",
        "pasterdream:goldenrod", "pasterdream:crop_0a", "pasterdream:crop_1a",
        "pasterdream:crop_2a", "pasterdream:crop_3a", "pasterdream:crop_4a",
    }

    unregistered = [b for b in all_blocks if b not in registered_blocks]
    if unregistered:
        print("\n[!!] UNREGISTERED BLOCKS (will show as purple-black):")
        for b in unregistered:
            print(f"  X {b}")
    else:
        print("\n[OK] All referenced blocks are registered!")

    print(f"\nTotal NBT block references found: {len(all_blocks)}")
    print(f"Files analyzed: {len(file_results)}")

if __name__ == "__main__":
    main()
