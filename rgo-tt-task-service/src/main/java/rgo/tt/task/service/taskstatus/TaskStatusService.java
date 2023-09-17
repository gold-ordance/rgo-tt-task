package rgo.tt.task.service.taskstatus;

import rgo.tt.task.persistence.storage.entity.TaskStatus;

import java.util.List;

public interface TaskStatusService {

    List<TaskStatus> findAll();
}
