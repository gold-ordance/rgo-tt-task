package rgo.tt.task.persistence.storage.repository.taskstatus;

import rgo.tt.task.persistence.storage.entity.TaskStatus;

import java.util.List;

public interface TaskStatusRepository {

    List<TaskStatus> findAll();
}
