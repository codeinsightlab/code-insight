package lab.codeinsight.backend.scan.model.report;

import java.util.List;

public record EntityInfo(String className, String tableName, List<FieldInfo> fields) {}
