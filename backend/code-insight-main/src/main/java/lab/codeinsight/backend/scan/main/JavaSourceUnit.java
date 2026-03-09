package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.CompilationUnit;
import java.nio.file.Path;
import lab.codeinsight.backend.scan.model.report.JavaSourceFile;

/**
 * Parsed Java source unit with source path, raw source and AST.
 */
record JavaSourceUnit(Path sourcePath, JavaSourceFile javaSourceFile, CompilationUnit compilationUnit) {
}
