* [x] 日志降噪：非关键注册流程日志已统一为 DEBUG，启动 banner 与异常摘要保留 INFO

* [x] CompatLayer 兼容层源码已完全删除且无残留引用

* [x] 持有静态缓存的核心 API/注册类已提供 `resetForTesting()`

* [x] 公开查询方法的空值返回已改为 `Optional<T>` 或标注 `@Nullable`

* [x] `ItemMigrationAPI` 已重命名为 `ItemAPI`，包路径同步改为 `api/item/`

* [x] `example` / `gen` 包已迁移到 test sourceSet 或独立工具模块，不再进入主产物

* [x] 已新增 `BlockEntityAPI`、`MenuAPI`、`FluidAPI` 三个 facade 并接入 `PasterDreamAPI.registerAll(...)`

* [x] `README.md` 修复进度表对应 7 项状态已更新为“已修复”

* [x] 全量编译通过：`PasterDreamAPI:compileJava` 与 `PasterDream:compileJava`
