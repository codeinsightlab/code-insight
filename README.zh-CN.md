# CodeInsight

[English](./README.md) | 中文

CodeInsight 是一个用于分析 Java/Spring Boot 项目的 AI 工具。

## 当前状态

- 后端已初始化为 Maven 多模块工程
- 模块结构已稳定并可构建
- 核心扫描能力处于规划与实现阶段

## 后端模块

- `code-insight-boot`
- `code-insight-web`
- `code-insight-main`
- `code-insight-dal`
- `code-insight-model`

依赖方向：

`web -> main -> dal -> model`，以及 `boot -> web`

## 快速开始

```bash
cd backend
mvn test
mvn -pl code-insight-boot -am spring-boot:run
```

## 文档

- [架构说明](./docs/ARCHITECTURE.zh-CN.md)
- [开发指南](./docs/DEV-GUIDE.zh-CN.md)
- [贡献规范](./CONTRIBUTING.zh-CN.md)
