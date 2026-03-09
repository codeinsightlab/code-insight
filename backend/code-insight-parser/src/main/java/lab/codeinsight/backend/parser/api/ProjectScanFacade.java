package lab.codeinsight.backend.parser.api;

import java.nio.file.Path;
import lab.codeinsight.backend.parser.core.ScanContext;
import lab.codeinsight.backend.parser.model.scan.ProjectScanModel;
import lab.codeinsight.backend.parser.parser.ast.AstParser;
import lab.codeinsight.backend.parser.scanner.controller.ControllerScanner;
import lab.codeinsight.backend.parser.scanner.endpoint.EndpointScanner;
import lab.codeinsight.backend.parser.scanner.project.ProjectScanner;
import lab.codeinsight.backend.parser.scanner.project.ProjectStructureScanner;
import lab.codeinsight.backend.parser.scanner.source.JavaSourceLoader;

/**
 * External entry point for parser module callers.
 *
 * <p>Current minimal flow builds a unified scan model that includes project structure, Java source
 * files, controllers, and endpoints.
 */
public class ProjectScanFacade {

  private final ProjectScanner projectScanner;

  public ProjectScanFacade() {
    this(
        new ProjectScanner(
            new ProjectStructureScanner(new AstParser()),
            new JavaSourceLoader(),
            new ControllerScanner(),
            new EndpointScanner()));
  }

  public ProjectScanFacade(ProjectScanner projectScanner) {
    this.projectScanner = projectScanner;
  }

  /** Scans a project by path and returns the unified parser scan model. */
  public ProjectScanModel scanProject(String projectPath) {
    ScanContext context = new ScanContext(Path.of(projectPath).toAbsolutePath().normalize());
    return projectScanner.scanProject(context).data();
  }
}
