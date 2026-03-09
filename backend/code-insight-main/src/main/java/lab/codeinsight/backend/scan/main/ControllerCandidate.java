package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

/**
 * Internal candidate model for controller discovery pipeline.
 */
record ControllerCandidate(JavaSourceUnit sourceUnit, ClassOrInterfaceDeclaration declaration, String className,
		String packageName, String basePath, java.util.List<String> methods) {
}
