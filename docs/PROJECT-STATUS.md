# Project Status

English | [中文](./PROJECT-STATUS.zh-CN.md)

## Overview

This document summarizes the current delivery stage of CodeInsight and defines the immediate next execution focus.

## Completed Milestones

1. Backend foundation initialized with Java 17 + Spring Boot 3.5.x.
2. Backend refactored to Maven multi-module architecture:
   - `code-insight-boot`
   - `code-insight-web`
   - `code-insight-main`
   - `code-insight-dal`
   - `code-insight-model`
3. Dependency direction stabilized:
   - `web -> main -> dal -> model`
   - `boot -> web`
4. Baseline API chain implemented for project domain:
   - controller + service + repository + model flow
   - unified response and global exception handling
5. Engineering baseline established:
   - CI workflow (`mvn test`)
   - Spotless integrated with `google-java-format`
   - formatting and test verification passed
6. Documentation baseline completed in bilingual form:
   - `README`
   - `CONTRIBUTING`
   - `DEV-GUIDE`
   - `ARCHITECTURE`

## Current Stage

Current stage: **Project bootstrap complete, entering feature implementation stage**.

### Engineering Readiness

- Build: Ready
- Test baseline: Ready
- Code style enforcement: Ready
- Collaboration conventions: Ready
- Architecture baseline: Ready

## Pending / Not Yet Started

1. Core scanner implementation is not yet delivered:
   - `project-scanner`
   - `controller-scanner`
   - `entity-scanner`
   - `report-generator`
2. Persistent storage strategy is not finalized (currently in-memory example style).
3. API contract documentation (OpenAPI/Swagger) is not yet introduced.
4. Domain test coverage (service/controller behavior tests) is still minimal.

## Next Phase Plan (Recommended)

1. Deliver `project-scanner` MVP with clear input/output contract.
2. Deliver `controller-scanner` MVP and endpoint extraction model.
3. Define scanner result model in `code-insight-model` and standardize response payload.
4. Add focused unit/web tests for scanner services and APIs.
5. Add API contract docs (OpenAPI) after first scanner API stabilizes.

## Acceptance for Next Phase

A phase is considered complete when:

- scanner APIs are callable end-to-end,
- outputs are structured and documented,
- tests pass in CI,
- English/Chinese docs are updated in the same task.
