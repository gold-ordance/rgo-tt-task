package rgo.tt.task.service.taskstatus;

import rgo.tt.task.persistence.storage.entity.TaskStatus;
import rgo.tt.task.persistence.storage.repository.taskstatus.TaskStatusRepository;

import java.util.List;

public class InternalTaskStatusService implements TaskStatusService {

    private final TaskStatusRepository repository;

    public InternalTaskStatusService(TaskStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TaskStatus> findAll() {
        return repository.findAll();
    }
}
