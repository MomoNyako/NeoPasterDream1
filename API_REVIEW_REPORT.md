# PasterDreamAPI 全面重构审查报告

> **审查日期**：2026-06-18  
> **审查范围**：`PasterDreamAPI/src/main/java/com/pasterdream/pasterdreammod/api` 全部源码与 `PasterDreamAPI/build.gradle`  
> **对照基准**：项目 `README.md` 已知问题清单、`.trae/rules/project_rules.md` 开发规范  
> **编译状态**：`BUILD SUCCESSFUL`（仅 1 条 Curio 弃用警告）

---

## 0. 执行摘要

本次审查采用「多子代理并行扫描 + 关键文件人工精读 + 实测编译验证」的方式，对 `PasterDreamAPI` 模块进行了代码质量、架构分层、错误处理、日志、API 设计、文件结构等 6 个维度的系统审查。

**核心结论**：
- 模块整体架构良好，Facade + Builder + Result 模式统一；
- 当前可正常编译，但存在 **1 个 P0 级运行时缺陷（粒子 Provider 缺失）**、**1 个 P0 级服务端崩溃风险（示例类引用客户端类）**；
- 示例代码、开发工具类仍在 `src/main/java` 中，会随 JAR 发布；
- 日志输出过噪、部分 API 使用已弃用方法、查询方法返回 `null` 等问题需要治理。

---

## 1. 与 README.md 已知问题交叉核对

| README 条目 | README 状态 | 本次审查确认 | 严重度 |
|------------|:----------:|:------------|:------:|
| CurioAPI 引用客户端类 | 已修复 | 已通过 `Supplier<?>` + `CurioClientBridge` 解耦，但 Bridge 接口仍定义在 common 模块，建议再加 side 校验 | 中 |
| 双重注册残留 | 已修复 | 编译通过，未发现残留 | — |
| **粒子 Provider 缺失** | **未修复** | **确认属实：SHADOW_STONE、SPORE、FOX_FIRE_0、FOX_FIRE_1 四个粒子未在 ClientSetup 注册 Provider，且 `client/particle/` 无对应粒子类** | **P0** |
| 缺少统一注册入口 | 修复中 | 确认未实现 `PasterDreamAPI.registerAll(modEventBus)` | P1 |
| 日志过多 | 计划中 | 确认存在，ParticleBuilder/EntityAPI/BlockLootAPI 尤为严重 | P1 |
| 粒子新旧混用 | 未进行 | 确认：PDParticles 混合旧式 `DeferredHolder` 与 ParticleAPI Builder，缓存也不完整 | P1 |
| 静态缓存无清理 | 未进行 | 确认：ParticleAPI、EntityAPI、CurioAPI 均无 `resetForTesting()` | P2 |
| 查询返回 null | 未进行 | 确认 6 处返回 null，建议统一为 `Optional` | P2 |
| ItemMigrationAPI 命名 | 未进行 | 建议拆分为 `ItemAPI` + `ItemMigrationAPI` | P2 |
| example 包打包 | 未进行 | 确认 5 个示例文件会进入 JAR | P3 |
| API 覆盖不完整 | 未进行 | 缺 BlockEntity/Menu/Fluid 等 Facade，属长期规划 | P3 |

### 本次审查新发现（README 未列出）

| 新发现问题 | 严重度 | 说明 |
|-----------|:------:|------|
| `DimensionApiDemo` 直接 import 客户端类 | **P0** | `net.minecraft.client.renderer.DimensionSpecialEffects` 出现在 common 源码，有被服务端加载导致 `NoClassDefFoundError` 的风险 |
| `CurioBuilder` 使用已弃用 `ICurioItem` 方法 | P1 | `getAttributeModifiers(SlotContext,UUID,ItemStack)` 已标记 `forRemoval`，下版本会编译失败 |
| `ImportHelper` 等开发工具类会打包进 JAR | P1 | 756 行的代码生成器属于开发期工具，不应随 API 发布 |
| `BatchBlockBuilder` / `VariantSetBuilder` 未写入 `BLOCK_SUPPLIERS` | P1 | 导致 `BlockAPI.getBlock()` 查询不到批量/变体注册方块 |
| `BlockLootAPI` INFO 级别日志泛滥 | P1 | 每个方法 4-5 条 INFO，服务端日志可读性差 |
| `ApiSoundRegistry` 硬编码音乐文件 | P2 | 7 个 `.ogg` 名称静态写死，缺失时启动期无检测，运行时静默失败 |
| `ApiCodeGenConfig.setDefaultBasePath(null)` 无校验 | P2 | 延迟到使用时才抛异常 |
| `CompatLayer` 仍保留在源码 | P2 | 已标记 `@Deprecated(forRemoval=true)`，可直接删除 |

