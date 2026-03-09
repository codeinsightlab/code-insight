package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

record EntityCandidate(
    JavaSourceUnit sourceUnit,
    ClassOrInterfaceDeclaration declaration,
    String className,
    String tableName) {}
