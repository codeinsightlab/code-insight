# CodeInsight

English | [中文](./README.zh-CN.md)

CodeInsight is an AI-powered analysis tool for Java/Spring Boot projects.

## Current Status

- Backend is initialized as a Maven multi-module project
- Module layout is stable and buildable
- Core scanner capabilities are in planning and implementation stage

## Backend Modules

- `code-insight-boot`
- `code-insight-web`
- `code-insight-main`
- `code-insight-dal`
- `code-insight-model`

Dependency direction:

`web -> main -> dal -> model`, and `boot -> web`

## Quick Start

```bash
cd backend
mvn test
mvn -pl code-insight-boot -am spring-boot:run
```

## Docs

- [Architecture](./docs/ARCHITECTURE.md)
- [Development Guide](./docs/DEV-GUIDE.md)
- [Contributing](./CONTRIBUTING.md)
