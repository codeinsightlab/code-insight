package lab.codeinsight.backend.parser.core;

import lab.codeinsight.backend.parser.model.scan.ProjectStructure;

public interface ParserEngine {

  ScanResult<ProjectStructure> scanProjectStructure(ScanContext context);
}
