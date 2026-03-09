package lab.codeinsight.backend.scan.model.report;

public record ProjectStats(
    int totalJavaFiles,
    int totalControllers,
    int totalEndpoints,
    int totalEntities,
    int totalEntityFields) {}
