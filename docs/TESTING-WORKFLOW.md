# CodeInsight Testing Workflow

This document defines the testing workflow when using AI coding agents such as Codex.

The goal is to ensure that all changes:
- remain stable
- do not break existing functionality
- pass automated verification before review

This workflow must be followed for every implementation task.

---

# Testing Philosophy

Testing is organized in four layers:

1. Unit Tests
2. API Tests
3. Example Project Validation
4. CI Pipeline

Each layer validates a different aspect of the system.

AI agents must run tests before finishing any task.

---

# Layer 1: Unit Tests

Unit tests validate core logic.

Every scanner module must include unit tests.

Required test classes:

ProjectScannerTest  
JavaSourceLoaderTest  
ControllerScannerTest  
EndpointScannerTest  
EntityScannerTest  
FieldScannerTest  
ProjectModelBuilderTest  
ReportGeneratorTest  

Unit tests should verify:

- annotation detection
- endpoint path extraction
- entity recognition
- field parsing
- edge cases
- invalid input handling

Unit tests must pass before any feature is considered complete.

---

# Layer 2: API Tests

API tests validate the web layer.

Example test:

ScanControllerTest

These tests must verify:

- HTTP status code
- response structure
- JSON fields
- statistics correctness

Example validations:

status = 200  
success = true  
message = "OK"

---

# Layer 3: Example Project Validation

The repository contains a demo project:

examples/demo-project

This project acts as a regression test dataset.

The demo project must contain:

- multiple controllers
- multiple endpoints
- at least one entity

After implementing scanner logic, the agent must run a scan on this project and verify:

controllerCount
endpointCount
entityCount
javaFileCount

If the counts change unexpectedly, the implementation must be reviewed.

---

# Layer 4: CI Pipeline

All commits must pass CI.

CI automatically runs:

mvn spotless:check  
mvn test  

If any step fails, the task is not complete.

---

# Codex Self-Testing Procedure

Before finishing a task, Codex must execute the following steps:

1. List all modified files
2. Run formatting checks
3. Run all tests
4. Fix failing tests
5. Verify example project scan
6. Output test results

Minimum success criteria:

- formatting check passes
- all tests pass
- example project scan works

---

# Developer Review

After tests pass, the human developer reviews:

- architecture consistency
- module responsibilities
- model structure
- unnecessary dependencies

Only then should the change be committed.

---

# Development Loop

Every feature follows this loop:

1. Define task
2. Implement feature
3. Add tests
4. Run tests
5. Verify example project
6. Human review
7. Commit and CI verification

This ensures the system remains stable as the project grows.
