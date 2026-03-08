package lab.codeinsight.backend.project.main;

import lab.codeinsight.backend.project.dal.ProjectRepository;
import lab.codeinsight.backend.project.model.dto.CreateProjectRequest;
import lab.codeinsight.backend.project.model.entity.ProjectEntity;
import lab.codeinsight.backend.project.model.vo.ProjectView;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectView create(CreateProjectRequest request) {
        ProjectEntity entity = new ProjectEntity();
        entity.setName(request.name());
        entity.setPath(request.path());
        entity.setCreatedAt(LocalDateTime.now());

        ProjectEntity saved = projectRepository.save(entity);
        return toView(saved);
    }

    public List<ProjectView> list() {
        return projectRepository.findAll().stream().map(this::toView).toList();
    }

    private ProjectView toView(ProjectEntity entity) {
        return new ProjectView(entity.getId(), entity.getName(), entity.getPath(), entity.getCreatedAt());
    }
}