---

## 2. 错误处理机制审查

### 2.1 现状总览

| 分类 | 文件数 | 评级 |
|------|:-----:|:----:|
| Builder 必填校验完整 | 11 | ✅ |
| IO 异常捕获完整 | 8 | ✅ |
| 空指针保护不足 | 3 | ⚠️ |
| 运行时静默失败风险 | 1 | ❌ |

### 2.2 良好实践

- **Builder 校验**：`MobEffectBuilder.validate()`、`SimpleBlockBuilder.build()`、`BatchBlockBuilder.build()`、`CurioBuilder.register()` 均对必填字段执行校验；
- **IO 异常处理**：`BlockLootAPI.saveToFile()`、`DimensionBuilder.build()`、`ParticleGenerator`、`SoundsJsonGenerator` 均正确捕获 `IOException` 并包装为 `RuntimeException`；
- **资源访问温和降级**：`DimensionBuilder` 对 `.ogg` 文件缺失给出警告而非崩溃。

### 2.3 需改进项

| 文件 | 方法/位置 | 问题 | 建议 |
|------|----------|------|------|
| `ApiCodeGenConfig.java` | `setDefaultBasePath()` | 无 null 校验 | 添加 `Objects.requireNonNull` |
| `BatchBlockBuilder.java` | `build()` | 未将 `DeferredBlock` 存入 `BlockAPI.BLOCK_SUPPLIERS` | 注册后调用 `BlockAPI.putBlock()` |
| `VariantSetBuilder.java` | `build()` | 变体注册后未存入 `BLOCK_SUPPLIERS` | 每个变体注册后补充存储 |
| `ApiSoundRegistry.java` | 静态初始化块 | 硬编码 7 个音乐名，文件缺失时启动期不报错 | 改为从数据包读取或在构造期校验存在性 |

### 2.4 严重缺陷

**`DimensionApiDemo` 的客户端类引用**（详见第 4 节）属于错误处理层面的「侧加载风险」——common 模块代码若被服务端反射/类加载触发，将产生 `NoClassDefFoundError`。

---

## 3. 代码分层检查（客户端 / 服务端）

### 3.1 生产 API 分层状态

| 文件 | 客户端引用 | 风险等级 |
|------|:--------:|:------:|
| `CurioAPI.java` | 通过 `Supplier<?>` + `CurioClientBridge` 间接引用 | 🟡 需加 side 校验 |
| `ProcedureAnimationHandler.java` | 仅引用 GeckoLib 动画类（common 可用） | ✅ 安全 |
| `ParticleBuilder.java` | 仅注释中引用 `client.particle.Particle` | ✅ 安全 |
| `DimensionResult.java` | 仅注释中引用 `DimensionSpecialEffects` | ✅ 安全 |
| `DimensionApiDemo.java` | **直接 import `DimensionSpecialEffects`** | 🔴 **P0** |

### 3.2 结论

生产代码整体遵循了前后端分离原则，所有 Facade 类均未直接引用客户端类型。风险集中在 **示例/演示代码** 被编译进主源码集，可能随 class path 进入服务端。

---

## 4. 服务端安全风险详情

### 4.1 `DimensionApiDemo` 引用 `DimensionSpecialEffects`

