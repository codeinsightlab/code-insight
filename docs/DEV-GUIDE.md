# Development Guide

English | [中文](./DEV-GUIDE.zh-CN.md)

## Stack

- Java 17
- Spring Boot 3.5.x
- Maven multi-module

## Module Layout

- `code-insight-boot`: Spring Boot entrypoint and runtime composition
- `code-insight-web`: Controller layer and HTTP concerns
- `code-insight-main`: Use-case orchestration and business services
- `code-insight-dal`: Data access implementations
- `code-insight-model`: Shared entities, DTOs, and VOs

Dependency direction:

`web -> main -> dal -> model`, and `boot -> web`

## Package Conventions

Inside each module, prefer domain-first packages. Example:

- `project/web`
- `project/main`
- `project/dal`
- `project/model`

Keep common cross-domain concerns under `web/common` or equivalent shared package.

## Local Development

Run tests for all modules:

```bash
cd backend
mvn test
```

Run app from boot module:

```bash
cd backend
mvn -pl code-insight-boot -am spring-boot:run
```

## Coding Guidelines

- Controller should not contain business logic
- Service (`main`) defines transaction/business boundaries
- DAL should only handle persistence concerns
- Model should stay framework-light when possible
- Add validation in request DTOs and handle errors centrally

## Testing Guidelines

- Add unit tests for service logic
- Add web tests for controller contracts
- Keep at least one context load test in boot module

## Documentation Guidelines

When changing architecture, module boundaries, or API contracts:

- Update English and Chinese docs in the same task
- Keep examples runnable and path references accurate
