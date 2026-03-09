package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.CompilationUnit;
import java.nio.file.Path;
import lab.codeinsight.backend.scan.model.report.JavaSourceFile;

record JavaSourceUnit(Path sourcePath, JavaSourceFile javaSourceFile, CompilationUnit compilationUnit) {}