- **文件**：`src/main/java/com/pasterdream/pasterdreammod/api/dimension/example/DimensionApiDemo.java`
- **行号**：第 7 行 `import net.minecraft.client.renderer.DimensionSpecialEffects;`
- **后果**：任何对 `DimensionApiDemo` 的类加载（包括反射扫描、注解处理器、IDE 调试入口）在 dedicated server 上都会触发 `NoClassDefFoundError`。
- **修复**：将该文件及同目录 `DimensionJsonGenerator.java` 一并迁移到 `src/test/java` 或独立工具模块。

### 4.2 `CurioAPI.registerClientRenderers()` 无 side 保护

- **文件**：`src/main/java/com/pasterdream/pasterdreammod/api/curio/CurioAPI.java`
- **行号**：第 119-124 行
- **后果**：若主模块在非客户端环境错误设置 `clientBridge`，调用时会尝试加载客户端类。
- **修复**：在方法入口增加：
  ```java
  if (FMLEnvironment.dist != Dist.CLIENT) {
      LOGGER.warn("registerClientRenderers() 只能在客户端调用");
      return;
  }
  ```

---

## 5. 胶水代码治理

### 5.1 已识别的胶水代码

| 文件/类 | 行数 | 状态 | 建议 |
|---------|:---:|:----:|------|
| `compat/CompatLayer.java` | 71 | `@Deprecated(forRemoval=true)` | **删除** |
| `compat/package-info.java` | 21 | `@Deprecated(forRemoval=true)` | **删除** |
| `ParticleAPI.cacheParticle()` | 1 方法 | `@Deprecated(forRemoval=true)` | 保留至下个主版本（主模块仍在调用） |
| `EntityResult.deferredHolder()` | 1 方法 | `@Deprecated(forRemoval=true)` | 保留至下个主版本 |
| `ItemMigrationAPI` 中的工具入口 | 多个方法 | 非 API 核心职责 | 拆出或标记弃用 |

### 5.2 `ItemMigrationAPI` 职责过重

该文件 573 行，混合了 3 类职责：

1. **物品注册**：`simpleItem()` / `foodItem()` / `toolItem()` / `curioItem()`
2. **迁移管理**：`markMigrated()` / `markPending()` / `generateReport()`
3. **开发工具入口**：`recipeGen()` / `lootTableGen()` / `blockDataGen()` / `creativeTabHelper()` / `importHelper()`

**建议**：
- 物品注册部分升级为 `ItemAPI`；
- `ItemMigrationAPI` 仅保留迁移追踪与报告；
- 工具入口统一迁移到 `itemmigration/gen/` 包下独立类，并从生产源码中移除。

---

## 6. 日志系统优化

### 6.1 日志使用统计

| 文件 | 日志调用数 | 主要级别 | 问题 |
|------|:--------:|:------:|------|
| `BlockLootAPI.java` | 32 | INFO | 🔴 泛滥，应降为 DEBUG |
| `EntityAPI.java` | 25 | INFO/DEBUG | 🟡 部分 INFO 可降 DEBUG |
| `ParticleBuilder.java` | 18 | INFO/DEBUG | 🟡 build 流程中 INFO 过密 |
| `MobEffectBuilder.java` | 18 | DEBUG/INFO | 🟢 级别基本合理，但 setter 可精简 |
| `MobEffectAPI.java` | 12 | DEBUG | 🟢 合理 |
| `DimensionAPI.java` | 8 | INFO | 🟢 合理 |

### 6.2 具体问题示例

**`BlockLootAPI.java` 典型过噪代码**：

```java
PasterDreamAPI.LOGGER.info("[BlockLootAPI] ===== selfDrop() 被调用 =====");
PasterDreamAPI.LOGGER.info("[BlockLootAPI] 方块名称: {}, 完整ID: {}", blockName, blockId);
PasterDreamAPI.LOGGER.info("[BlockLootAPI] 即将保存战利品表文件 → {}", blockName);
PasterDreamAPI.LOGGER.info("[BlockLootAPI] ✅ selfDrop() 完成: {}", blockName);
```

**建议**：除 `error` 外，其余注册流程日志统一降为 `debug`。

### 6.3 日志开关

当前 `PasterDreamAPI.LOGGER` 为标准 SLF4J Logger，无运行时开关。建议增加：

