package lab.codeinsight.backend.parser.scanner.project;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lab.codeinsight.backend.parser.core.ScanContext;
import lab.codeinsight.backend.parser.model.scan.ProjectScanModel;
import lab.codeinsight.backend.parser.parser.ast.AstParser;
import lab.codeinsight.backend.parser.scanner.controller.ControllerScanner;
import lab.codeinsight.backend.parser.scanner.endpoint.EndpointScanner;
import lab.codeinsight.backend.parser.scanner.source.JavaSourceLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ProjectScannerTest {

  @TempDir Path tempDir;

  @Test
  void shouldBuildMinimalProjectScanModel() throws IOException {
    Path projectRoot = tempDir.resolve("demo");
    write(
        projectRoot.resolve("src/main/java/com/example/web/UserController.java"),
        """
        package com.example.web;

        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

        @RestController
        @RequestMapping("/api/users")
        class UserController {
          @GetMapping("/{id}")
          String getUser() { return "ok"; }
        }
        """);
    write(
        projectRoot.resolve("src/main/java/com/example/service/UserService.java"),
        "package com.example.service; class UserService {}\n");

    ProjectScanner scanner =
        new ProjectScanner(
            new ProjectStructureScanner(new AstParser()),
            new JavaSourceLoader(),
            new ControllerScanner(),
            new EndpointScanner());

    ProjectScanModel model = scanner.scanProject(new ScanContext(projectRoot)).data();

    assertThat(model.projectStructure().javaFiles()).hasSize(2);
    assertThat(model.javaSourceFiles()).hasSize(2);
    assertThat(model.controllers()).hasSize(1);
    assertThat(model.endpoints()).hasSize(1);
    assertThat(model.endpoints().get(0).path()).isEqualTo("/api/users/{id}");
    assertThat(model.endpoints().get(0).httpMethod()).isEqualTo("GET");
  }

  private void write(Path file, String content) throws IOException {
    Files.createDirectories(file.getParent());
    Files.writeString(file, content);
  }
}
