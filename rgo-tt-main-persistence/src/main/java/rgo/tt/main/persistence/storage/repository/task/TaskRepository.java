package rgo.tt.main.persistence.storage.repository.task;

import rgo.tt.common.persistence.CommonRepository;
import rgo.tt.main.persistence.storage.entity.Task;

import java.util.List;

public interface TaskRepository extends CommonRepository<Task> {

    List<Task> findAllForBoard(Long boardId);

    List<Task> findSoftlyByName(String name, Long boardId);

    Task save(Task task);

    Task update(Task task);

    boolean deleteByEntityId(Long entityId);
}
