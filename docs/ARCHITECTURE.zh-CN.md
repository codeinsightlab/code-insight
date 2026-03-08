# CodeInsight 架构说明

[English](./ARCHITECTURE.md) | 中文

CodeInsight 是一个用于分析 Java/Spring Boot 项目的 AI 工具。

## 目标

- 扫描 Java 项目
- 提取 API 端点
- 提取实体定义
- 生成架构洞察报告

## 后端工程模块（Maven）

- `code-insight-boot`：应用启动与运行时装配
- `code-insight-web`：REST 控制器、请求/响应模型、全局异常处理
- `code-insight-main`：应用服务与业务编排
- `code-insight-dal`：数据访问层（repository/mapper）
- `code-insight-model`：共享领域模型（entity/dto/vo）

依赖方向：

`web -> main -> dal -> model`，以及 `boot -> web`

## 核心业务能力模块

- `project-scanner`
- `controller-scanner`
- `entity-scanner`
- `report-generator`

## 技术栈

- Java 17
- Spring Boot 3.5.x
- JavaParser
