package lab.codeinsight.backend.scan.main;

import lab.codeinsight.backend.scan.model.report.ProjectModel;
import lab.codeinsight.backend.scan.model.report.ProjectReport;
import org.springframework.stereotype.Service;

@Service
public class ReportGenerator {

  public ProjectReport generate(ProjectModel projectModel) {
    return new ProjectReport(
        projectModel.controllers().size(),
        projectModel.endpoints().size(),
        projectModel.entities().size(),
        projectModel.statistics().totalJavaFiles());
  }
}
