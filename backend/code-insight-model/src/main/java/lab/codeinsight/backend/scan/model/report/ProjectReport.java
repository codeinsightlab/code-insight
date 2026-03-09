package lab.codeinsight.backend.scan.model.report;

public record ProjectReport(
    int controllerCount, int endpointCount, int entityCount, int javaFileCount) {}
