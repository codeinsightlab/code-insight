package lab.codeinsight.backend.parser.scanner.source;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lab.codeinsight.backend.parser.model.scan.JavaSourceFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests Java source loader behavior.
 */
class JavaSourceLoaderTest {

	@TempDir
	Path tempDir;

	/**
	 * Verifies source loader reads file content and resolves package declaration.
	 */
	@Test
	void shouldLoadJavaSourceAndExtractPackage() throws IOException {
		Path projectRoot = tempDir.resolve("demo");
		Path javaFile = projectRoot.resolve("src/main/java/com/example/web/UserController.java");
		Files.createDirectories(javaFile.getParent());
		Files.writeString(javaFile, "package com.example.web;\nclass UserController {}\n");

		JavaSourceLoader loader = new JavaSourceLoader();
		List<JavaSourceFile> sources = loader.load(projectRoot,
				List.of("src/main/java/com/example/web/UserController.java"));

		assertThat(sources).hasSize(1);
		assertThat(sources.get(0).filePath()).isEqualTo("src/main/java/com/example/web/UserController.java");
		assertThat(sources.get(0).packageName()).isEqualTo("com.example.web");
		assertThat(sources.get(0).sourceCode()).contains("class UserController");
	}
}
