# 开发指南

[English](./DEV-GUIDE.md) | 中文

## 技术栈

- Java 17
- Spring Boot 3.5.x
- Maven 多模块工程

## 模块结构

- `code-insight-boot`：Spring Boot 启动入口与运行时装配
- `code-insight-web`：Controller 层与 HTTP 适配
- `code-insight-main`：用例编排与业务服务
- `code-insight-dal`：数据访问实现
- `code-insight-model`：共享实体、DTO、VO

依赖方向：

`web -> main -> dal -> model`，以及 `boot -> web`

## 分包约定

每个模块内部优先按业务域分包，例如：

- `project/web`
- `project/main`
- `project/dal`
- `project/model`

跨域公共能力放在 `web/common` 或等价共享包中。

## 本地开发

执行全模块测试：

```bash
cd backend
mvn test
```

从 boot 模块启动应用：

```bash
cd backend
mvn -pl code-insight-boot -am spring-boot:run
```

代码格式（IntelliJ IDEA 风格）：

- 以 IntelliJ IDEA 的 `Reformat Code` 作为格式基准。
- 项目格式偏好由 `.editorconfig` 统一定义。
- 避免使用与 IntelliJ 风格冲突的外部格式化器。

## 编码规范

- Controller 不写业务逻辑
- Service（`main`）负责业务与事务边界
- DAL 只负责持久化相关逻辑
- Model 尽量保持轻框架依赖
- 请求 DTO 做参数校验，异常统一处理

## 测试规范

- 为 service 逻辑补充单元测试
- 为 controller 契约补充 web 层测试
- 在 boot 模块保留至少一个 context load 测试

## 文档规范

当你修改架构、模块边界或 API 契约时：

- 同一任务内同步更新中英文文档
- 确保示例命令可运行、路径引用准确
