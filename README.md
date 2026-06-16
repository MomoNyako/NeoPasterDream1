# PasterDream — API 架构修复进度

## 目前已知问题

| 优先级    | 问题                  | 建议     | 修复状态              |      
| ------ | ------------------- |----------------------------|-------------------|
| **P0** | CurioAPI 引用客户端类     | 用 `Supplier<ICurioRenderer>` 延迟加载，或拆分 client/common | 计划中               |
| **P0** | 双重注册残留              | 移除旧 `MOB_EFFECTS` 和 `STRUCTURE_TYPES` 注册器           | 未进行               |
| **P1** | 缺少统一注册入口            | 新增 `PasterDreamAPI.registerAll(modEventBus)`        | 未进行               |
| **P1** | 日志过多                | 全部降为 `debug`，仅保留启动 banner 为 `info`                  | 未进行               |
| **P1** | 粒子新旧混用              | 迁移剩余 7 个旧式粒子到 ParticleAPI                           | 未进行               |
| **P2** | 静态缓存无清理             | 新增 `resetForTesting()` 方法                           | 未进行               |
| **P2** | 查询返回 null           | 改为 `Optional` 或加 `@Nullable`                        | 未进行               |
| **P2** | ItemMigrationAPI 命名 | 重命名为 `ItemAPI`                                      | 未进行               |
| **P3** | example 包打包         | 移到 test sourceSet                                   | 未进行               |
| **P3** | API 覆盖不完整           | 按需为 BlockEntity/Menu/Fluid 等添加 API                  | 未进行               |
