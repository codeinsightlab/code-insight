package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

/**
 * Internal candidate model for entity discovery pipeline.
 */
record EntityCandidate(JavaSourceUnit sourceUnit, ClassOrInterfaceDeclaration declaration, String className,
		String tableName) {
}
