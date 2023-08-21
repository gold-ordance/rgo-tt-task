package rgo.tt.main.service.task;

import rgo.tt.main.persistence.storage.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAllForBoard(Long boardId);

    Optional<Task> findByEntityId(Long entityId);

    List<Task> findSoftlyByName(String name, Long boardId);

    Task save(Task task);

    Task update(Task task);

    boolean deleteByEntityId(Long entityId);
}
