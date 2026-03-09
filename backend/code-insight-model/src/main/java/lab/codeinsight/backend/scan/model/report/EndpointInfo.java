package lab.codeinsight.backend.scan.model.report;

/**
 * Endpoint scan result for one controller method mapping.
 */
public record EndpointInfo(String path, String httpMethod, String controllerClass, String methodName) {
}
