package rgo.tt.task.service.tasksboard;

import rgo.tt.task.persistence.storage.entity.TasksBoard;

import java.util.List;
import java.util.Optional;

public interface TasksBoardService {

    List<TasksBoard> findAll();

    Optional<TasksBoard> findByEntityId(Long entityId);

    TasksBoard save(TasksBoard board);

    boolean deleteByEntityId(Long entityId);
}
