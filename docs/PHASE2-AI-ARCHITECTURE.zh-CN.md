# CodeInsight Phase 2 — AI 分析引擎架构

## 概览

Phase 2 引入 CodeInsight 的 **AI 分析能力**。

如果说 Phase 1 关注的是从 Java/Spring Boot 项目中提取结构化信息，
那么 Phase 2 关注的是使用 AI 对项目进行**理解与分析**。

目标是提供有工程价值的洞察，例如：

- 项目摘要
- 架构说明
- API 能力总览
- 结构风险分析
- 交互式项目问答

本阶段建立在扫描引擎产出的结构化数据之上。

---

# 架构总览

处理管线：

Scanner Engine (Phase 1)
↓
Structured Project Model
↓
Analysis Context Builder
↓
AI Analysis Orchestrator
├── Project Summary Analyzer
├── Architecture Analyzer
├── Endpoint Analyzer
├── Risk Analyzer
└── Q&A Analyzer
↓
Prompt Template Engine
↓
LLM Gateway
↓
Analysis Result Formatter
↓
API / Web Layer

---

# 核心组件

## 1. Structured Project Model（结构化项目模型）

目的：

提供统一的数据结构来表达扫描后的项目。

输入：

- controllers
- endpoints
- entities
- fields
- javaFiles
- statistics

输出：

ProjectAnalysisContext

该模型将作为 **AI 分析任务的输入**。

---

## 2. Analysis Context Builder（分析上下文构建器）

目的：

为不同分析任务准备 AI 友好的上下文。

职责：

- 减少无关数据
- 构建结构化 Prompt 上下文
- 准备任务特定输入

上下文类型：

### Project Overview Context

用于：

- 项目摘要
- 技术栈描述
- 模块说明

### API Context

用于：

- 接口分组
- API 能力总结

### Entity Context

用于：

- 数据模型说明
- 核心领域识别

### Risk Context

用于：

- 架构风险
- 结构不一致性

---

## 3. AI Analysis Orchestrator（AI 分析编排器）

目的：

协调各类 AI 分析任务。

职责：

- 选择 analyzer
- 构建 prompt
- 调用 LLM
- 处理结果

子模块：

### ProjectSummaryAnalyzer

输出：

- 项目描述
- 核心功能
- 系统概览

---

### ArchitectureAnalyzer

输出：

- 架构说明
- 模块结构
- 设计模式提示

---

### EndpointAnalyzer

输出：

- API 分类
- 服务能力描述
- 领域分组

---

### RiskAnalyzer

输出：

- 结构性风险
- 架构建议
- 命名不一致
- 潜在设计问题

---

### QAAnalyzer

基于项目结构提供交互式问答能力。

示例问题：

- 这个项目是做什么的？
- 订单管理相关有哪些 API？
- 哪些实体代表核心领域对象？

---

## 4. Prompt Template Engine（Prompt 模板引擎）

目的：

管理可复用 Prompt 模板。

模板分类：

- project-summary
- architecture-analysis
- endpoint-analysis
- risk-analysis
- qa-analysis

每个模板应定义：

- system instruction
- task description
- expected output format

---

## 5. LLM Gateway

目的：

封装所有 LLM 提供方集成。

职责：

- 模型调用
- 超时处理
- 异常处理
- 日志记录

该抽象可支持后续灵活切换 LLM 提供方。

---

## 6. Analysis Result Formatter（分析结果格式化器）

目的：

将原始 AI 输出转换为结构化结果。

输出格式示例：

- title
- summary
- bulletPoints
- risks
- suggestions

---

# Phase 2 开发任务

Task 1: Project Analysis Context Builder

Task 2: Prompt Template Engine

Task 3: LLM Gateway

Task 4: Project Summary Analyzer

Task 5: Architecture Analyzer

Task 6: Endpoint Analyzer

Task 7: Risk Analyzer

Task 8: Analysis API

---

# Phase 2 完成标准

满足以下条件即可判定 Phase 2 完成：

- 能生成项目摘要
- 可提供架构说明
- API 能力总结可用
- 可识别结构风险
- 分析 API 可用
