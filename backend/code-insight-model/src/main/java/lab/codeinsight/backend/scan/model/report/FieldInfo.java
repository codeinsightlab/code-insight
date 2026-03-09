package lab.codeinsight.backend.scan.model.report;

import java.util.List;

public record FieldInfo(String name, String type, String columnName, List<String> annotations) {}
