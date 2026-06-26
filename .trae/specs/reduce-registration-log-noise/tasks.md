# Tasks

- [ ] Task 1: 搜索并审阅注册流程中的 INFO 日志
  - [ ] SubTask 1.1: 在 PasterDreamAPI 与 PasterDream 模块中搜索所有 LOGGER.info() / logger.info() 调用
  - [ ] SubTask 1.2: 逐条判断日志是否属于非关键注册流程，记录待降级位置
- [ ] Task 2: 将非关键注册流程日志从 INFO 改为 DEBUG
  - [ ] SubTask 2.1: 修改 MobEffectAPI 中相关日志
  - [ ] SubTask 2.2: 修改 MobEffectBuilder 中相关日志
  - [ ] SubTask 2.3: 修改 EntityAPI 中相关日志
  - [ ] SubTask 2.4: 修改 ParticleAPI 中相关日志
  - [ ] SubTask 2.5: 修改 DimensionAPI / DimensionBuilder 中相关日志
  - [ ] SubTask 2.6: 修改 RuinAPI / RuinBuilder / 遗迹生成器类中相关日志
  - [ ] SubTask 2.7: 修改 BlockLootAPI / LootTableGenerator 中相关日志
  - [ ] SubTask 2.8: 修改 MigrationManager / 物品迁移相关类中相关日志
  - [ ] SubTask 2.9: 修改 ApiSoundRegistry / EntitySkillManager / StructureTerrainNegotiator 中相关日志
- [ ] Task 3: 编译验证
  - [ ] SubTask 3.1: 运行 PowerShell 命令：.\gradlew :PasterDreamAPI:compileJava :PasterDream:compileJava
  - [ ] SubTask 3.2: 确认两个模块均 BUILD SUCCESSFUL
