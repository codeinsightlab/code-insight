package lab.codeinsight.backend.project.main;

import java.time.LocalDateTime;
import java.util.List;
import lab.codeinsight.backend.project.dal.ProjectRepository;
import lab.codeinsight.backend.project.model.dto.CreateProjectRequest;
import lab.codeinsight.backend.project.model.entity.ProjectEntity;
import lab.codeinsight.backend.project.model.vo.ProjectView;
import org.springframework.stereotype.Service;

/**
 * Application service for project creation and query operations.
 */
@Service
public class ProjectService {

	private final ProjectRepository projectRepository;

	/**
	 * Creates project service with repository dependency.
	 */
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	/**
	 * Creates a new project record from request payload.
	 */
	public ProjectView create(CreateProjectRequest request) {
		ProjectEntity entity = new ProjectEntity();
		entity.setName(request.name());
		entity.setPath(request.path());
		entity.setCreatedAt(LocalDateTime.now());

		ProjectEntity saved = projectRepository.save(entity);
		return toView(saved);
	}

	/**
	 * Returns all projects in insertion order.
	 */
	public List<ProjectView> list() {
		return projectRepository.findAll().stream().map(this::toView).toList();
	}

	/**
	 * Converts persistence entity into API-facing view model.
	 */
	private ProjectView toView(ProjectEntity entity) {
		return new ProjectView(entity.getId(), entity.getName(), entity.getPath(), entity.getCreatedAt());
	}
}
