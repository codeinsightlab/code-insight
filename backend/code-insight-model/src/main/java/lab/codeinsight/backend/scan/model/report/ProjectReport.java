package lab.codeinsight.backend.scan.model.report;

/**
 * Final report payload exposed by scan API.
 */
public record ProjectReport(int controllerCount, int endpointCount, int entityCount, int javaFileCount) {
}
