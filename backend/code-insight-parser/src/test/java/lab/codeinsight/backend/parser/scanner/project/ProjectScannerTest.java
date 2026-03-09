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
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests full parser project scanner orchestration.
 */
class ProjectScannerTest {

	@TempDir
	Path tempDir;

	/**
	 * Verifies scanner builds complete model for a minimal project.
	 */
	@Test
	void shouldBuildMinimalProjectScanModel() throws IOException {
		Path projectRoot = tempDir.resolve("demo");
		write(projectRoot.resolve("src/main/java/com/example/web/UserController.java"), """
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
		write(projectRoot.resolve("src/main/java/com/example/service/UserService.java"),
				"package com.example.service; class UserService {}\n");

		ProjectScanner scanner = new ProjectScanner(new ProjectStructureScanner(new AstParser()),
				new JavaSourceLoader(), new ControllerScanner(), new EndpointScanner());

		ProjectScanModel model = scanner.scanProject(new ScanContext(projectRoot)).data();

		assertThat(model.projectStructure().javaFiles()).hasSize(2);
		assertThat(model.javaSourceFiles()).hasSize(2);
		assertThat(model.controllers()).hasSize(1);
		assertThat(model.endpoints()).hasSize(1);
		assertThat(model.endpoints().get(0).path()).isEqualTo("/api/users/{id}");
		assertThat(model.endpoints().get(0).httpMethod()).isEqualTo("GET");
	}

	/**
	 * Verifies scanner returns empty outputs for an empty project directory.
	 */
	@Test
	void shouldHandleEmptyProjectWithoutFailure() throws IOException {
		Path projectRoot = tempDir.resolve("empty-demo");
		Files.createDirectories(projectRoot);

		ProjectScanner scanner = new ProjectScanner(new ProjectStructureScanner(new AstParser()),
				new JavaSourceLoader(), new ControllerScanner(), new EndpointScanner());

		ProjectScanModel model = scanner.scanProject(new ScanContext(projectRoot)).data();

		assertThat(model.projectStructure().javaFiles()).isEmpty();
		assertThat(model.javaSourceFiles()).isEmpty();
		assertThat(model.controllers()).isEmpty();
		assertThat(model.endpoints()).isEmpty();
	}

	/**
	 * Verifies scanner regression counts on the fixed demo project dataset.
	 */
	@Test
	void shouldMatchDemoProjectRegressionCounts() {
		Path modulePath = Path.of("..", "..", "examples", "demo-project").toAbsolutePath().normalize();
		Assumptions.assumeTrue(Files.exists(modulePath), "Skip when demo project is unavailable");

		ProjectScanner scanner = new ProjectScanner(new ProjectStructureScanner(new AstParser()),
				new JavaSourceLoader(), new ControllerScanner(), new EndpointScanner());

		ProjectScanModel model = scanner.scanProject(new ScanContext(modulePath)).data();

		assertThat(model.projectStructure().javaFiles()).hasSize(8);
		assertThat(model.javaSourceFiles()).hasSize(8);
		assertThat(model.controllers()).hasSize(3);
		assertThat(model.endpoints()).hasSize(6);
	}

	/**
	 * Writes test file content and creates parent directories if needed.
	 */
	private void write(Path file, String content) throws IOException {
		Files.createDirectories(file.getParent());
		Files.writeString(file, content);
	}
}
