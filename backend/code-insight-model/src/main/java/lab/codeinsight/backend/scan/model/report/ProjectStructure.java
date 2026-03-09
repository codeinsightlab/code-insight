package lab.codeinsight.backend.scan.model.report;

import java.util.List;

/**
 * Filesystem scan structure including Java files, source dirs and base
 * packages.
 */
public record ProjectStructure(String projectPath, List<String> javaFiles, List<String> sourceDirectories,
		List<String> basePackages) {
}
