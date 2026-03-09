package lab.codeinsight.backend.parser.scanner.controller;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import java.util.ArrayList;
import java.util.List;
import lab.codeinsight.backend.parser.model.scan.ControllerInfo;
import lab.codeinsight.backend.parser.model.scan.JavaSourceFile;
import lab.codeinsight.backend.parser.parser.annotation.AnnotationSupport;

/** Detects Spring MVC controllers and extracts minimal controller metadata. */
public class ControllerScanner {

  /** Scans Java source files and returns controller candidates with class-level mapping path. */
  public List<ControllerInfo> scan(List<JavaSourceFile> javaSourceFiles) {
    List<ControllerInfo> results = new ArrayList<>();

    for (JavaSourceFile sourceFile : javaSourceFiles) {
      var compilationUnit = StaticJavaParser.parse(sourceFile.sourceCode());
      for (ClassOrInterfaceDeclaration declaration :
          compilationUnit.findAll(ClassOrInterfaceDeclaration.class)) {
        if (!isController(declaration)) {
          continue;
        }

        String basePath = normalizePath(AnnotationSupport.resolveRequestPath(declaration).orElse(""));
        List<String> methods = declaration.getMethods().stream().map(method -> method.getNameAsString()).toList();

        results.add(
            new ControllerInfo(
                declaration.getNameAsString(), sourceFile.packageName(), basePath, methods));
      }
    }

    return results;
  }

  private boolean isController(ClassOrInterfaceDeclaration declaration) {
    return AnnotationSupport.hasAnnotation(declaration, "RestController")
        || AnnotationSupport.hasAnnotation(declaration, "Controller");
  }

  private String normalizePath(String path) {
    if (path == null || path.isBlank()) {
      return "";
    }
    return path.startsWith("/") ? path : "/" + path;
  }
}
