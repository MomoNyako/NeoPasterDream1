from PIL import Image
import os

# 创建简单的传送水晶纹理 (16x16)
img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
pixels = img.load()

colors = [
    (0x8B, 0x5C, 0xF6, 0xFF),  # 紫色
    (0x6B, 0x3C, 0xD6, 0xFF),  # 深紫
    (0xB8, 0x9C, 0xFE, 0xFF),  # 浅紫
    (0x5C, 0x3C, 0xA6, 0xFF),  # 暗紫
]

# 绘制水晶形状
for y in range(16):
    for x in range(16):
        dx, dy = x - 7.5, y - 7.5
        dist = (dx*dx + dy*dy) ** 0.5
        
        if dist < 7.5:
            # 从中心向外渐变
            brightness = 1.0 - (dist / 7.5)
            if brightness > 0.3:
                idx = min(int(brightness * 3), 3)
                c = colors[idx]
                # 边缘发光
                alpha = int(255 * brightness)
                pixels[x, y] = (c[0], c[1], c[2], min(255, int(alpha * 1.2)))
        elif dist < 8:
            # 边缘发光
            alpha = int(64 * (1.0 - (dist - 7.5) / 0.5))
            pixels[x, y] = (0x8B, 0x5C, 0xF6, alpha)

# 画点高光
for (hx, hy) in [(5, 5), (6, 4), (9, 6)]:
    for dy in range(-1, 2):
        for dx in range(-1, 2):
            if 0 <= hx+dx < 16 and 0 <= hy+dy < 16:
                r, g, b, a = pixels[hx+dx, hy+dy]
                pixels[hx+dx, hy+dy] = (min(255, r+40), min(255, g+40), min(255, b+40), a)

output_dir = r'c:\Users\97128\Documents\GitHub\NeoPasterDream1\src\main\resources\assets\pasterdream\textures\item'
os.makedirs(output_dir, exist_ok=True)
img.save(os.path.join(output_dir, 'dyedream_teleport_crystal.png'))
print('  [OK] dyedream_teleport_crystal.png 已生成')