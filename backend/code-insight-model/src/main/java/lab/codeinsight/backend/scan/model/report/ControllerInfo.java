package lab.codeinsight.backend.scan.model.report;

import java.util.List;

public record ControllerInfo(String className, String packageName, String basePath, List<String> methods) {}
