package rgo.tt.main.service.task;

import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.repository.task.TaskRepository;

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
        String clearedName = name.strip();
        return repository.findSoftlyByName(clearedName, boardId);
    }

    @Override
    public Task save(Task task) {
        Task cleared = clearSpaces(task);
        return repository.save(cleared);
    }

    @Override
    public Task update(Task task) {
        Task cleared = clearSpaces(task);
        return repository.update(cleared);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        return repository.deleteByEntityId(entityId);
    }

    private static Task clearSpaces(Task task) {
        return task.toBuilder()
                .setName(task.getName().strip())
                .setDescription(task.getDescription().strip())
                .build();
    }
}
