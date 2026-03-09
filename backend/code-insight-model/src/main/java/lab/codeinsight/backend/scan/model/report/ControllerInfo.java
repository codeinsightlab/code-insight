package lab.codeinsight.backend.scan.model.report;

import java.util.List;

/**
 * Controller scan result with class metadata and declared methods.
 */
public record ControllerInfo(String className, String packageName, String basePath, List<String> methods) {
}
