package lab.codeinsight.backend.parser.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lab.codeinsight.backend.parser.model.scan.ProjectStructure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ProjectScanFacadeTest {

  @TempDir Path tempDir;

  @Test
  void shouldReturnProjectStructureForGivenProjectPath() throws IOException {
    Path projectRoot = tempDir.resolve("demo");
    write(projectRoot.resolve("src/main/java/com/example/App.java"));

    ProjectScanFacade facade = new ProjectScanFacade();
    ProjectStructure structure = facade.scanProject(projectRoot.toString());

    assertThat(structure.projectPath()).isEqualTo(projectRoot.toAbsolutePath().normalize().toString());
    assertThat(structure.javaFiles()).containsExactly("src/main/java/com/example/App.java");
    assertThat(structure.sourceDirectories()).containsExactly("src/main/java");
    assertThat(structure.basePackages()).containsExactly("com.example");
  }

  private void write(Path file) throws IOException {
    Files.createDirectories(file.getParent());
    Files.writeString(file, "package com.example;\nclass App {}\n");
  }
}
