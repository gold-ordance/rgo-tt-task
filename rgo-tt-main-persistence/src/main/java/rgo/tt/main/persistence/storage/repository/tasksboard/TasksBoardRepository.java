package rgo.tt.main.persistence.storage.repository.tasksboard;

import rgo.tt.main.persistence.storage.entity.TasksBoard;

import java.util.List;
import java.util.Optional;

public interface TasksBoardRepository {

    List<TasksBoard> findAll();

    Optional<TasksBoard> findByEntityId(Long entityId);

    TasksBoard save(TasksBoard board);

    TasksBoard update(TasksBoard board);

    boolean deleteByEntityId(Long entityId);
}
