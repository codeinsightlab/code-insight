package lab.codeinsight.backend.scan.model.report;

import java.util.List;

public record ProjectModel(
    List<ControllerInfo> controllers,
    List<EndpointInfo> endpoints,
    List<EntityInfo> entities,
    ProjectStats statistics) {}
