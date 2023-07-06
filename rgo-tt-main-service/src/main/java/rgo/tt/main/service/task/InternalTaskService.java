package rgo.tt.main.service.task;

import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

public class InternalTaskService implements TaskService {

    private final TaskRepository repository;

    public InternalTaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId);
    }

    @Override
    public List<Task> findSoftlyByName(String name) {
        return repository.findSoftlyByName(name);
    }

    @Override
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    public Task update(Task task) {
        return repository.update(task);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        return repository.deleteByEntityId(entityId);
    }
}
