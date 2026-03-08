# Contributing

English | [中文](./CONTRIBUTING.zh-CN.md)

## Collaboration Workflow

Use this request template when opening a task:

- Goal: what outcome is expected
- Scope: what is in and out of this task
- Acceptance Criteria: measurable checks for completion
- Delivery: commit only, or commit + push
- Deadline/Priority: expected timeline

## Branch and Commit Rules

- Branch naming: `feature/<topic>`, `fix/<topic>`, `chore/<topic>`
- Commit style: Conventional Commits (for example: `feat: add project scanner api`)
- Keep one logical change per commit

## Definition of Done

Before marking a task done:

- Build and tests pass locally (`mvn test` from `backend/`)
- Required docs are updated in both English and Chinese
- API/behavior changes are reflected in `docs/`

## Communication Rules

- The assistant should execute directly unless a risky decision is needed
- If assumptions are made, list them explicitly in the final summary
- If blocked, report blocker + proposed fallback

## Documentation Policy

For every Markdown doc change, keep bilingual versions aligned:

- English: `<name>.md`
- Chinese: `<name>.zh-CN.md`

For this repo, at minimum keep these synced:

- `docs/ARCHITECTURE.md` and `docs/ARCHITECTURE.zh-CN.md`
- `CONTRIBUTING.md` and `CONTRIBUTING.zh-CN.md`
- `docs/DEV-GUIDE.md` and `docs/DEV-GUIDE.zh-CN.md`
