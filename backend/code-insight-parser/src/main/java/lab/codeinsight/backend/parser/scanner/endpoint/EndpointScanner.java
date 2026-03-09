package lab.codeinsight.backend.parser.scanner.endpoint;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lab.codeinsight.backend.parser.model.scan.ControllerInfo;
import lab.codeinsight.backend.parser.model.scan.EndpointInfo;
import lab.codeinsight.backend.parser.model.scan.JavaSourceFile;
import lab.codeinsight.backend.parser.parser.annotation.AnnotationSupport;

/**
 * Extracts endpoint metadata from controller method-level mapping annotations.
 */
public class EndpointScanner {

	/**
	 * Produces endpoint list by matching controllers to source declarations.
	 */
	public List<EndpointInfo> scan(List<ControllerInfo> controllers, List<JavaSourceFile> javaSourceFiles) {
		List<EndpointInfo> endpoints = new ArrayList<>();

		for (ControllerInfo controller : controllers) {
			Optional<ClassOrInterfaceDeclaration> declarationOpt = findControllerDeclaration(controller,
					javaSourceFiles);
			if (declarationOpt.isEmpty()) {
				continue;
			}

			for (MethodDeclaration method : declarationOpt.get().getMethods()) {
				Optional<String> methodPath = AnnotationSupport.resolveRequestPath(method);
				Optional<String> httpMethod = AnnotationSupport.resolveHttpMethod(method);
				if (methodPath.isEmpty() && httpMethod.isEmpty()) {
					continue;
				}

				String fullPath = joinPath(controller.basePath(), methodPath.orElse(""));
				endpoints.add(new EndpointInfo(fullPath, httpMethod.orElse("REQUEST"), controller.className(),
						method.getNameAsString()));
			}
		}

		return endpoints;
	}

	/**
	 * Finds class declaration that matches controller class name and package.
	 */
	private Optional<ClassOrInterfaceDeclaration> findControllerDeclaration(ControllerInfo controller,
			List<JavaSourceFile> javaSourceFiles) {
		for (JavaSourceFile sourceFile : javaSourceFiles) {
			Optional<CompilationUnit> compilationUnitOpt = parseCompilationUnit(sourceFile.sourceCode());
			if (compilationUnitOpt.isEmpty()) {
				continue;
			}

			CompilationUnit compilationUnit = compilationUnitOpt.get();
			for (ClassOrInterfaceDeclaration declaration : compilationUnit.findAll(ClassOrInterfaceDeclaration.class)) {
				if (declaration.getNameAsString().equals(controller.className())
						&& sourceFile.packageName().equals(controller.packageName())) {
					return Optional.of(declaration);
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Parses Java source into compilation unit and ignores malformed files.
	 */
	private Optional<CompilationUnit> parseCompilationUnit(String sourceCode) {
		try {
			return Optional.of(StaticJavaParser.parse(sourceCode));
		} catch (Exception ignored) {
			return Optional.empty();
		}
	}

	/**
	 * Joins class-level and method-level mapping paths into final endpoint path.
	 */
	private String joinPath(String basePath, String methodPath) {
		String left = normalize(basePath);
		String right = normalize(methodPath);
		if (left.isEmpty() && right.isEmpty()) {
			return "/";
		}
		if (left.isEmpty()) {
			return right;
		}
		if (right.isEmpty() || "/".equals(right)) {
			return left;
		}
		// Collapse accidental duplicated slashes after concatenation.
		return (left + right).replaceAll("//+", "/");
	}

	/**
	 * Normalizes mapping path to leading-slash format.
	 */
	private String normalize(String path) {
		if (path == null || path.isBlank()) {
			return "";
		}
		return path.startsWith("/") ? path : "/" + path;
	}
}
