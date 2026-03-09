package lab.codeinsight.backend.scan.model.report;

import java.util.List;

/**
 * Unified project model aggregating scanner outputs and statistics.
 */
public record ProjectModel(List<ControllerInfo> controllers, List<EndpointInfo> endpoints, List<EntityInfo> entities,
		ProjectStats statistics) {
}
