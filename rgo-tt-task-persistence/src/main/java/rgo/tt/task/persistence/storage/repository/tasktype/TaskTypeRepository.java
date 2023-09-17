package rgo.tt.task.persistence.storage.repository.tasktype;

import rgo.tt.task.persistence.storage.entity.TaskType;

import java.util.List;

public interface TaskTypeRepository {

    List<TaskType> findAll();
}
