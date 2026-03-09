package lab.codeinsight.backend.project.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectRequest(
    @NotBlank(message = "must not be blank") String name,
    @NotBlank(message = "must not be blank") String path) {}
