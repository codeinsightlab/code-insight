package lab.codeinsight.backend.project.web;

import jakarta.validation.Valid;
import java.util.List;
import lab.codeinsight.backend.project.main.ProjectService;
import lab.codeinsight.backend.project.model.dto.CreateProjectRequest;
import lab.codeinsight.backend.project.model.vo.ProjectView;
import lab.codeinsight.backend.web.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP controller exposing project CRUD-lite APIs.
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectService projectService;

	/**
	 * Creates project controller with service dependency.
	 */
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	/**
	 * Creates a project from request body.
	 */
	@PostMapping
	public ApiResponse<ProjectView> createProject(@Valid @RequestBody CreateProjectRequest request) {
		return ApiResponse.ok(projectService.create(request));
	}

	/**
	 * Lists all existing projects.
	 */
	@GetMapping
	public ApiResponse<List<ProjectView>> listProjects() {
		return ApiResponse.ok(projectService.list());
	}
}
