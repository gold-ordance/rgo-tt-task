package rgo.tt.main.service.taskstatus;

import rgo.tt.main.persistence.storage.entity.TaskStatus;

import java.util.List;

public interface TaskStatusService {

    List<TaskStatus> findAll();
}
