package lab.codeinsight.backend.parser.model.scan;

import java.util.List;

/** Basic controller metadata extracted from Java source annotations. */
public record ControllerInfo(String className, String packageName, String basePath, List<String> methods) {}
