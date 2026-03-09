# 项目阶段回顾

[English](./PROJECT-STATUS.md) | 中文

## 总览

本文档用于回顾 CodeInsight 当前交付阶段，并明确下一步执行重点。

## 已完成里程碑

1. 完成后端基础初始化：Java 17 + Spring Boot 3.5.x。
2. 完成后端 Maven 多模块重构：
   - `code-insight-boot`
   - `code-insight-web`
   - `code-insight-main`
   - `code-insight-dal`
   - `code-insight-model`
3. 固化依赖方向：
   - `web -> main -> dal -> model`
   - `boot -> web`
4. 完成 project 业务域基线链路：
   - controller + service + repository + model
   - 统一返回结构与全局异常处理
5. 完成工程化基线：
   - CI 工作流（`mvn test`）
   - Spotless 接入并采用 `google-java-format`
   - 格式检查与测试均通过
6. 完成双语文档基线：
   - `README`
   - `CONTRIBUTING`
   - `DEV-GUIDE`
   - `ARCHITECTURE`

## 当前阶段判断

当前阶段：**项目启动准备完成，进入功能实现阶段**。

### 工程准备度

- 构建能力：就绪
- 测试基线：就绪
- 代码风格约束：就绪
- 协作规范：就绪
- 架构基线：就绪

## 待完成 / 未开始项

1. 核心扫描能力尚未正式交付：
   - `project-scanner`
   - `controller-scanner`
   - `entity-scanner`
   - `report-generator`
2. 持久化方案尚未定版（当前为内存示例形态）。
3. API 契约文档（OpenAPI/Swagger）尚未接入。
4. 领域测试覆盖仍较少（service/controller 行为测试不足）。

## 下一阶段建议计划

1. 交付 `project-scanner` MVP，明确输入输出契约。
2. 交付 `controller-scanner` MVP，完成端点提取模型。
3. 在 `code-insight-model` 中统一 scanner 结果模型与响应载荷。
4. 为 scanner 服务与 API 增补针对性单元测试 / Web 测试。
5. 在首批 scanner API 稳定后引入 OpenAPI 文档。

## 下一阶段验收标准

满足以下条件即可判定该阶段完成：

- scanner API 可以端到端调用，
- 输出结构化且有文档说明，
- CI 测试通过，
- 中英文文档在同一任务内同步更新。
