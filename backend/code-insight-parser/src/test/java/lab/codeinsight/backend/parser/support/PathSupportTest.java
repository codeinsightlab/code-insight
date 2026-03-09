package lab.codeinsight.backend.parser.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * Tests path helper utilities used by parser scanners.
 */
class PathSupportTest {

	/**
	 * Verifies helper converts root-relative path to Unix-style separators.
	 */
	@Test
	void shouldConvertToUnixRelativePath() {
		Path root = Path.of("/tmp/project");
		Path file = Path.of("/tmp/project/src/main/java/com/example/Demo.java");

		String relative = PathSupport.toRelativeUnixPath(root, file);

		assertThat(relative).isEqualTo("src/main/java/com/example/Demo.java");
	}

	/**
	 * Verifies helper returns -1 when path segment is absent.
	 */
	@Test
	void shouldReturnMinusOneWhenSegmentDoesNotExist() {
		Path path = Path.of("src/main/kotlin/com/example/App.kt");

		int index = PathSupport.indexOfPathElement(path, "java");

		assertThat(index).isEqualTo(-1);
	}
}
