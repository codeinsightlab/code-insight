package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

record ControllerCandidate(
    JavaSourceUnit sourceUnit,
    ClassOrInterfaceDeclaration declaration,
    String className,
    String packageName,
    String basePath,
    java.util.List<String> methods) {}
