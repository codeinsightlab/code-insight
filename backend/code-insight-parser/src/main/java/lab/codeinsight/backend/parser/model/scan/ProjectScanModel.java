package lab.codeinsight.backend.parser.model.scan;

import java.util.List;

/**
 * Unified minimal parser output for the first delivery slice.
 */
public record ProjectScanModel(ProjectStructure projectStructure, List<JavaSourceFile> javaSourceFiles,
		List<ControllerInfo> controllers, List<EndpointInfo> endpoints) {
}
