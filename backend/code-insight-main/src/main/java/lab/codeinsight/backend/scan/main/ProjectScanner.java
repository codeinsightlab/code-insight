package lab.codeinsight.backend.scan.main;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lab.codeinsight.backend.scan.dal.JavaSourceLoader;
import lab.codeinsight.backend.scan.model.report.ControllerInfo;
import lab.codeinsight.backend.scan.model.report.EndpointInfo;
import lab.codeinsight.backend.scan.model.report.EntityInfo;
import lab.codeinsight.backend.scan.model.report.JavaSourceFile;
import lab.codeinsight.backend.scan.model.report.JavaSourceFileInfo;
import lab.codeinsight.backend.scan.model.report.ProjectModel;
import lab.codeinsight.backend.scan.model.report.ProjectReport;
import lab.codeinsight.backend.scan.model.report.ProjectStructure;
import org.springframework.stereotype.Service;

@Service
public class ProjectScanner {

  private final ProjectStructureScanner projectStructureScanner;
  private final JavaSourceLoader javaSourceLoader;
  private final ControllerScanner controllerScanner;
  private final EndpointScanner endpointScanner;
  private final EntityScanner entityScanner;
  private final ProjectModelBuilder projectModelBuilder;
  private final ReportGenerator reportGenerator;

  public ProjectScanner(
      ProjectStructureScanner projectStructureScanner,
      JavaSourceLoader javaSourceLoader,
      ControllerScanner controllerScanner,
      EndpointScanner endpointScanner,
      EntityScanner entityScanner,
      ProjectModelBuilder projectModelBuilder,
      ReportGenerator reportGenerator) {
    this.projectStructureScanner = projectStructureScanner;
    this.javaSourceLoader = javaSourceLoader;
    this.controllerScanner = controllerScanner;
    this.endpointScanner = endpointScanner;
    this.entityScanner = entityScanner;
    this.projectModelBuilder = projectModelBuilder;
    this.reportGenerator = reportGenerator;
  }

  public ProjectReport scan(String projectPath) {
    ProjectStructure projectStructure = projectStructureScanner.scan(projectPath);
    Path projectRoot = Path.of(projectStructure.projectPath()).toAbsolutePath().normalize();

    List<JavaSourceFile> javaSources =
        javaSourceLoader.loadJavaSources(projectRoot, projectStructure.javaFiles());
    List<JavaSourceUnit> sourceUnits = parseAll(projectRoot, javaSources);

    List<JavaSourceFileInfo> javaFiles = toJavaFileInfos(projectRoot, sourceUnits);
    List<ControllerInfo> controllers = controllerScanner.scan(sourceUnits);
    List<EndpointInfo> endpoints = endpointScanner.scan(controllers, sourceUnits);
    List<EntityInfo> entities = entityScanner.scan(sourceUnits);

    ProjectModel projectModel = projectModelBuilder.build(controllers, endpoints, entities, javaFiles);

    return reportGenerator.generate(projectModel);
  }

  private List<JavaSourceUnit> parseAll(Path projectRoot, List<JavaSourceFile> javaSources) {
    List<JavaSourceUnit> sourceUnits = new ArrayList<>();
    for (JavaSourceFile javaSource : javaSources) {
      Path javaPath = projectRoot.resolve(javaSource.filePath()).normalize();
      try {
        CompilationUnit compilationUnit = StaticJavaParser.parse(javaSource.sourceCode());
        sourceUnits.add(new JavaSourceUnit(javaPath, javaSource, compilationUnit));
      } catch (Exception ignored) {
        // Keep scan resilient: skip files that cannot be parsed.
      }
    }
    return sourceUnits;
  }

  private List<JavaSourceFileInfo> toJavaFileInfos(Path projectRoot, List<JavaSourceUnit> sourceUnits) {
    return sourceUnits.stream()
        .map(
            unit ->
                new JavaSourceFileInfo(
                    projectRoot.relativize(unit.sourcePath()).toString().replace('\\', '/'),
                    unit.javaSourceFile().packageName(),
                    unit
                        .compilationUnit()
                        .getPrimaryType()
                        .map(TypeDeclaration::getNameAsString)
                        .orElse("")))
        .toList();
  }
}
