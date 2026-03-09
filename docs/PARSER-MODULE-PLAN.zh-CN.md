# CodeInsight Parser 模块架构

## 概览

Parser 模块是 CodeInsight 的**静态分析核心**。

职责：

- 扫描项目目录
- 加载 Java 源文件
- 解析 Java AST
- 提取 Controller
- 提取 Endpoint
- 提取 Entity
- 构建项目模型

该模块不负责：

- Web API
- AI 分析
- 持久化
- UI 逻辑

---

# 模块结构

推荐 Maven 模块：

code-insight-parser

目录结构：

src/main/java/...

api/
core/
scanner/
parser/
model/
support/
exception/

---

# 包职责

## api

向外部模块暴露 parser 能力。

示例：

- ProjectScanFacade
- ControllerScanFacade

外部模块应仅通过这一层调用 parser。

---

## core

定义核心抽象。

示例：

- Scanner 接口
- ScanContext
- ScanResult
- ParserEngine

---

## scanner

包含具体扫描器实现。

子包：

project/
source/
controller/
endpoint/
entity/
field/

职责：

- 识别注解
- 提取结构化信息

---

## parser

底层解析工具。

子包：

ast/
annotation/
mapping/

职责：

- AST 解析
- 注解识别
- Request Mapping 解析

---

## model

包含 parser 数据模型。

子包：

raw/
scan/
report/

### raw

底层模型：

- JavaSourceUnit
- ParsedClass
- ParsedMethod

### scan

扫描输出模型：

- ProjectStructure
- ControllerInfo
- EndpointInfo
- EntityInfo
- FieldInfo
- ProjectModel

### report

报告模型：

- ProjectStatistics
- ProjectReport

---

## support

工具类。

示例：

- 文件工具
- 路径辅助
- 文本辅助
- 集合工具

该层不应放置业务逻辑。

---

## exception

Parser 专用异常。

示例：

- SourceLoadException
- AstParseException
- ScannerException

---

# Parser 处理流程

ProjectScanFacade
↓
ProjectScanner
↓
JavaSourceLoader
↓
ControllerScanner / EntityScanner
↓
EndpointScanner / FieldScanner
↓
ProjectModelBuilder
↓
ProjectReportGenerator

---

# 开发任务

Task 1: define core abstractions

Task 2: implement project scanner

Task 3: implement Java source loader

Task 4: implement AST parser wrapper

Task 5: implement controller scanner

Task 6: implement endpoint scanner

Task 7: implement entity scanner

Task 8: implement field scanner

Task 9: implement project model builder

Task 10: implement report generator

---

# 设计原则

1. 扫描器模块必须保持独立。
2. Parser 应保持只读。
3. 所有输出应使用统一模型。
4. 每个扫描器都应可独立测试。
