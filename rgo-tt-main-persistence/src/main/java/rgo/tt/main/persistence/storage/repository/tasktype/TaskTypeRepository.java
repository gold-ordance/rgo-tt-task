package rgo.tt.main.persistence.storage.repository.tasktype;

import rgo.tt.main.persistence.storage.entity.TaskType;

import java.util.List;

public interface TaskTypeRepository {

    List<TaskType> findAll();
}
