package lab.codeinsight.backend.scan.main;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lab.codeinsight.backend.scan.dal.JavaSourceLoader;
import lab.codeinsight.backend.scan.model.report.ProjectReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ProjectScannerAcceptanceTest {

  @TempDir Path tempDir;

  @Test
  void shouldScanSpringBootProjectAndGenerateStructuredReport() throws IOException {
    Path projectRoot = tempDir.resolve("demo");
    write(
        projectRoot.resolve("src/main/java/com/example/demo/web/UserController.java"),
        """
        package com.example.demo.web;

        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

        @RestController
        @RequestMapping("/api/users")
        public class UserController {

          @GetMapping("/{id}")
          public String getUser() {
            return "ok";
          }

          @PostMapping
          public String createUser() {
            return "ok";
          }
        }
        """);

    write(
        projectRoot.resolve("src/main/java/com/example/demo/model/UserEntity.java"),
        """
        package com.example.demo.model;

        import jakarta.persistence.Column;
        import jakarta.persistence.Entity;
        import jakarta.persistence.Id;
        import jakarta.persistence.Table;

        @Entity
        @Table(name = "users")
        public class UserEntity {

          @Id
          @Column(name = "id")
          private Long id;

          @Column(name = "username")
          private String username;
        }
        """);

    write(
        projectRoot.resolve("src/main/java/com/example/demo/service/UserService.java"),
        """
        package com.example.demo.service;

        public class UserService {}
        """);

    ProjectScanner scanner = createScanner();
    ProjectReport report = scanner.scan(projectRoot.toString());

    assertThat(report.controllerCount()).isEqualTo(1);
    assertThat(report.endpointCount()).isEqualTo(2);
    assertThat(report.entityCount()).isEqualTo(1);
    assertThat(report.javaFileCount()).isEqualTo(3);
  }

  private ProjectScanner createScanner() {
    JavaSourceLoader javaSourceLoader = new JavaSourceLoader();
    ProjectStructureScanner projectStructureScanner = new ProjectStructureScanner(javaSourceLoader);
    ControllerScanner controllerScanner = new ControllerScanner();
    EndpointScanner endpointScanner = new EndpointScanner();
    FieldScanner fieldScanner = new FieldScanner();
    EntityScanner entityScanner = new EntityScanner(fieldScanner);
    ProjectModelBuilder projectModelBuilder = new ProjectModelBuilder();
    ReportGenerator reportGenerator = new ReportGenerator();

    return new ProjectScanner(
        projectStructureScanner,
        javaSourceLoader,
        controllerScanner,
        endpointScanner,
        entityScanner,
        projectModelBuilder,
        reportGenerator);
  }

  private void write(Path file, String content) throws IOException {
    Files.createDirectories(file.getParent());
    Files.writeString(file, content);
  }
}
