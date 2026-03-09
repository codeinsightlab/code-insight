package lab.codeinsight.backend.scan.model.report;

import java.util.List;

public record ProjectStructure(
    String projectPath,
    List<String> javaFiles,
    List<String> sourceDirectories,
    List<String> basePackages) {}
