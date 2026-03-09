package lab.codeinsight.backend.parser.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class PathSupportTest {

  @Test
  void shouldConvertToUnixRelativePath() {
    Path root = Path.of("/tmp/project");
    Path file = Path.of("/tmp/project/src/main/java/com/example/Demo.java");

    String relative = PathSupport.toRelativeUnixPath(root, file);

    assertThat(relative).isEqualTo("src/main/java/com/example/Demo.java");
  }

  @Test
  void shouldReturnMinusOneWhenSegmentDoesNotExist() {
    Path path = Path.of("src/main/kotlin/com/example/App.kt");

    int index = PathSupport.indexOfPathElement(path, "java");

    assertThat(index).isEqualTo(-1);
  }
}
