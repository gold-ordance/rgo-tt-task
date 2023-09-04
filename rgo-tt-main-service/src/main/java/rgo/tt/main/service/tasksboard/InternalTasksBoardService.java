package rgo.tt.main.service.tasksboard;

import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.repository.tasksboard.TasksBoardRepository;

import java.util.List;
import java.util.Optional;

public class InternalTasksBoardService implements TasksBoardService {

    private final TasksBoardRepository repository;

    public InternalTasksBoardService(TasksBoardRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TasksBoard> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TasksBoard> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId);
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        return repository.save(board);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        return repository.deleteByEntityId(entityId);
    }
}
