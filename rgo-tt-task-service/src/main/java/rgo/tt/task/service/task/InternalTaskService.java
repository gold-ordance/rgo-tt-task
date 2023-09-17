package rgo.tt.task.service.task;

import rgo.tt.task.persistence.storage.entity.Task;
import rgo.tt.task.persistence.storage.repository.task.TaskRepository;

import java.util.List;
import java.util.Optional;

public class InternalTaskService implements TaskService {

    private final TaskRepository repository;

    public InternalTaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Task> findAllForBoard(Long boardId) {
        return repository.findAllForBoard(boardId);
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId);
    }

    @Override
    public List<Task> findSoftlyByName(String name, Long boardId) {
        return repository.findSoftlyByName(name, boardId);
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
