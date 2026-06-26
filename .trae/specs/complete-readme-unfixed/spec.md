# README 遗留修复项 Spec

## Why
`README.md` 的 API 架构修复进度表中仍有 7 项未修复/未进行，涉及日志噪音、废弃兼容层、测试可维护性、API 命名一致性、示例代码归属和 API 覆盖度。本次变更一次性完成这些遗留项，使修复进度表全部闭环。

## What Changes
- **日志降噪**：将 API 与主模块中高频 INFO 日志统一降级为 DEBUG，仅保留启动 banner 与异常摘要为 INFO。
- **删除 `CompatLayer`**：移除已标记 `@Deprecated(forRemoval = true)` 的兼容层源码包 `api/compat/`。
- **静态缓存可清理**：在持有静态缓存的核心 API/注册类中新增 `resetForTesting()`，便于单元测试隔离状态。
- **空值返回显式化**：将公开 API 中返回 `null` 的查询方法改为返回 `Optional<T>` 或标注 `@Nullable`。
- **`ItemMigrationAPI` 重命名**：将 `itemmigration` 包及 `ItemMigrationAPI` 重命名为 `item`/ `ItemAPI`，同步修改引用处。
- **示例/生成工具包迁移**：将 `api/*/example/` 与 `api/*/gen/` 下的演示/生成器代码迁移到 `src/test/java` 或独立工具模块，避免打包进正式产物。
- **补齐 API 覆盖**：按需新增 `BlockEntityAPI`、`MenuAPI`、`FluidAPI` 三个 facade，保持与现有 `BlockAPI`/`EntityAPI` 一致的 Builder/Facade 风格。

## Impact
- Affected specs: API 设计一致性、日志策略、测试隔离性、命名规范、包结构规范
- Affected code:
  - `PasterDreamAPI/src/main/java/com/pasterdream/pasterdreammod/api/`
  - `PasterDream/src/main/java/com/pasterdream/pasterdreammod/registry/PDItems.java`
  - `PasterDreamAPI/build.gradle` / `PasterDream/build.gradle`
  - `README.md`

## ADDED Requirements
### Requirement: 日志降噪
The system SHALL 将非关键注册流程日志输出级别从 INFO 降至 DEBUG，且仅保留启动 banner 与异常摘要使用 INFO。

#### Scenario: 正常运行
- **WHEN** 模组初始化并执行注册流程
- **THEN** 控制台不再出现大量 INFO 级别注册日志

### Requirement: 静态缓存可清理
The system SHALL 为持有静态缓存的 API/注册类提供 `resetForTesting()` 方法，供测试或重载场景清空状态。

#### Scenario: 单元测试隔离
- **WHEN** 测试用例调用 `resetForTesting()`
- **THEN** 静态缓存回到初始空状态

### Requirement: 空值查询显式化
The system SHALL 对可能返回 null 的公开查询方法返回 `Optional<T>` 或标注 `@Nullable`。

#### Scenario: 使用查询 API
- **WHEN** 调用方查询不存在的注册项
- **THEN** 通过 `Optional.empty()` 或 `@Nullable` 明确感知空值

### Requirement: BlockEntity/Menu/Fluid API 补齐
The system SHALL 提供 `BlockEntityAPI`、`MenuAPI`、`FluidAPI` 三个 facade，支持基本注册入口。

#### Scenario: 新增 BlockEntity/Menu/Fluid
- **WHEN** 开发者调用 `BlockEntityAPI.register(...)` / `MenuAPI.register(...)` / `FluidAPI.register(...)`
- **THEN** 对应 DeferredRegister 完成注册并可通过 facade 查询

## MODIFIED Requirements
### Requirement: API 命名一致性
`ItemMigrationAPI` 及其包名应统一为 `ItemAPI`/`item`，所有内部 Builder、Manager、Generator 引用同步更新。

## REMOVED Requirements
### Requirement: `CompatLayer` 兼容层
**Reason**: 已标记 `@Deprecated(forRemoval = true)`，README 明确要删除。
**Migration**: 删除包 `api/compat/`，检查主模块是否有引用并同步移除。

### Requirement: `example` / `gen` 包随主产物打包
**Reason**: 演示代码与生成器不应进入发布产物。
**Migration**: 移动到 `src/test/java` 或独立工具模块，保留功能但变更源码集归属。
