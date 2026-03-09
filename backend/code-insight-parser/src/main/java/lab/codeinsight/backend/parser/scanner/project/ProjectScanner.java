package lab.codeinsight.backend.parser.scanner.project;

import lab.codeinsight.backend.parser.core.ParserEngine;
import lab.codeinsight.backend.parser.core.ScanContext;
import lab.codeinsight.backend.parser.core.ScanResult;
import lab.codeinsight.backend.parser.model.scan.ProjectStructure;

public class ProjectScanner implements ParserEngine {

  private final ProjectStructureScanner projectStructureScanner;

  public ProjectScanner(ProjectStructureScanner projectStructureScanner) {
    this.projectStructureScanner = projectStructureScanner;
  }

  @Override
  public ScanResult<ProjectStructure> scanProjectStructure(ScanContext context) {
    ProjectStructure structure = projectStructureScanner.scan(context.projectRoot());
    return ScanResult.success(structure);
  }
}
