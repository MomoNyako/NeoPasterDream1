# PasterDream API 可扩展性分析报告

> 生成日期：2026-06-07  
> 分析范围：`api/` 包 + `worldgen/decor/` 包  
> 分析目标：评估各 API 对未来功能扩展的支持程度

---

## 目录

1. [总览：API 架构概览](#1-总览api-架构概览)
2. [DecorationBuilder 装饰物 API](#2-decorationbuilder-装饰物-api)
3. [EntityAPI 实体 API](#3-entityapi-实体-api)
4. [ParticleAPI 粒子 API](#4-particleapi-粒子-api)
5. [ItemMigrationAPI 物品迁移 API](#5-itemmigrationapi-物品迁移-api)
6. [BlockAPI 方块 API](#6-blockapi-方块-api)
7. [DimensionAPI 维度 API](#7-dimensionapi-维度-api)
8. [RuinAPI 遗迹 API](#8-ruinapi-遗迹-api)
9. [总结：扩展性评分矩阵](#9-总结扩展性评分矩阵)

---

## 1. 总览：API 架构概览

本项目采用 **Facade（外观）+ Builder（构建器）** 混合模式设计 API：

```
┌─────────────────────────────────────────────────────┐
│                  Facade API 外观层                    │
│  EntityAPI / BlockAPI / ParticleAPI / DimensionAPI   │
│  RuinAPI / ItemMigrationAPI                         │
├─────────────────────────────────────────────────────┤
│                  Builder 构建器层                     │
│  EntityBuilder / SimpleBlockBuilder / RuinBuilder    │
│  ItemBuilder / DecorationBuilder                     │
├─────────────────────────────────────────────────────┤
│                  Registry 注册层                      │
│  DecorationRegistry / DeferredRegister               │
└─────────────────────────────────────────────────────┘
```

**整体评价**：API 设计遵循一致的模式，Builder 链式调用统一，可扩展性良好。但在部分细节上存在可优化点。

---

## 2. DecorationBuilder 装饰物 API

### 2.1 架构

```
DecorationBuilder (链式配置)
    ↓ build()
DecorationConfig (不可变记录)
    ↓
GenericDecorationFeature.place() (类型分发)
    ↓
switch(DecorationType) → 具体生成逻辑
```

### 2.2 扩展性评分

| 维度 | 评分 | 说明 |
|------|------|------|
| 新增装饰物实例 | ⭐⭐⭐⭐⭐ | 只需调用 Builder 链式配置，零模板代码 |
| 新增装饰物类型 | ⭐⭐⭐ | 需在 4 处添加代码 |
| 新增配置参数 | ⭐⭐⭐ | Config 记录需添加字段，Builder 需添加方法 |
| 自定义生成逻辑 | ⭐⭐⭐⭐⭐ | `ICustomDecorationGenerator` 接口即插即用 |
| JSON 输出灵活性 | ⭐⭐ | `generateAllJson()` 路径硬编码 |
| 第三方扩展 | ⭐⭐⭐⭐ | 可通过实现接口扩展，但无法绕过 GenericDecorationFeature |

### 2.3 关键发现

#### ✅ 优点

1. **`ICustomDecorationGenerator` 接口** — 任何第三方模块只需实现 `generate()` 方法并调用 `DecorationRegistry.registerCustomGenerator()` 即可添加自定义装饰物生成器，完全符合开闭原则。

2. **`DecorationBuilder` 的链式调用** — 每个配置方法独立且可自由组合，添加新配置参数只需新增一个方法，不影响现有调用。

3. **`DecorationRegistry` 存储结构** — 使用 `ArrayList<DecorationEntry>` + `Map<String, ICustomDecorationGenerator>`，支持高效查询和按需扩展。

#### ⚠️ 待优化点

1. **`DecorationConfig` 记录类字段硬编码**

   当前 `DecorationConfig` 使用 Java `record` 将所有参数声明为字段。未来添加新参数时必须修改记录定义：

   ```java
   // 当前：所有字段硬编码
   public record DecorationConfig(
       DecorationType type,
       BlockStateProvider body,
       int minHeight, int maxHeight,   // ← 添加新参数需要修改这里
       int minWidth, int maxWidth,
       // ... 更多字段
   ) {}
   ```

   **影响**：如果未来需要添加持续伤害区域、红石信号响应等新型参数，Config 记录会不断膨胀。

   **优化建议**：考虑引入 `Map<String, Object> extraConfig` 兜底字段，或使用分层 Config（基础 Config + 类型特定 Config）。

2. **`GenericDecorationFeature.place()` 的 switch 分发**

   ```java
   // 当前：switch 语句，新增类型需修改此方法
   switch (config.type()) {
       case SPIKE -> placeSpike(context, config);
       case PILLAR -> placePillar(context, config);
       case BLOB -> placeBlob(context, config);
       case CUSTOM -> placeCustom(context, config);
       // 新增类型 → 必须在这里加 case ❌
   }
   ```

   **影响**：违反开闭原则。每新增一个 `DecorationType` 枚举值，都必须修改 `place()` 方法。

   **优化建议**：使用 **策略模式（Strategy Pattern）**：

   ```java
   // 策略模式替代 switch
   Map<DecorationType, DecorationPlacer> placers = new HashMap<>();
   placers.put(DecorationType.SPIKE, new SpikePlacer());
   placers.put(DecorationType.PILLAR, new PillarPlacer());
   // 新增类型只需 placers.put(newType, newPlacer) ✅
   ```

3. **JSON 生成路径硬编码**

   `DecorationRegistry.generateAllJson()` 方法中输出路径为硬编码字符串。若未来需要支持多数据包输出或多格式输出（如 JSON5），需修改核心代码。

### 2.4 扩展场景验证

| 场景 | 可行？ | 需改动 |
|------|--------|--------|
| 添加新的钻石形状冰晶装饰物 | ✅ | 零改动，纯 Builder 配置 |
| 添加新的自定义门型结构 | ✅ | 实现 `ICustomDecorationGenerator` |
| 添加「持续掉落陨石」类型 | ❌ 需优化 | 新增 `DecorationType` + Config 字段 + switch case |
| 让装饰物支持红石信号 | ❌ 需优化 | Config 加字段 + Builder 加方法 + Feature 里处理 |

---

## 3. EntityAPI 实体 API

### 3.1 可扩展性

| 维度 | 评分 | 说明 |
|------|------|------|
| 新增实体 | ⭐⭐⭐⭐⭐ | 一行调用 + Builder 配置 |
| 新增实体行为 | ⭐⭐⭐⭐ | 通过子类继承扩展 |
| 新增配置参数 | ⭐⭐⭐⭐ | Builder 加方法，不影响现有 |
| 第三方扩展 | ⭐⭐⭐⭐ | Facade 开放，Builder 可继承 |

### 3.2 关键发现

`EntityAPI` 采用 **Facade + Builder** 模式：

```java
EntityAPI.createEntity(modId, "my_entity")
    .type(EntityType.MONSTER)
    .attributes(MyAttributes::create)
    .egg(0xFF0000, 0x00FF00)
    .buildAndRegister();
```

**优点**：
- Builder 的每个配置方法都返回 `EntityBuilder` 自身，链式调用流畅
- 支持技能系统扩展（通过 `EntityAPI.registerSkill()`）
- 不强制所有实体使用 Builder，支持直接注册

**待优化点**：
- 实体属性注册（Attributes）在 `EntityBuilder` 中通过硬编码方法设置，未来若需要支持自定义属性，可能需要新增方法或配置项
- 部分逻辑（如生成蛋注册）耦合在 Builder 中，若不需要生成蛋，仍会执行相关逻辑

---

## 4. ParticleAPI 粒子 API

### 4.1 可扩展性

| 维度 | 评分 | 说明 |
|------|------|------|
| 新增粒子 | ⭐⭐⭐⭐⭐ | Builder 配置，一行搞定 |
| 新增粒子属性 | ⭐⭐⭐⭐ | Builder 加方法即可 |
| 支持自定义渲染 | ⭐⭐⭐ | 需额外实现 ParticleProvider |
| 第三方扩展 | ⭐⭐⭐⭐ | 接口开放 |

### 4.2 关键发现

```java
ParticleAPI.createParticle(modId, "my_particle")
    .texture("pasterdream:my_particle")
    .gravity(0.01f)
    .alwaysShow(true)
    .build();
```

**优点**：API 极为精简，内部封装了 `SimpleParticleType` 注册、纹理绑定等复杂度。

**待优化点**：
- 目前仅支持 `SimpleParticleType`（无参数粒子），不支持带参数的自定义粒子类型（`ParticleType<T>`）
- 有限的生命周期回调接口（粒子生成、更新、消亡等事件暂未暴露）

---

## 5. ItemMigrationAPI 物品迁移 API

### 5.1 可扩展性

| 维度 | 评分 | 说明 |
|------|------|------|
| 新增简单物品 | ⭐⭐⭐⭐⭐ | `createSimpleItem()` 一行搞定 |
| 新增复杂物品 | ⭐⭐⭐⭐ | Builder 支持自定义属性 |
| 旧模组迁移 | ⭐⭐⭐⭐⭐ | 专为此场景设计 |
| 自定义物品行为 | ⭐⭐⭐ | 简单物品不支持重写方法 |

### 5.2 关键发现

```java
// 方式 1：超快速导入
ItemMigrationAPI.createSimpleItem("my_item", new Item.Properties());

// 方式 2：Builder 模式（支持更多配置）
ItemMigrationAPI.createItem(modId, "my_item")
    .properties(props)
    .build();
```

**优点**：
- 专门为从旧版 MCreator 模组迁移设计，大量简化重复代码
- 支持一键生成配方、战利品表、标签
- Builder 支持多种物品类型（普通、食物、唱片等）

**待优化点**：
- 简单物品只支持纯构造函数（无方法重写），若需要 `use()`、`inventoryTick()` 等自定义行为，仍需写独立类
- API 名称为 `ItemMigrationAPI`，暗示这是"过渡"方案，长期来看应更名为 `ItemAPI` 或类似名称，表明这是标准注册方式

---

## 6. BlockAPI 方块 API

### 6.1 可扩展性

| 维度 | 评分 | 说明 |
|------|------|------|
| 新增基础方块 | ⭐⭐⭐⭐⭐ | Builder 批量注册 |
| 新增方块变种 | ⭐⭐⭐⭐⭐ | VariantSetBuilder 支持楼梯/ slabs/墙 |
| 自定义方块行为 | ⭐⭐⭐⭐ | 支持自定义 Block 类 |
| 第三方扩展 | ⭐⭐⭐⭐ | 接口开放 |

### 6.2 关键发现

```java
// 批量注册基础方块
BlockAPI.createSimpleBlock(modId, "my_block")
    .batchRegister("block_1", "block_2", "block_3");

// 带变种的方块组
BlockAPI.createVariantSet(modId, "my_stone")
    .stairs().slab().wall()
    .register("my_stone");
```

**优点**：
- 三种 Builder 类型覆盖了绝大多数使用场景
- `VariantSetBuilder` 自动生成楼梯/ slabs/墙的配套注册，大幅减少重复代码

**待优化点**：
- `BlockConfig` 与 `BlockAPI` 之间有职责重叠，`BlockConfig` 的很多字段可通过 Builder 链式设置

---

## 7. DimensionAPI 维度 API

### 7.1 可扩展性

| 维度 | 评分 | 说明 |
|------|------|------|
| 新增维度 | ⭐⭐⭐⭐⭐ | Builder 一键配置 |
| 自定义维度类型 | ⭐⭐⭐⭐ | 可自定义噪声/生物群系/音效 |
| 默认支持染梦风格 | ⭐⭐⭐⭐⭐ | 专为此场景优化 |
| 第三方扩展 | ⭐⭐⭐ | 部分配置内置了染梦默认值 |

### 7.2 关键发现

```java
DimensionAPI.createDimension(modId, "my_dimension")
    .type(dimensionType)
    .generator(noiseGenerator)
    .register();
```

**优点**：封装了维度注册的全部复杂度（维度类型、噪声设置、生物群系等）。

**待优化点**：
- 部分默认配置（如默认生物群系、默认音效）内置了染梦维度的硬编码值，第三方复用需要仔细检查
- `DimensionAPI` 对外暴露的配置项较多，容易遗漏必填参数

---

## 8. RuinAPI 遗迹 API

### 8.1 可扩展性

| 维度 | 评分 | 说明 |
|------|------|------|
| 新增遗迹 | ⭐⭐⭐⭐⭐ | Builder 配置模板/生物群系/权重 |
| 自定义生成逻辑 | ⭐⭐⭐⭐ | 可自定义处理器 |
| 地表评估 | ⭐⭐⭐⭐⭐ | 内置地形评估工具 |
| 第三方扩展 | ⭐⭐⭐⭐ | 接口开放 |

### 8.2 关键发现

```java
RuinAPI.createRuin(modId, "my_ruin")
    .template(ResourceLocation.parse("pasterdream:my_ruin"))
    .biomes("#pasterdream:is_dyedream")
    .spacing(10, 5)
    .register();
```

**优点**：
- 内置了结构生成的地表评估和诊断功能
- 与原版结构注册（`Structure` / `StructureSet`）完全兼容

**待优化点**：
- 结构模板（nbt 文件）的路径约定需要文档化
- 注册后生成的 JSON 文件路径可能在 IDE 和 Gradle 构建中行为不一致

---

## 9. 总结：扩展性评分矩阵

### 综合评分

```
DecorationBuilder    ██████████░░░░  65%  (switch + Config 硬编码拖后腿)
EntityAPI            ████████████░░  75%  (属性注册可再灵活)
ParticleAPI          █████████████░  80%  (仅支持简单粒子)
ItemMigrationAPI     ████████████░░  72%  (名称暗示过渡性)
BlockAPI             █████████████░  82%  (比较完善)
DimensionAPI         ████████████░░  75%  (硬编码默认值)
RuinAPI              ████████████░░  75%  (文档化不足)
```

### 优先级改进建议

| 优先级 | 改进项 | 影响范围 | 难度 |
|--------|--------|---------|------|
| P0 | `GenericDecorationFeature.place()` 策略模式改造 | DecorationBuilder 体系 | 中 |
| P0 | `DecorationConfig` 支持扩展配置字段 | DecorationBuilder 体系 | 低 |
| P1 | `ItemMigrationAPI` → `ItemAPI` 重命名+统一 | api/itemmigration/ | 低 |
| P1 | 粒子 API 支持 `ParticleType<T>` | ParticleAPI | 中 |
| P2 | DimensionAPI 剥离染梦默认值 | DimensionAPI | 低 |
| P2 | JSON 生成路径配置化 | DecorationRegistry | 低 |
| P3 | Builder 必填字段校验 | 所有 Builder | 中 |

### 扩展性最佳实践建议

1. **策略模式替代 switch**：在所有需要按类型分发的地方使用 `Map<Type, Handler>` 替代 switch
2. **Config 采用分层结构**：基础 Config + 类型特定 Config，避免单记录膨胀
3. **Builder 方法统一**：所有 Builder 的方法命名保持一致（`.type()`、`.body()`、`.register()`）
4. **必填参数使用 `requireNonNull`**：在 `register()` 中校验必填字段，提供清晰的错误信息
5. **Facade 层只做路由**：各 API 的 Facade 方法只负责参数校验和路由，具体逻辑委托给 Builder