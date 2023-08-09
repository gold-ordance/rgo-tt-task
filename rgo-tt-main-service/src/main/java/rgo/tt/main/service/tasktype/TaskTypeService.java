package rgo.tt.main.service.tasktype;

import rgo.tt.main.persistence.storage.entity.TaskType;

import java.util.List;

public interface TaskTypeService {

    List<TaskType> findAll();
}
