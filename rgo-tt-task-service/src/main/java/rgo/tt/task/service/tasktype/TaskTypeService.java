package rgo.tt.task.service.tasktype;

import rgo.tt.task.persistence.storage.entity.TaskType;

import java.util.List;

public interface TaskTypeService {

    List<TaskType> findAll();
}
