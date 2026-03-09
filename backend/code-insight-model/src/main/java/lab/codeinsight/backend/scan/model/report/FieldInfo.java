package lab.codeinsight.backend.scan.model.report;

import java.util.List;

/**
 * Field scan result with type, column mapping and raw annotations.
 */
public record FieldInfo(String name, String type, String columnName, List<String> annotations) {
}
