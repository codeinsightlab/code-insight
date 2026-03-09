# CodeInsight Parser Module Architecture

## Overview

The parser module is the **static analysis core** of CodeInsight.

Responsibilities:

- scan project directories
- load Java source files
- parse Java AST
- extract controllers
- extract endpoints
- extract entities
- construct project models

This module does NOT handle:

- web APIs
- AI analysis
- persistence
- UI logic

---

# Module Structure

Recommended Maven module:

code-insight-parser

Directory structure:

src/main/java/...

api/
core/
scanner/
parser/
model/
support/
exception/

---

# Package Responsibilities

## api

Expose parser capabilities to external modules.

Examples:

- ProjectScanFacade
- ControllerScanFacade

External modules should call the parser only through this layer.

---

## core

Define core abstractions.

Examples:

- Scanner interface
- ScanContext
- ScanResult
- ParserEngine

---

## scanner

Contains concrete scanners.

Subpackages:

project/
source/
controller/
endpoint/
entity/
field/

Responsibilities:

- detect annotations
- extract structural information

---

## parser

Low-level parsing utilities.

Subpackages:

ast/
annotation/
mapping/

Responsibilities:

- AST parsing
- annotation detection
- request mapping parsing

---

## model

Contains parser data models.

Subpackages:

raw/
scan/
report/

### raw

Low-level models:

- JavaSourceUnit
- ParsedClass
- ParsedMethod

### scan

Scanner output models:

- ProjectStructure
- ControllerInfo
- EndpointInfo
- EntityInfo
- FieldInfo
- ProjectModel

### report

Report models:

- ProjectStatistics
- ProjectReport

---

## support

Utility classes.

Examples:

- file utilities
- path helpers
- text helpers
- collection utilities

No business logic should be placed here.

---

## exception

Parser-specific exceptions.

Examples:

- SourceLoadException
- AstParseException
- ScannerException

---

# Parser Processing Pipeline

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

# Development Tasks

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

# Design Principles

1. Scanner modules must remain independent.
2. Parser should remain read-only.
3. All outputs should use unified models.
4. Each scanner must be independently testable.
