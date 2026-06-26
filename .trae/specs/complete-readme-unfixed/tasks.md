# Tasks

- [ ] Task 1: 日志降噪
  - [ ] SubTask 1.1: 扫描 PasterDreamAPI 与 PasterDream 中所有 INFO 级别注册流程日志
  - [ ] SubTask 1.2: 将非关键注册日志降级为 DEBUG，仅保留启动 banner 与异常摘要为 INFO
  - [ ] SubTask 1.3: 编译验证无日志 API 误用

- [ ] Task 2: 删除 CompatLayer 兼容层
  - [ ] SubTask 2.1: 删除 `PasterDreamAPI/src/main/java/com/pasterdream/pasterdreammod/api/compat/` 包
  - [ ] SubTask 2.2: 全局搜索并移除对 `CompatLayer` 的引用
  - [ ] SubTask 2.3: 编译验证

- [ ] Task 3: 静态缓存可清理
  - [ ] SubTask 3.1: 识别持有静态缓存的核心 API/注册类（如 BlockAPI、EntityAPI、ParticleAPI、ItemAPI 等）
  - [ ] SubTask 3.2: 为每个类新增 `resetForTesting()` 方法，清空静态缓存
  - [ ] SubTask 3.3: 编译验证

- [ ] Task 4: 空值查询显式化
  - [ ] SubTask 4.1: 扫描公开 API 中返回 null 的查询方法
  - [ ] SubTask 4.2: 改为返回 `Optional<T>` 或添加 `@Nullable` 注解
  - [ ] SubTask 4.3: 更新调用方代码，编译验证

- [ ] Task 5: ItemMigrationAPI 重命名为 ItemAPI
  - [ ] SubTask 5.1: 将包 `api/itemmigration/` 重命名为 `api/item/`
  - [ ] SubTask 5.2: 将 `ItemMigrationAPI` 重命名为 `ItemAPI`
  - [ ] SubTask 5.3: 同步更新内部 Builder、Manager、Generator 类名与引用
  - [ ] SubTask 5.4: 更新 `PDItems.java` 等主模块引用
  - [ ] SubTask 5.5: 编译验证

- [x] Task 6: example / gen 包迁移
  - [x] SubTask 6.1: 梳理 `api/*/example/` 与 `api/*/gen/` 目录清单
  - [x] SubTask 6.2: 决定每类代码归属：测试源码集或独立工具模块
  - [x] SubTask 6.3: 移动文件并更新包声明与 import
  - [x] SubTask 6.4: 调整 build.gradle sourceSets（如需）
  - [x] SubTask 6.5: 编译验证

- [ ] Task 7: 补齐 BlockEntity/Menu/Fluid API
  - [ ] SubTask 7.1: 创建 `BlockEntityAPI` facade 与 `BlockEntityBuilder`
  - [ ] SubTask 7.2: 创建 `MenuAPI` facade 与 `MenuBuilder`
  - [ ] SubTask 7.3: 创建 `FluidAPI` facade 与 `FluidBuilder`
  - [ ] SubTask 7.4: 在 `PasterDreamAPI.registerAll(...)` 中注册新的 DeferredRegister
  - [ ] SubTask 7.5: 编译验证

- [x] Task 8: 更新 README 修复进度
  - [x] SubTask 8.1: 将本次修复的 7 项状态改为“已修复”
  - [x] SubTask 8.2: 最终全量编译验证

# Task Dependencies
- Task 5 depends on Task 4（避免同时大规模修改查询 API 与命名）
- Task 6 可与 Task 2、Task 3 并行
- Task 7 与 Task 1-6 无强依赖，可并行
- Task 8 依赖于 Task 1-7 全部完成
