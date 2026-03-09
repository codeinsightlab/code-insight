package lab.codeinsight.backend.parser.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lab.codeinsight.backend.parser.model.scan.ProjectScanModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests parser facade behavior as external parser entry point.
 */
class ProjectScanFacadeTest {

	@TempDir
	Path tempDir;

	/**
	 * Verifies facade returns complete scan model for a minimal project.
	 */
	@Test
	void shouldReturnProjectScanModelForGivenProjectPath() throws IOException {
		Path projectRoot = tempDir.resolve("demo");
		write(projectRoot.resolve("src/main/java/com/example/web/AppController.java"), """
				package com.example.web;

				import org.springframework.web.bind.annotation.GetMapping;
				import org.springframework.web.bind.annotation.RequestMapping;
				import org.springframework.web.bind.annotation.RestController;

				@RestController
				@RequestMapping("/api/apps")
				class AppController {
				  @GetMapping
				  String getApp() { return "ok"; }
				}
				""");

		ProjectScanFacade facade = new ProjectScanFacade();
		ProjectScanModel model = facade.scanProject(projectRoot.toString());
		var structure = model.projectStructure();

		assertThat(structure.projectPath()).isEqualTo(projectRoot.toAbsolutePath().normalize().toString());
		assertThat(structure.javaFiles()).containsExactly("src/main/java/com/example/web/AppController.java");
		assertThat(structure.sourceDirectories()).containsExactly("src/main/java");
		assertThat(structure.basePackages()).containsExactly("com.example.web");
		assertThat(model.javaSourceFiles()).hasSize(1);
		assertThat(model.controllers()).hasSize(1);
		assertThat(model.endpoints()).hasSize(1);
		assertThat(model.endpoints().get(0).path()).isEqualTo("/api/apps");
	}

	/**
	 * Writes Java source content for test setup.
	 */
	private void write(Path file, String content) throws IOException {
		Files.createDirectories(file.getParent());
		Files.writeString(file, content);
	}
}