```java
private static volatile boolean debugLogging = false;

public static void setDebugLogging(boolean enabled) { ... }
public static boolean isDebugLogging() { return debugLogging; }
```

---

## 7. API 设计规范审查

### 7.1 Facade + Builder + Result 一致性

| API | Facade | Builder | Result | 注册器 | 评级 |
|-----|:------:|:------:|:-----:|:-----:|:---:|
| BlockAPI | ✅ | ✅ | VariantSetResult | ✅ | ✅ |
| EntityAPI | ✅ | ✅ | EntityResult | ✅ | ✅ |
| MobEffectAPI | ✅ | ✅ | MobEffectResult | ✅ | ✅ |
| ParticleAPI | ✅ | ✅ | ParticleResult | ✅ | ✅ |
| DimensionAPI | ✅ | ✅ | DimensionResult | ✅ | ✅ |
| RuinAPI | ✅ | ✅ | RuinResult | ✅ | ✅ |
| CurioAPI | ✅ | ✅ | CurioRegistration | ✅ | ✅ |
| ItemMigrationAPI | ❌ 混合 | ❌ 混合 | — | ❌ 内部 | 🔴 |

### 7.2 查询接口不一致

| API | 返回 null | 返回 Optional | 返回空集合 |
|-----|:--------:|:-----------:|:--------:|
| `EntityAPI.getEntityType()` | ✅ | — | — |
| `MobEffectAPI.getEffect()` | ✅ | — | — |
| `ParticleAPI.getParticle()` | ✅ | — | — |
| `CurioAPI.getCurio()` | — | ✅ | — |
| `EntityAPI.getEntitySkills()` | — | — | ✅ 空列表 |

**建议**：统一返回 `Optional`，并在 Javadoc 中标注 `@Nullable` 或 `@NotNull`。

### 7.3 大型文件清单（>300 行）

| 文件 | 行数 | 评估 |
|------|:---:|------|
| `itemmigration/example/ItemMigrationExample.java` | 757 | 示例文件，应移除 |
| `itemmigration/gen/ImportHelper.java` | 756 | 开发工具，应移除 |
| `dimension/example/DimensionApiDemo.java` | 596 | 示例文件，应移除 |
| `itemmigration/ItemMigrationAPI.java` | 573 | God Class，应拆分 |
| `block/builder/VariantSetBuilder.java` | 473 | 可抽取变体注册通用方法 |
| `entity/skill/EntitySkillManager.java` | 465 | 职责可拆分 |
| `itemmigration/gen/LootTableGenerator.java` | 442 | 开发工具，应移除 |
| `entity/EntityAPI.java` | 425 | 功能边界合理，可保持 |
| `effect/base/PasterDreamEffect.java` | 405 | 可接受 |
| `entity/builder/EntityBuilder.java` | 386 | 可接受 |
| `curio/CurioBuilder.java` | 382 | 可接受 |
| `ruin/builder/RuinBuilder.java` | 381 | 可接受 |

---

## 8. 文件结构整理

### 8.1 不应进入发布 JAR 的文件

以下文件位于 `src/main/java`，会被 `java-library` 插件打包：

| 文件/目录 | 行数 | 性质 | 风险 |
|----------|:---:|------|------|
| `block/example/BlockApiDemo.java` | 304 | 示例代码 | 含 `main()` |
| `dimension/example/DimensionApiDemo.java` | 596 | 示例代码 | 含 `main()` + 引用客户端类 |
| `dimension/example/DimensionJsonGenerator.java` | 246 | 示例/工具 | 生成资源文件 |
| `itemmigration/example/ItemMigrationExample.java` | 757 | 示例代码 | 含 `System.out.println` |
| `itemmigration/example/RecipeGenerationDemo.java` | 293 | 示例代码 | 含 `printStackTrace` |
| `itemmigration/gen/ImportHelper.java` | 756 | 开发工具 | 代码生成器 |
| `itemmigration/gen/RecipeGenerator.java` | 331 | 开发工具 | 代码生成器 |
| `itemmigration/gen/LootTableGenerator.java` | 442 | 开发工具 | 代码生成器 |
| `itemmigration/gen/BlockDataGenerator.java` | 311 | 开发工具 | 代码生成器 |
| `itemmigration/gen/LanguageGenerator.java` | — | 开发工具 | 代码生成器 |
| `itemmigration/gen/CreativeTabHelper.java` | 295 | 开发工具 | 代码生成器 |
| `compat/CompatLayer.java` | 71 | 废弃兼容层 | 已标记删除 |
| `compat/package-info.java` | 21 | 废弃兼容层 | 已标记删除 |

