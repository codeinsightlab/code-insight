package lab.codeinsight.backend.parser.model.scan;

/**
 * Basic endpoint metadata resolved from controller method mappings.
 */
public record EndpointInfo(String path, String httpMethod, String controllerClass, String methodName) {
}
