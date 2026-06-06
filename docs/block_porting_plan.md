# 🧱 PasterDream 方块/流体移植计划

## 概述

旧模组（FixPasterDream）中有 6 个方块/流体尚未移植到新模组（NeoPasterDream1）。这些方块在 NBT 结构文件中被引用，导致结构生成后对应位置显示为空气/紫黑格子。

---

## 1. `meltdream_liquid` — 融梦涌泉（流体）

**在 NBT 中出现位置**：`dyedream_worldtree_true.nbt`（50 个方块）

**旧模组实现分析**（`libs/FixPasterDream-main/`）：

| 文件 | 说明 |
|:---|:---|
| `fluid/MeltdreamLiquidFluid.java` | 流体本体（Source + Flowing） |
| `fluid/types/MeltdreamLiquidFluidType.java` | 流体类型（含纹理 `meltdream_liquid_still` / `meltdream_liquid_flowing`） |
| `block/MeltdreamLiquidBlock.java` | 流体方块（继承 `LiquidBlock`） |
| `registry/ModFluids.java` | 注册点 |

**新模组现状**：仅有 `MeltdreamLiquidItem.java`（普通 Item 占位）、`meltdream_liquid_bucket`（桶物品）。

**移植步骤**：
1. 创建 `fluid/MeltdreamLiquidFluid.java` —— 注册 Source + Flowing
2. 创建 `fluid/MeltdreamLiquidFluidType.java` —— 注册 FluidType
3. 创建 `block/MeltdreamLiquidBlock.java` —— 注册 FluidBlock
4. 更新 `MeltdreamLiquidItem.java` 为 `BucketItem` 子类
5. 注册到对应的 DeferredRegister
6. 复制纹理：`meltdream_liquid_still.png` / `meltdream_liquid_flowing.png`

**参考资料**：NeoForge 1.21.1 流体注册指南，旧模组代码位于 `libs/FixPasterDream-main/`

---

## 2. `meltdream_chest` / `meltdream_chest_open` — 融梦水晶箱

**在 NBT 中出现位置**：`dream_train.nbt`（1+1 个）、`dyedream_worldtree_true.nbt`（6+2 个）、`pinkagaric_house_3.nbt`（1 个）

**旧模组实现分析**：

| 文件 | 说明 |
|:---|:---|
| `block/MeltdreamChestBlock.java` | 箱子方块 |
| `block/MeltdreamChestOpenBlock.java` | 开启状态的箱子方块 |
| `block/entity/MeltdreamChestTileEntity.java` | 方块实体（含物品栏） |
| `block/display/MeltdreamChestDisplayItem.java` | 手持显示 |
| `world/inventory/MeltdreamChestGuiMenu.java` | GUI 菜单 |
| `client/gui/MeltdreamChestGuiScreen.java` | GUI 屏幕 |
| 各种 GeoModel / Renderer 文件 | 3D 模型渲染 |

**新模组现状**：有一个 `shadow_chest`（影之箱）已移植，可作为参考实现。

**移植步骤**：
1. 注册 `meltdream_chest` 和 `meltdream_chest_open` 两个方块
2. 注册对应的 BlockEntity
3. 注册 GUI Menu 类型
4. 实现 Screen
5. 实现 DisplayItem（手持渲染）
6. 实现 3D 模型（GeckoLib 或原版模型）
7. 配置 BlockItem

**参考资料**：参考已移植的 `shadow_chest` 实现，旧模组代码位于 `libs/FixPasterDream-main/`

---

## 3. `the_endless_book_of_dream_seekers` — §e寻梦者的永恒书卷

**在 NBT 中出现位置**：`dream_train.nbt`（1 个）

**旧模组实现分析**：

| 文件 | 说明 |
|:---|:---|
| `block/TheEndlessBookOfDreamSeekersBlock.java` | 方块（BaseEntityBlock，含物品栏） |
| `block/entity/TheEndlessBookOfDreamSeekersTileEntity.java` | 方块实体（RandomizableContainerBlockEntity） |
| `block/display/TheEndlessBookOfDreamSeekersDisplayItem.java` | 手持显示 |
| `world/inventory/TheEndlessBookOfDreamSeekersGuiMenu.java` | GUI 菜单 |
| `client/gui/TheEndlessBookOfDreamSeekersGuiScreen.java` | GUI 屏幕 |
| 各种 GeoModel / Renderer 文件 | 3D 书籍模型渲染 |

**新模组现状**：完全未移植。

**移植步骤**：
1. 注册方块
2. 注册 BlockEntity
3. 注册 GUI Menu
4. 实现 Screen
5. 实现 DisplayItem
6. 实现 GeckoLib 3D 模型

---

## 4. `dream_cauldron` — 法术工厂（炼药锅）

**在 NBT 中出现位置**：`dyedream_worldtree_true.nbt`（1 个）

**旧模组实现分析**：

| 文件 | 说明 |
|:---|:---|
| `block/DreamCauldronBlock.java` | 方块（BaseEntityBlock，带 GUI） |
| `block/entity/DreamCauldronTileEntity.java` | 方块实体 |
| `block/display/DreamCauldronDisplayItem.java` | 手持显示 |
| `world/inventory/DreamCauldronGuiMenu.java` | GUI 菜单 |
| `client/gui/DreamCauldronGuiScreen.java` | GUI 屏幕 |
| `jei/dreamcauldron/DreamCauldronRecipe.java` | JEI 合成表 |
| `jei/dreamcauldron/DreamCauldron.java` | JEI 显示 |

**新模组现状**：完全未移植。

**移植步骤**：
1. 注册方块
2. 注册 BlockEntity
3. 注册 GUI Menu
4. 实现 Screen
5. 实现 DisplayItem
6. 实现自定义合成表（DreamCauldronRecipe）
7. 配置 JEI 集成

---

## 5. `dream_train_structure` — 梦境列车结构方块

**在 NBT 中出现位置**：`dream_train_platform.nbt`（1 个）

**旧模组实现分析**：这是一个简单方块，点击触发 `DreamTrainStructurePr0Procedure`。

**新模组现状**：完全未移植。

**移植步骤**：
1. 注册简单方块
2. 实现右键交互逻辑

---

## 快速方案（在移植完成前让结构正常显示）

可在 NBT 文件中将这些方块临时替换为原版等价物，在需要时恢复：

| 旧方块 | 建议替换为 | 影响结构 |
|:---|:---|:---|
| `meltdream_liquid` | `minecraft:water` | dyedream_worldtree_true |
| `meltdream_chest` / `_open` | `minecraft:chest` | dream_train, worldtree, pinkagaric_3 |
| `the_endless_book_of_dream_seekers` | `minecraft:lectern` | dream_train |
| `dream_cauldron` | `minecraft:cauldron` | dyedream_worldtree_true |
| `dream_train_structure` | `minecraft:purple_stained_glass` | dream_train_platform |

---

## 优先级建议

| 优先级 | 方块 | 理由 |
|:---:|:---|:---|
| 🔴 P0 | `meltdream_chest` / `_open` | 出现数量最多（23 个），涉及 3 个结构 |
| 🔴 P0 | `meltdream_liquid` | 出现 50 个方块，影响视觉效果最大的 |
| 🟡 P1 | `the_endless_book_of_dream_seekers` | 仅 1 个，但独特方块 |
| 🟡 P1 | `dream_cauldron` | 仅 1 个，但功能复杂 |
| 🟢 P2 | `dream_train_structure` | 仅 1 个，简单方块 |

---

> **注意**：所有旧模组源码和资源均位于 `libs/FixPasterDream-main/` 目录下，可作为移植参考。
