# CodeInsight 测试工作流

本文档定义了在使用 Codex 等 AI 编码代理时的测试工作流。

目标是确保所有改动：
- 保持稳定
- 不破坏现有功能
- 在评审前通过自动化验证

该工作流适用于每一项实现任务。

---

# 测试理念

测试分为四层：

1. 单元测试
2. API 测试
3. 示例项目验证
4. CI 流水线

每一层关注系统的不同侧面。

AI 代理在完成任务前必须执行测试。

---

# 第一层：单元测试

单元测试用于验证核心逻辑。

每个 scanner 模块都必须有单元测试。

要求的测试类：

ProjectScannerTest  
JavaSourceLoaderTest  
ControllerScannerTest  
EndpointScannerTest  
EntityScannerTest  
FieldScannerTest  
ProjectModelBuilderTest  
ReportGeneratorTest  

单元测试应验证：

- 注解识别
- endpoint 路径提取
- entity 识别
- 字段解析
- 边界场景
- 非法输入处理

在任何功能被视为完成前，单元测试必须通过。

---

# 第二层：API 测试

API 测试用于验证 Web 层。

示例测试：

ScanControllerTest

这些测试必须验证：

- HTTP 状态码
- 响应结构
- JSON 字段
- 统计值正确性

示例断言：

status = 200  
success = true  
message = "OK"

---

# 第三层：示例项目验证

仓库中包含一个示例项目：

examples/demo-project

该项目作为回归测试数据集。

示例项目应包含：

- 多个 controller
- 多个 endpoint
- 至少一个 entity

在实现 scanner 逻辑后，代理必须对该项目执行扫描并验证：

controllerCount
endpointCount
entityCount
javaFileCount

如果计数出现非预期变化，必须复审实现。

---

# 第四层：CI 流水线

所有提交必须通过 CI。

CI 自动执行：

mvn spotless:check  
mvn test  

任一步骤失败，任务都不算完成。

---

# Codex 自检流程

在结束任务前，Codex 必须执行以下步骤：

1. 列出所有修改文件
2. 运行格式检查
3. 运行所有测试
4. 修复失败测试
5. 验证示例项目扫描
6. 输出测试结果

最低成功标准：

- 格式检查通过
- 全部测试通过
- 示例项目扫描可用

---

# 开发者评审

测试通过后，由人工开发者评审：

- 架构一致性
- 模块职责边界
- 模型结构
- 不必要依赖

仅在完成上述评审后才应提交改动。

---

# 开发循环

每个功能都遵循如下循环：

1. 定义任务
2. 实现功能
3. 增加测试
4. 运行测试
5. 验证示例项目
6. 人工评审
7. 提交并通过 CI 验证

这样可以在项目持续演进时保持系统稳定。
