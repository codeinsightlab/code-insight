package lab.codeinsight.backend.scan.model.report;

import java.util.List;

/**
 * Entity scan result with class name, table name and field list.
 */
public record EntityInfo(String className, String tableName, List<FieldInfo> fields) {
}
