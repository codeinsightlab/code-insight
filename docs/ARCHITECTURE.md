# CodeInsight Architecture

English | [中文](./ARCHITECTURE.zh-CN.md)

CodeInsight is an AI-powered tool that analyzes Java/Spring Boot projects.

## Goals

- Scan Java projects
- Extract API endpoints
- Extract entities
- Generate architecture insights

## Backend Project Modules (Maven)

- `code-insight-boot`: application bootstrap and runtime assembly
- `code-insight-web`: REST controllers, request/response models, global exception handling
- `code-insight-main`: application services and business orchestration
- `code-insight-dal`: data access layer (repository/mapper)
- `code-insight-model`: shared domain models (entity/dto/vo)

Dependency direction:

`web -> main -> dal -> model`, and `boot -> web`

## Core Business Capabilities

- `project-scanner`
- `controller-scanner`
- `entity-scanner`
- `report-generator`

## Tech Stack

- Java 17
- Spring Boot 3.5.x
- JavaParser
