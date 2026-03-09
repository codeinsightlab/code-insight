package lab.codeinsight.backend.parser.scanner.project;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lab.codeinsight.backend.parser.model.scan.ProjectStructure;
import lab.codeinsight.backend.parser.parser.ast.AstParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ProjectStructureScannerTest {

  @TempDir Path tempDir;

  @Test
  void shouldScanJavaFilesAndExtractSourceDirectoriesAndBasePackages() throws IOException {
    Path projectRoot = tempDir.resolve("demo");
    write(projectRoot.resolve("src/main/java/com/example/web/UserController.java"));
    write(projectRoot.resolve("src/main/java/com/example/model/UserEntity.java"));
    write(projectRoot.resolve("src/test/java/com/example/web/UserControllerTest.java"));
    write(projectRoot.resolve("target/generated-sources/Dummy.java"));

    ProjectStructureScanner scanner = new ProjectStructureScanner(new AstParser());
    ProjectStructure structure = scanner.scan(projectRoot);

    assertThat(structure.javaFiles())
        .containsExactly(
            "src/main/java/com/example/model/UserEntity.java",
            "src/main/java/com/example/web/UserController.java",
            "src/test/java/com/example/web/UserControllerTest.java");
    assertThat(structure.sourceDirectories()).containsExactly("src/main/java", "src/test/java");
    assertThat(structure.basePackages()).containsExactly("com.example.model", "com.example.web");
  }

  private void write(Path file) throws IOException {
    Files.createDirectories(file.getParent());
    Files.writeString(file, "class X {}\n");
  }
}
