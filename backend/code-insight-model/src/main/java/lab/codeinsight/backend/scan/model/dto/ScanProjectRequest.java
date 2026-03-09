package lab.codeinsight.backend.scan.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for triggering project scan by path.
 */
public record ScanProjectRequest(@NotBlank(message = "must not be blank") String projectPath) {
}
