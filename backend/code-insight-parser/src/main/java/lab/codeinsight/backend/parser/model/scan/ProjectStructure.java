package lab.codeinsight.backend.parser.model.scan;

import java.util.List;

public record ProjectStructure(
    String projectPath,
    List<String> javaFiles,
    List<String> sourceDirectories,
    List<String> basePackages) {}
