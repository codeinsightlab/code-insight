package lab.codeinsight.backend.scan.main;

import java.util.List;
import lab.codeinsight.backend.scan.model.report.ControllerInfo;
import lab.codeinsight.backend.scan.model.report.EndpointInfo;
import lab.codeinsight.backend.scan.model.report.EntityInfo;
import lab.codeinsight.backend.scan.model.report.JavaSourceFileInfo;
import lab.codeinsight.backend.scan.model.report.ProjectModel;
import lab.codeinsight.backend.scan.model.report.ProjectStats;
import org.springframework.stereotype.Service;

@Service
public class ProjectModelBuilder {

  public ProjectModel build(
      List<ControllerInfo> controllers,
      List<EndpointInfo> endpoints,
      List<EntityInfo> entities,
      List<JavaSourceFileInfo> javaFiles) {

    int totalEntityFields = entities.stream().mapToInt(entity -> entity.fields().size()).sum();

    ProjectStats statistics =
        new ProjectStats(
            javaFiles.size(),
            controllers.size(),
            endpoints.size(),
            entities.size(),
            totalEntityFields);

    return new ProjectModel(controllers, endpoints, entities, statistics);
  }
}
