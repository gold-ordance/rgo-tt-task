package rgo.tt.main.service;

import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Optional<Task> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId);
    }

    public List<Task> findSoftlyByName(String name) {
        return repository.findSoftlyByName(name);
    }

    public Task save(Task task) {
        return repository.save(task);
    }

    public Task update(Task task) {
        return repository.update(task);
    }

    public void deleteByEntityId(Long entityId) {
        repository.deleteByEntityId(entityId);
    }
}
