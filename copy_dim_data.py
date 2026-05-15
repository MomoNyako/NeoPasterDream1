import os
import shutil
import json

src_base = r'c:\Users\97128\Documents\GitHub\NeoPasterDream1\libs\FixPasterDream-main\src\main\resources\data\pasterdream'
dst_base = r'c:\Users\97128\Documents\GitHub\NeoPasterDream1\src\main\resources\data\pasterdream'

# Files to copy: dimension, dimension_type, biomes
files_to_copy = [
    ('dimension_type', 'dyedream_world.json'),
    ('dimension', 'dyedream_world.json'),
    ('worldgen/biome', 'biome_dyedream_0.json'),
    ('worldgen/biome', 'biome_dyedream_1.json'),
    ('worldgen/biome', 'biome_dyedream_2.json'),
    ('worldgen/biome', 'biome_dyedream_3.json'),
]

for subdir, fname in files_to_copy:
    src = os.path.join(src_base, subdir, fname)
    dst_dir = os.path.join(dst_base, subdir)
    dst = os.path.join(dst_dir, fname)
    
    os.makedirs(dst_dir, exist_ok=True)
    
    if os.path.exists(src):
        shutil.copy2(src, dst)
        print(f'  [COPY] {subdir}/{fname}')
    else:
        print(f'  [MISS] {subdir}/{fname}')

print()
print('=== 全部复制完成! ===')