import zipfile, json

jar_path = r'C:\Users\97128\Documents\GitHub\NeoPasterDream1\build\moddev\artifacts\neoforge-21.1.219-client-extra-aka-minecraft-resources.jar'
with zipfile.ZipFile(jar_path, 'r') as z:
    # Look for factor definitions in noise files
    noise_files = [n for n in z.namelist() if 'worldgen/noise/' in n and n.endswith('.json')]
    for nf in noise_files:
        name = nf.split('/')[-1].replace('.json','')
        if 'factor' in name.lower() or 'depth' == name:
            data = json.loads(z.read(nf))
            print(f'=== {name} ===')
            print(json.dumps(data, indent=2))
            print()