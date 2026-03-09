package lab.codeinsight.backend.scan.model.dto;

import jakarta.validation.constraints.NotBlank;

public record ScanProjectRequest(@NotBlank(message = "must not be blank") String projectPath) {}