### 8.2 建议操作

```bash
# 迁移示例代码（保留历史，但不参与发布）
mv PasterDreamAPI/src/main/java/.../api/block/example      PasterDreamAPI/src/test/java/.../api/block/
mv PasterDreamAPI/src/main/java/.../api/dimension/example  PasterDreamAPI/src/test/java/.../api/dimension/
mv PasterDreamAPI/src/main/java/.../api/itemmigration/example PasterDreamAPI/src/test/java/.../api/itemmigration/

# 迁移或独立出开发工具类
mv PasterDreamAPI/src/main/java/.../api/itemmigration/gen  PasterDreamAPI/src/test/java/.../api/itemmigration/
# 或新建 PasterDreamAPI-Tools 子模块

# 删除废弃兼容层
rm -r PasterDreamAPI/src/main/java/.../api/compat/
```

---

## 9. 引用、依赖与循环引用检查

### 9.1 跨模块引用

| 引用方向 | 状态 |
|---------|:--:|
| API 内部 Facade → Builder | ✅ 正常 |
| API 内部 Builder → Result | ✅ 正常 |
| API → Curios API（compileOnly） | ✅ 正确 |
| API → GeckoLib（compileOnly） | ✅ 正确 |
| API → 主模块 `com.pasterdream.pasterdreammod.*` | ✅ 未发现 |

### 9.2 循环引用

**未发现循环引用**。依赖方向始终为 `Facade → Builder → Result/Model/Generator`。

---

## 10. 逻辑验证

### 10.1 关键业务逻辑

| 功能 | 验证结果 |
|------|:------:|
| SelfDropBlock 掉落逻辑 | ✅ 先查战利品表，再回退自掉落 |
| DimensionBuilder 维度构建 | ✅ 自动生成 dimension_type + dimension JSON |
| MobEffectBuilder 效果构建 | ✅ 必填校验 + EffectConfig 封装 |
| EntityBuilder 实体构建 | ✅ 支持属性、生成蛋、技能缓存 |
| CurioBuilder 饰品构建 | ⚠️ 使用已弃用 API，需迁移 |
| VariantSetBuilder 变体构建 | ⚠️ 未存入 BLOCK_SUPPLIERS |
| BatchBlockBuilder 批量构建 | ⚠️ 未存入 BLOCK_SUPPLIERS |

### 10.2 粒子 Provider 缺失（P0 详情）

`PDParticles.java` 注册了 15 个粒子类型，但在 `ClientSetup.registerParticleProviders()` 中只注册了 11 个 Provider：

| 粒子 | 是否注册 Provider | 对应粒子类 |
|------|:---------------:|-----------|
| `meltdream_crystal_particle` | ✅ | `LifeCrystalParticle` |
| `shadow_stone_particle` | ❌ | **不存在** |
| `dreamfertiliter_particle` | ✅ | `DreamfertiliterFallingParticle` |
| `dream_ambient_particle` | ✅ | `DreamAmbientParticle` |
| `leaves_particle` | ✅ | `LeavesParticle` |
| `calle_particle` | ✅ | `CalleParticle` |
| `silver_particle` | ✅ | `SilverParticle` |
| `crack_0_particle` | ✅ | `CrackParticle` |
| `white_star_particle` | ✅ | `WhiteStarParticle` |
| `snowflake_0_particle` | ✅ | `SnowflakeParticle` |
| `feather_white_particle` | ✅ | `FeatherWhiteParticle` |
| `dyedream_0_particle` | ✅ | `DyedreamParticle` |
| `spore_particle` | ❌ | **不存在** |
| `fox_fire_0_particle` | ❌ | **不存在** |
| `fox_fire_1_particle` | ❌ | **不存在** |

