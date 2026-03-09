package lab.codeinsight.backend.parser.scanner.project;

import lab.codeinsight.backend.parser.core.ParserEngine;
import lab.codeinsight.backend.parser.core.ScanContext;
import lab.codeinsight.backend.parser.core.ScanResult;
import lab.codeinsight.backend.parser.model.scan.ControllerInfo;
import lab.codeinsight.backend.parser.model.scan.EndpointInfo;
import lab.codeinsight.backend.parser.model.scan.JavaSourceFile;
import lab.codeinsight.backend.parser.model.scan.ProjectScanModel;
import lab.codeinsight.backend.parser.model.scan.ProjectStructure;
import lab.codeinsight.backend.parser.scanner.controller.ControllerScanner;
import lab.codeinsight.backend.parser.scanner.endpoint.EndpointScanner;
import lab.codeinsight.backend.parser.scanner.source.JavaSourceLoader;

/** Orchestrates the minimal parser pipeline for Phase 2 parser kernel. */
public class ProjectScanner implements ParserEngine {

  private final ProjectStructureScanner projectStructureScanner;
  private final JavaSourceLoader javaSourceLoader;
  private final ControllerScanner controllerScanner;
  private final EndpointScanner endpointScanner;

  public ProjectScanner(
      ProjectStructureScanner projectStructureScanner,
      JavaSourceLoader javaSourceLoader,
      ControllerScanner controllerScanner,
      EndpointScanner endpointScanner) {
    this.projectStructureScanner = projectStructureScanner;
    this.javaSourceLoader = javaSourceLoader;
    this.controllerScanner = controllerScanner;
    this.endpointScanner = endpointScanner;
  }

  @Override
  public ScanResult<ProjectScanModel> scanProject(ScanContext context) {
    // 1) Directory scan
    ProjectStructure projectStructure = projectStructureScanner.scan(context.projectRoot());
    // 2) Source load
    java.util.List<JavaSourceFile> javaSourceFiles =
        javaSourceLoader.load(context.projectRoot(), projectStructure.javaFiles());
    // 3) Controller and endpoint extraction
    java.util.List<ControllerInfo> controllers = controllerScanner.scan(javaSourceFiles);
    java.util.List<EndpointInfo> endpoints = endpointScanner.scan(controllers, javaSourceFiles);

    return ScanResult.success(
        new ProjectScanModel(projectStructure, javaSourceFiles, controllers, endpoints));
  }
}
