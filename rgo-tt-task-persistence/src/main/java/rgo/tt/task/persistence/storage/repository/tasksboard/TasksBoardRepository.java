package rgo.tt.task.persistence.storage.repository.tasksboard;

import rgo.tt.common.persistence.CommonRepository;
import rgo.tt.task.persistence.storage.entity.TasksBoard;

import java.util.List;

public interface TasksBoardRepository extends CommonRepository<TasksBoard> {

    List<TasksBoard> findAll();

    TasksBoard save(TasksBoard board);

    boolean deleteByEntityId(Long entityId);
}
