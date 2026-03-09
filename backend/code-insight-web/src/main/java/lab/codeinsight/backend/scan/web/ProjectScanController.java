package lab.codeinsight.backend.scan.web;

import jakarta.validation.Valid;
import lab.codeinsight.backend.scan.main.ProjectScanner;
import lab.codeinsight.backend.scan.main.ProjectStructureScanner;
import lab.codeinsight.backend.scan.model.dto.ScanProjectRequest;
import lab.codeinsight.backend.scan.model.report.ProjectReport;
import lab.codeinsight.backend.scan.model.report.ProjectStructure;
import lab.codeinsight.backend.web.common.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scan")
public class ProjectScanController {

  private final ProjectScanner projectScanner;
  private final ProjectStructureScanner projectStructureScanner;

  public ProjectScanController(
      ProjectScanner projectScanner, ProjectStructureScanner projectStructureScanner) {
    this.projectScanner = projectScanner;
    this.projectStructureScanner = projectStructureScanner;
  }

  @PostMapping("/project-structure")
  public ApiResponse<ProjectStructure> scanProjectStructure(
      @Valid @RequestBody ScanProjectRequest request) {
    return ApiResponse.ok(projectStructureScanner.scan(request.projectPath()));
  }

  @PostMapping({"", "/report"})
  public ApiResponse<ProjectReport> scan(@Valid @RequestBody ScanProjectRequest request) {
    return ApiResponse.ok(projectScanner.scan(request.projectPath()));
  }
}
