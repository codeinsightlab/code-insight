# 贡献与协作规范

[English](./CONTRIBUTING.md) | 中文

## 协作流程

发起任务时建议使用以下模板：

- 目标（Goal）：期望达成的结果
- 范围（Scope）：本次任务包含/不包含的内容
- 验收标准（Acceptance Criteria）：可检查的完成条件
- 交付方式（Delivery）：仅提交 commit，或 commit + push
- 时间/优先级（Deadline/Priority）：期望完成时间

## 分支与提交规则

- 分支命名：`feature/<topic>`、`fix/<topic>`、`chore/<topic>`
- 提交规范：Conventional Commits（例如：`feat: add project scanner api`）
- 一个 commit 尽量只包含一个逻辑变更

## 完成定义（Definition of Done）

任务完成前需要满足：

- 本地构建与测试通过（在 `backend/` 目录执行 `mvn test`）
- 涉及的文档中英文同步更新
- API 或行为变化同步到 `docs/`

## 沟通规则

- 默认直接执行；只有在高风险决策时再确认
- 若存在假设，在最终说明中明确列出
- 如被阻塞，需说明阻塞点和备选方案

## 文档双语策略

所有 Markdown 文档更新默认保持中英双版本同步：

- 英文：`<name>.md`
- 中文：`<name>.zh-CN.md`

本仓库至少保持以下文件同步：

- `docs/ARCHITECTURE.md` 与 `docs/ARCHITECTURE.zh-CN.md`
- `CONTRIBUTING.md` 与 `CONTRIBUTING.zh-CN.md`
- `docs/DEV-GUIDE.md` 与 `docs/DEV-GUIDE.zh-CN.md`
