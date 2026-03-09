package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import java.util.ArrayList;
import java.util.List;
import lab.codeinsight.backend.scan.model.report.ControllerInfo;
import org.springframework.stereotype.Service;

/**
 * Scans parsed Java source units and extracts Spring MVC controllers.
 */
@Service
public class ControllerScanner {

	/**
	 * Returns controller info list from compilation units.
	 */
	public List<ControllerInfo> scan(List<JavaSourceUnit> sourceUnits) {
		List<ControllerInfo> results = new ArrayList<>();

		for (JavaSourceUnit sourceUnit : sourceUnits) {
			for (ClassOrInterfaceDeclaration declaration : sourceUnit.compilationUnit()
					.findAll(ClassOrInterfaceDeclaration.class)) {
				if (!isController(declaration)) {
					continue;
				}

				String basePath = normalizePath(AnnotationSupport.resolveRequestPath(declaration).orElse(""));
				String packageName = sourceUnit.javaSourceFile().packageName();
				List<String> methods = declaration.getMethods().stream().map(m -> m.getNameAsString()).toList();

				results.add(new ControllerInfo(declaration.getNameAsString(), packageName, basePath, methods));
			}
		}

		return results;
	}

	/**
	 * Determines whether a type is a Spring controller.
	 */
	private boolean isController(ClassOrInterfaceDeclaration declaration) {
		return AnnotationSupport.hasAnnotation(declaration, "RestController")
				|| AnnotationSupport.hasAnnotation(declaration, "Controller");
	}

	/**
	 * Normalizes request path to leading-slash format.
	 */
	private String normalizePath(String path) {
		if (path == null || path.isBlank()) {
			return "";
		}
		return path.startsWith("/") ? path : "/" + path;
	}
}
