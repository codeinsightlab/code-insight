package lab.codeinsight.backend.parser.api;

import java.nio.file.Path;
import lab.codeinsight.backend.parser.core.ScanContext;
import lab.codeinsight.backend.parser.model.scan.ProjectStructure;
import lab.codeinsight.backend.parser.parser.ast.AstParser;
import lab.codeinsight.backend.parser.scanner.project.ProjectScanner;
import lab.codeinsight.backend.parser.scanner.project.ProjectStructureScanner;

public class ProjectScanFacade {

  private final ProjectScanner projectScanner;

  public ProjectScanFacade() {
    this(new ProjectScanner(new ProjectStructureScanner(new AstParser())));
  }

  public ProjectScanFacade(ProjectScanner projectScanner) {
    this.projectScanner = projectScanner;
  }

  public ProjectStructure scanProject(String projectPath) {
    ScanContext context = new ScanContext(Path.of(projectPath).toAbsolutePath().normalize());
    return projectScanner.scanProjectStructure(context).data();
  }
}
