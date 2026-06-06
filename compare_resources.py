"""
资源文件冲突检测脚本
比较 libs/FixPasterDream-main 和 src/main/resources 中的资源文件
"""
import os
from pathlib import Path
import hashlib

# 定义路径
LIBS_BASE = Path(r"c:\Users\97128\Documents\GitHub\NeoPasterDream1\libs\FixPasterDream-main\src\main\resources\assets\pasterdream")
SRC_BASE = Path(r"c:\Users\97128\Documents\GitHub\NeoPasterDream1\src\main\resources\assets\pasterdream")

# 要扫描的子目录
SUB_DIRS = [
    "textures/block",
    "textures/item",
    "textures/particle",
    "textures/entity",
    "textures/gui",
    "textures/environment",
    "textures/armor",
    "textures/models",
    "textures/misc",
    "textures/colormap",
    "models/item",
    "models/block",
    "models/entity",
    "lang",
    "sounds",
    "particles",
]


def get_relative_paths(base_path, sub_dir):
    """获取某个子目录下的所有文件相对路径"""
    full_path = base_path / sub_dir
    if not full_path.exists():
        return set()
    result = set()
    for root, dirs, files in os.walk(full_path):
        rel_root = os.path.relpath(root, base_path)
        for f in files:
            # 使用正斜杠统一路径格式
            rel_path = os.path.join(rel_root, f).replace("\\", "/")
            result.add(rel_path)
    return result


def get_file_hash(filepath):
    """计算文件的 MD5 哈希"""
    h = hashlib.md5()
    with open(filepath, "rb") as f:
        for chunk in iter(lambda: f.read(4096), b""):
            h.update(chunk)
    return h.hexdigest()


