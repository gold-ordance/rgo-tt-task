package rgo.tt.main.persistence.storage.repository.taskstatus;

import rgo.tt.main.persistence.storage.entity.TaskStatus;

import java.util.List;

public interface TaskStatusRepository {

    List<TaskStatus> findAll();
}
