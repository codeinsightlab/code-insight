package lab.codeinsight.backend.parser.model.scan;

import java.util.List;

/**
 * Project-level static structure discovered from filesystem scanning.
 */
public record ProjectStructure(String projectPath, List<String> javaFiles, List<String> sourceDirectories,
		List<String> basePackages) {
}
