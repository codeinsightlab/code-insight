package lab.codeinsight.backend.project.web;

import jakarta.validation.Valid;
import lab.codeinsight.backend.project.main.ProjectService;
import lab.codeinsight.backend.project.model.dto.CreateProjectRequest;
import lab.codeinsight.backend.project.model.vo.ProjectView;
import lab.codeinsight.backend.web.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ApiResponse<ProjectView> createProject(@Valid @RequestBody CreateProjectRequest request) {
        return ApiResponse.ok(projectService.create(request));
    }

    @GetMapping
    public ApiResponse<List<ProjectView>> listProjects() {
        return ApiResponse.ok(projectService.list());
    }
}
