package lab.codeinsight.backend.parser.scanner.endpoint;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lab.codeinsight.backend.parser.model.scan.ControllerInfo;
import lab.codeinsight.backend.parser.model.scan.EndpointInfo;
import lab.codeinsight.backend.parser.model.scan.JavaSourceFile;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

class EndpointScannerTest {

  @Test
  void shouldExtractEndpointsFromControllerMethods() {
    ControllerInfo controller =
        new ControllerInfo("UserController", "com.example.web", "/api/users", List.of("getUser", "createUser"));

    JavaSourceFile source =
        new JavaSourceFile(
            "src/main/java/com/example/web/UserController.java",
            "com.example.web",
            """
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
    assertThat(endpoints)
        .extracting(EndpointInfo::path, EndpointInfo::httpMethod, EndpointInfo::methodName)
        .containsExactlyInAnyOrder(
            Tuple.tuple("/api/users/{id}", "GET", "getUser"),
            Tuple.tuple("/api/users", "POST", "createUser"));
  }
}
