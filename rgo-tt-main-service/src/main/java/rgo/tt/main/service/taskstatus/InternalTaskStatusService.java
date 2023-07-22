package rgo.tt.main.service.taskstatus;

import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.repository.TaskStatusRepository;

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
