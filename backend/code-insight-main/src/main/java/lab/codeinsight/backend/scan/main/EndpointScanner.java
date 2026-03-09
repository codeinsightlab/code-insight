package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lab.codeinsight.backend.scan.model.report.ControllerInfo;
import lab.codeinsight.backend.scan.model.report.EndpointInfo;
import org.springframework.stereotype.Service;

/**
 * Scans controller methods and resolves endpoint metadata.
 */
@Service
public class EndpointScanner {

	/**
	 * Builds endpoint list from controllers and source units.
	 */
	public List<EndpointInfo> scan(List<ControllerInfo> controllers, List<JavaSourceUnit> sourceUnits) {
		List<EndpointInfo> endpoints = new ArrayList<>();

		for (ControllerInfo controller : controllers) {
			Optional<ClassOrInterfaceDeclaration> declarationOpt = findControllerDeclaration(controller, sourceUnits);
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
	 * Finds matching class declaration for one controller info record.
	 */
	private Optional<ClassOrInterfaceDeclaration> findControllerDeclaration(ControllerInfo controller,
			List<JavaSourceUnit> sourceUnits) {
		for (JavaSourceUnit sourceUnit : sourceUnits) {
			for (ClassOrInterfaceDeclaration declaration : sourceUnit.compilationUnit()
					.findAll(ClassOrInterfaceDeclaration.class)) {
				String className = declaration.getNameAsString();
				String packageName = sourceUnit.javaSourceFile().packageName();
				if (className.equals(controller.className()) && packageName.equals(controller.packageName())) {
					return Optional.of(declaration);
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Joins class-level and method-level paths into one normalized endpoint path.
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
		return (left + right).replaceAll("//+", "/");
	}

	/**
	 * Normalizes path to leading-slash format.
	 */
	private String normalize(String path) {
		if (path == null || path.isBlank()) {
			return "";
		}
		return path.startsWith("/") ? path : "/" + path;
	}
}
