package lab.codeinsight.backend.project.model.vo;

import java.time.LocalDateTime;

/**
 * View model returned by project APIs.
 */
public record ProjectView(Long id, String name, String path, LocalDateTime createdAt) {
}
