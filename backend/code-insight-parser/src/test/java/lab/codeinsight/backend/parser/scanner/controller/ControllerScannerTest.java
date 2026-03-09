package lab.codeinsight.backend.parser.scanner.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lab.codeinsight.backend.parser.model.scan.ControllerInfo;
import lab.codeinsight.backend.parser.model.scan.JavaSourceFile;
import org.junit.jupiter.api.Test;

class ControllerScannerTest {

  @Test
  void shouldDetectControllerAndRestController() {
    JavaSourceFile restController =
        new JavaSourceFile(
            "src/main/java/com/example/web/UserController.java",
            "com.example.web",
            """
            package com.example.web;

            import org.springframework.web.bind.annotation.RequestMapping;
            import org.springframework.web.bind.annotation.RestController;

            @RestController
            @RequestMapping("/api/users")
            class UserController {
              String getUser() { return "ok"; }
            }
            """);

    JavaSourceFile plainClass =
        new JavaSourceFile(
            "src/main/java/com/example/service/UserService.java",
            "com.example.service",
            "package com.example.service; class UserService {}\n");

    ControllerScanner scanner = new ControllerScanner();
    List<ControllerInfo> controllers = scanner.scan(List.of(restController, plainClass));

    assertThat(controllers).hasSize(1);
    assertThat(controllers.get(0).className()).isEqualTo("UserController");
    assertThat(controllers.get(0).packageName()).isEqualTo("com.example.web");
    assertThat(controllers.get(0).basePath()).isEqualTo("/api/users");
    assertThat(controllers.get(0).methods()).containsExactly("getUser");
  }
}