def main():
    print("=" * 80)
    print("资源文件冲突检测报告")
    print("=" * 80)

    # 1. 收集所有目录中的文件
    libs_files = {}  # sub_dir -> set of relative paths
    src_files = {}

    all_libs_files = set()
    all_src_files = set()

    for sub_dir in SUB_DIRS:
        lf = get_relative_paths(LIBS_BASE, sub_dir)
        sf = get_relative_paths(SRC_BASE, sub_dir)

        if lf:
            libs_files[sub_dir] = lf
            all_libs_files.update(lf)
        if sf:
            src_files[sub_dir] = sf
            all_src_files.update(sf)

    # 2. 统计信息
    print(f"\n📊 统计概览")
    print(f"   FixPasterDream-main (旧模组) 总文件数: {len(all_libs_files)}")
    print(f"   src/main/resources (当前项目) 总文件数: {len(all_src_files)}")

    print(f"\n按目录类型统计:")
    print(f"   {'目录':<25} {'旧模组':<10} {'当前项目':<10}")
    print(f"   {'-'*45}")
    for sub_dir in SUB_DIRS:
        lc = len(libs_files.get(sub_dir, set()))
        sc = len(src_files.get(sub_dir, set()))
        if lc > 0 or sc > 0:
            print(f"   {sub_dir:<25} {lc:<10} {sc:<10}")

    # 3. 查找重复文件（在 libs 和 src 中同时存在的文件）
    print(f"\n{'='*80}")
    print(f"🔍 重复文件检测（在两个目录中同时存在的文件）")
    print(f"{'='*80}")

    duplicate_files = all_libs_files & all_src_files
    dup_count_by_dir = {}

    if duplicate_files:
        sorted_dup = sorted(duplicate_files)
        for f in sorted_dup:
            dir_type = f.split("/")[0] + "/" + f.split("/")[1] if "/" in f else f
            dup_count_by_dir[dir_type] = dup_count_by_dir.get(dir_type, 0) + 1

        print(f"\n共发现 {len(duplicate_files)} 个重复文件:")
        for dir_type, count in sorted(dup_count_by_dir.items()):
            print(f"   📁 {dir_type}: {count} 个文件")
        print(f"\n详细列表:")
        for f in sorted_dup:
            print(f"   📄 {f}")
    else:
        print("   ✅ 没有发现重复文件！")

    # 4. 检查文件名相同但内容可能不同的文件
    print(f"\n{'='*80}")
    print(f"🔎 内容差异检测（相同文件名，检查内容是否不同）")
    print(f"{'='*80}")

    content_diff_count = 0
    for f in sorted(duplicate_files):
        libs_path = LIBS_BASE / f
        src_path = SRC_BASE / f

        if libs_path.exists() and src_path.exists():
            libs_hash = get_file_hash(libs_path)
            src_hash = get_file_hash(src_path)

            if libs_hash != src_hash:
                content_diff_count += 1
                libs_size = os.path.getsize(libs_path)
                src_size = os.path.getsize(src_path)
                print(f"   ⚠️ {f}")
                print(f"      旧模组: {libs_size} bytes, MD5: {libs_hash[:16]}...")
                print(f"      当前项目: {src_size} bytes, MD5: {src_hash[:16]}...")

    if content_diff_count == 0 and duplicate_files:
        print("   ✅ 所有重复文件的内容一致")
    elif content_diff_count == 0:
        print("   ✅ 没有重复文件需要检查")

    # 5. 缺失资源检测（libs 中有但 src 中没有的纹理文件）
    print(f"\n{'='*80}")
    print(f"❌ 缺失资源检测（旧模组中有但当前项目中没有的文件）")
    print(f"{'='*80}")

    missing_files = all_libs_files - all_src_files
    missing_count_by_dir = {}

    if missing_files:
        sorted_missing = sorted(missing_files)
        for f in sorted_missing:
            dir_type = f.split("/")[0] + "/" + f.split("/")[1] if "/" in f else f
            missing_count_by_dir[dir_type] = missing_count_by_dir.get(dir_type, 0) + 1

        print(f"\n共发现 {len(missing_files)} 个缺失资源文件:")
        for dir_type, count in sorted(missing_count_by_dir.items()):
            print(f"   📁 {dir_type}: {count} 个文件")

        print(f"\n详细列表（按目录分组）:")
        current_dir = ""
        for f in sorted_missing:
            dir_part = "/".join(f.split("/")[:-1])
            if dir_part != current_dir:
                print(f"\n   📁 {dir_part}/")
                current_dir = dir_part
            print(f"      📄 {os.path.basename(f)}")
    else:
        print("   ✅ 没有缺失资源文件")

    # 6. 额外文件检测（src 中有但 libs 中没有的文件）
    print(f"\n{'='*80}")
    print(f"➕ 新增资源检测（当前项目中有但旧模组中没有的文件）")
    print(f"{'='*80}")

    extra_files = all_src_files - all_libs_files
    extra_count_by_dir = {}

    if extra_files:
        sorted_extra = sorted(extra_files)
        for f in sorted_extra:
            dir_type = f.split("/")[0] + "/" + f.split("/")[1] if "/" in f else f
            extra_count_by_dir[dir_type] = extra_count_by_dir.get(dir_type, 0) + 1

        print(f"\n共发现 {len(extra_files)} 个新增资源文件:")
        for dir_type, count in sorted(extra_count_by_dir.items()):
            print(f"   📁 {dir_type}: {count} 个文件")

        print(f"\n详细列表（按目录分组）:")
        current_dir = ""
        for f in sorted_extra:
            dir_part = "/".join(f.split("/")[:-1])
            if dir_part != current_dir:
                print(f"\n   📁 {dir_part}/")
                current_dir = dir_part
            print(f"      📄 {os.path.basename(f)}")
    else:
        print("   ✅ 没有新增资源文件")

    # 7. 总结
    print(f"\n{'='*80}")
    print(f"📋 总结")
    print(f"{'='*80}")
    print(f"   旧模组文件总数: {len(all_libs_files)}")
    print(f"   当前项目文件总数: {len(all_src_files)}")
    print(f"   重复文件数: {len(duplicate_files)}")
    print(f"   内容有差异的文件数: {content_diff_count}")
    print(f"   缺失资源文件数: {len(missing_files)}")
    print(f"   新增资源文件数: {len(extra_files)}")


if __name__ == "__main__":
    main()