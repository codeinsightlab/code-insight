package lab.codeinsight.backend.project.dal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import lab.codeinsight.backend.project.model.entity.ProjectEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {

  private final AtomicLong idGenerator = new AtomicLong(1);
  private final List<ProjectEntity> storage = new ArrayList<>();

  public synchronized ProjectEntity save(ProjectEntity entity) {
    entity.setId(idGenerator.getAndIncrement());
    storage.add(entity);
    return entity;
  }

  public synchronized List<ProjectEntity> findAll() {
    return new ArrayList<>(storage);
  }

  public synchronized Optional<ProjectEntity> findById(Long id) {
    return storage.stream().filter(project -> project.getId().equals(id)).findFirst();
  }
}
