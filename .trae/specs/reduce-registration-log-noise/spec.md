# 日志降噪 Spec

## Why
模组启动与注册阶段产生大量 `INFO` 级别日志，淹没真正需要关注的启动横幅与异常摘要。通过将非关键注册流程日志降级为 `DEBUG`，可以显著降低玩家与开发者的日志噪音。

## What Changes
- 搜索 `PasterDreamAPI` 与 `PasterDream` 模块中所有与注册流程相关的 `LOGGER.info()` / `logger.info()` 调用。
- 将非关键的实体、方块、粒子、状态效果、维度、遗迹、物品迁移等注册流程日志从 `INFO` 降级为 `DEBUG`。
- 保留启动横幅、致命错误以及显式作为汇总的注册数量统计日志在 `INFO` 级别。

## Impact
- 受影响模块：`PasterDreamAPI`、`PasterDream`
- 受影响文件：注册相关的 API、Builder、Generator、Manager 类
- 不改变游戏行为，仅调整日志级别

## ADDED Requirements
### Requirement: 注册流程日志降噪
The system SHALL downgrade non-critical registration flow logs from `INFO` to `DEBUG`.

#### Scenario: 注册日志降低
- **WHEN** 模组启动并执行 DeferredRegister / Builder / Generator 注册流程
- **THEN** 单个注册项的创建、缓存、构建完成等细节日志仅在 `DEBUG` 级别输出，不会出现在默认 `INFO` 日志中

#### Scenario: 关键日志保留
- **WHEN** 模组启动或发生异常
- **THEN** 启动横幅、错误/异常摘要等用户需要关注的信息仍保留在 `INFO` 级别

## MODIFIED Requirements
无现有需求修改，仅调整日志级别实现。

## REMOVED Requirements
无需求移除。
