package lab.codeinsight.backend.project.model.entity;

import java.time.LocalDateTime;

/**
 * Persistence model representing a tracked project.
 */
public class ProjectEntity {
	private Long id;
	private String name;
	private String path;
	private LocalDateTime createdAt;

	/**
	 * Returns project id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets project id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns project name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets project name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns project filesystem path.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets project filesystem path.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Returns creation timestamp.
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets creation timestamp.
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
