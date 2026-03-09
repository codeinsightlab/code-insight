package lab.codeinsight.backend.parser.scanner.controller;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lab.codeinsight.backend.parser.model.scan.ControllerInfo;
import lab.codeinsight.backend.parser.model.scan.JavaSourceFile;
import lab.codeinsight.backend.parser.parser.annotation.AnnotationSupport;

/**
 * Detects Spring MVC controllers and extracts minimal controller metadata.
 */
public class ControllerScanner {

	/**
	 * Scans Java source files and returns controller candidates with class-level
	 * mapping path.
	 */
	public List<ControllerInfo> scan(List<JavaSourceFile> javaSourceFiles) {
		List<ControllerInfo> results = new ArrayList<>();

		for (JavaSourceFile sourceFile : javaSourceFiles) {
			Optional<CompilationUnit> compilationUnitOpt = parseCompilationUnit(sourceFile.sourceCode());
			if (compilationUnitOpt.isEmpty()) {
				// Keep full-project scan resilient when encountering malformed source files.
				continue;
			}

			CompilationUnit compilationUnit = compilationUnitOpt.get();
			for (ClassOrInterfaceDeclaration declaration : compilationUnit.findAll(ClassOrInterfaceDeclaration.class)) {
				if (!isController(declaration)) {
					continue;
				}

				String basePath = normalizePath(AnnotationSupport.resolveRequestPath(declaration).orElse(""));
				List<String> methods = declaration.getMethods().stream().map(method -> method.getNameAsString())
						.toList();

				results.add(
						new ControllerInfo(declaration.getNameAsString(), sourceFile.packageName(), basePath, methods));
			}
		}

		return results;
	}

	/**
	 * Parses Java source into compilation unit and swallows malformed files.
	 */
	private Optional<CompilationUnit> parseCompilationUnit(String sourceCode) {
		try {
			return Optional.of(StaticJavaParser.parse(sourceCode));
		} catch (Exception ignored) {
			return Optional.empty();
		}
	}

	/**
	 * Checks if declaration is annotated as Spring MVC controller.
	 */
	private boolean isController(ClassOrInterfaceDeclaration declaration) {
		return AnnotationSupport.hasAnnotation(declaration, "RestController")
				|| AnnotationSupport.hasAnnotation(declaration, "Controller");
	}

	/**
	 * Normalizes mapping path into leading-slash format.
	 */
	private String normalizePath(String path) {
		if (path == null || path.isBlank()) {
			return "";
		}
		return path.startsWith("/") ? path : "/" + path;
	}
}
