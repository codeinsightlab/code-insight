package lab.codeinsight.backend.project.model.vo;

import java.time.LocalDateTime;

public record ProjectView(Long id, String name, String path, LocalDateTime createdAt) {}
