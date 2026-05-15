import zipfile, json, sys

jar_path = r'C:\Users\97128\Documents\GitHub\NeoPasterDream1\build\moddev\artifacts\neoforge-21.1.219-client-extra-aka-minecraft-resources.jar'
with zipfile.ZipFile(jar_path, 'r') as z:
    ns_files = [n for n in z.namelist() if 'worldgen/noise_settings' in n and n.endswith('.json')]
    for ns in ns_files:
        name = ns.split('/')[-1].replace('.json','')
        if name == 'overworld':
            data = json.loads(z.read(ns))
            router = data.get('noise_router', {})
            with open(r'C:\Users\97128\Documents\GitHub\NeoPasterDream1\overworld_router.json', 'w') as f:
                json.dump(router, f, indent=2)
            print('Done! Router saved to overworld_router.json')
            print(f'Router has {len(router)} entries')
            break