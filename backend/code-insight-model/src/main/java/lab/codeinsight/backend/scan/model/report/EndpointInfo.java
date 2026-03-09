package lab.codeinsight.backend.scan.model.report;

public record EndpointInfo(String path, String httpMethod, String controllerClass, String methodName) {}