**影响**：这 4 个粒子在游戏中无法正常渲染，会显示为紫色缺失纹理方块。

---

## 11. 合规性审查

### 11.1 编码规范

| 规范 | 合规度 |
|------|:----:|
| PascalCase 类名 | ✅ 100% |
| camelCase 方法名 | ✅ 100% |
| UPPER_SNAKE_CASE 常量 | ✅ 100% |
| snake_case 注册名 | ✅ 100% |
| DeferredRegister 注册 | ✅ 100% |
| 类级 + 方法级注释 | ✅ 95% |

### 11.2 安全规范

- 无硬编码密码/密钥 ✅
- 无 eval/反射执行用户输入 ✅
- 文件路径使用 `java.nio.file.Path` API ✅
- 无 SQL 注入风险 ✅

---

## 12. 修复优先级矩阵

| 优先级 | 问题 | 文件/位置 | 分类 | 预计工作量 |
|:------:|------|----------|------|:--------:|
| **P0** | 补齐 4 个缺失粒子 Provider | 主模块 `ClientSetup` + 新建粒子类 | 运行时缺陷 | 30-60 分钟 |
| **P0** | 迁移/删除 `DimensionApiDemo` | `dimension/example/` | 服务端安全 | 10 分钟 |
| P1 | 迁移 Curio 弃用 API | `curio/CurioBuilder.java` | 兼容性 | 15 分钟 |
| P1 | 给 `CurioAPI.registerClientRenderers()` 加 side 校验 | `curio/CurioAPI.java` | 服务端安全 | 5 分钟 |
| P1 | `BatchBlockBuilder` / `VariantSetBuilder` 补写 `BLOCK_SUPPLIERS` | `block/builder/` | 逻辑缺陷 | 15 分钟 |
| P1 | 日志级别降级 + 移除 emoji | `BlockLootAPI` / `ParticleBuilder` / `EntityAPI` | 日志优化 | 30 分钟 |
| P1 | 实现 `PasterDreamAPI.registerAll(modEventBus)` | `PasterDreamAPI.java` | 统一入口 | 20 分钟 |
| P1 | 统一 PDParticles 全部走 ParticleAPI 并补缓存 | 主模块 `PDParticles.java` | 架构一致 | 30 分钟 |
| P2 | 查询方法统一返回 `Optional` | `EntityAPI` / `MobEffectAPI` / `ParticleAPI` | API 规范 | 30 分钟 |
| P2 | 添加 `resetForTesting()` | `EntityAPI` / `ParticleAPI` / `CurioAPI` | 测试支持 | 15 分钟 |
| P2 | 拆分 `ItemMigrationAPI` | `itemmigration/ItemMigrationAPI.java` | SRP | 1 小时 |
| P2 | `ApiSoundRegistry` 硬编码音乐文件校验 | `ApiSoundRegistry.java` | 健壮性 | 15 分钟 |
| P3 | 迁移 `example` / `gen` 到 test sourceSet | `block/example/` / `dimension/example/` / `itemmigration/example/` / `itemmigration/gen/` | 文件清理 | 20 分钟 |
| P3 | 删除 `compat` 包 | `compat/CompatLayer.java` / `compat/package-info.java` | 胶水代码 | 5 分钟 |

---

## 13. 审查统计数据

| 指标 | 数值 |
|------|:---:|
| 审查文件总数 | 73 |
| 与 README 重叠问题 | 8 |
| 本次新发现独立问题 | 10 |
| P0 级问题 | 2 |
| P1 级问题 | 6 |
| P2 级问题 | 4 |
| P3 级问题 | 2 |
| 编译错误 | 0 |
| 编译警告 | 1（Curio 弃用） |

---

*报告生成完毕。建议按 P0 → P1 → P2 → P3 顺序执行修复，每次修复后运行 `gradlew :PasterDreamAPI:compileJava` 验证。*
