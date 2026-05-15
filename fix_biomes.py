import os, json

biome_dir = r'c:\Users\97128\Documents\GitHub\NeoPasterDream1\src\main\resources\data\pasterdream\worldgen\biome'

# 修复所有 4 个生物群系的 JSON
for i in range(4):
    fpath = os.path.join(biome_dir, f'biome_dyedream_{i}.json')
    if not os.path.exists(fpath):
        print(f'  [MISS] biome_dyedream_{i}.json')
        continue
    
    with open(fpath, 'r', encoding='utf-8') as f:
        data = json.load(f)
    
    # 1. 移除 carvers（MC 1.21.1 不再支持生物群系级别的 carvers）
    if 'carvers' in data:
        del data['carvers']
        print(f'  [FIX {i}] 移除 carvers')
    
    # 2. 移除 features 中不存在的引用
    if 'features' in data:
        new_features = []
        for stage in data['features']:
            if isinstance(stage, list):
                new_stage = [f for f in stage if not isinstance(f, str) or not f.startswith('pasterdream:')]
                new_features.append(new_stage)
            else:
                new_features.append(stage)
        data['features'] = new_features
        print(f'  [FIX {i}] 清理 features 引用')
    
    # 3. 清理 effects 中不存在的引用
    if 'effects' in data:
        effects = data['effects']
        # 移除 music（sweetdream 音效未注册）
        if 'music' in effects:
            del effects['music']
            print(f'  [FIX {i}] 移除 music 引用')
        # 移除 particle（leaves_particle 未注册）
        if 'particle' in effects:
            del effects['particle']
            print(f'  [FIX {i}] 移除 particle 引用')
    
    # 4. 清理不存在的实体
    if 'spawners' in data:
        spawners = data['spawners']
        for category in list(spawners.keys()):
            entries = spawners.get(category, [])
            if isinstance(entries, list):
                filtered = []
                for entry in entries:
                    entity_type = entry.get('type', '')
                    if entity_type == 'pasterdream:pink_chicken':
                        print(f'  [FIX {i}] 移除 pink_chicken 生成')
                        continue
                    filtered.append(entry)
                spawners[category] = filtered
    
    with open(fpath, 'w', encoding='utf-8') as f:
        json.dump(data, f, indent=2)
    print(f'  [OK] biome_dyedream_{i}.json 已修复')

print()
print('=== 全部修复完成! ===')