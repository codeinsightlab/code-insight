package lab.codeinsight.backend.parser.scanner.endpoint;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lab.codeinsight.backend.parser.model.scan.ControllerInfo;
import lab.codeinsight.backend.parser.model.scan.EndpointInfo;
import lab.codeinsight.backend.parser.model.scan.JavaSourceFile;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

/**
 * Tests endpoint scanner extraction and malformed-source tolerance.
 */
class EndpointScannerTest {

	/**
	 * Verifies scanner resolves GET/POST endpoints from one controller.
	 */
	@Test
	void shouldExtractEndpointsFromControllerMethods() {
		ControllerInfo controller = new ControllerInfo("UserController", "com.example.web", "/api/users",
				List.of("getUser", "createUser"));

		JavaSourceFile source = new JavaSourceFile("src/main/java/com/example/web/UserController.java",
				"com.example.web", """
						package com.example.web;

						import org.springframework.web.bind.annotation.GetMapping;
						import org.springframework.web.bind.annotation.PostMapping;
						import org.springframework.web.bind.annotation.RequestMapping;
						import org.springframework.web.bind.annotation.RestController;

						@RestController
						@RequestMapping("/api/users")
						class UserController {
						  @GetMapping("/{id}")
						  String getUser() { return "ok"; }

						  @PostMapping
						  String createUser() { return "ok"; }
						}
						""");

		EndpointScanner scanner = new EndpointScanner();
		List<EndpointInfo> endpoints = scanner.scan(List.of(controller), List.of(source));

		assertThat(endpoints).hasSize(2);
		assertThat(endpoints).extracting(EndpointInfo::path, EndpointInfo::httpMethod, EndpointInfo::methodName)
				.containsExactlyInAnyOrder(Tuple.tuple("/api/users/{id}", "GET", "getUser"),
						Tuple.tuple("/api/users", "POST", "createUser"));
	}

	/**
	 * Verifies malformed files do not block endpoint extraction from valid files.
	 */
	@Test
	void shouldSkipMalformedSourceWhenResolvingControllerDeclaration() {
		ControllerInfo controller = new ControllerInfo("OrderController", "com.example.web", "/api/orders",
				List.of("list"));
		JavaSourceFile malformed = new JavaSourceFile("src/main/java/com/example/web/Broken.java", "com.example.web",
				"package com.example.web; class Broken {");
		JavaSourceFile valid = new JavaSourceFile("src/main/java/com/example/web/OrderController.java",
				"com.example.web", """
						package com.example.web;

						import org.springframework.web.bind.annotation.GetMapping;
						import org.springframework.web.bind.annotation.RequestMapping;
						import org.springframework.web.bind.annotation.RestController;

						@RestController
						@RequestMapping("/api/orders")
						class OrderController {
						  @GetMapping
						  String list() { return "ok"; }
						}
						""");

		EndpointScanner scanner = new EndpointScanner();
		List<EndpointInfo> endpoints = scanner.scan(List.of(controller), List.of(malformed, valid));

		assertThat(endpoints).hasSize(1);
		assertThat(endpoints.get(0).path()).isEqualTo("/api/orders");
		assertThat(endpoints.get(0).httpMethod()).isEqualTo("GET");
	}
}
