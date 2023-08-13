package rgo.tt.main.persistence.storage.repository.tasksboard;

import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

import java.util.List;
import java.util.Optional;

public class TxTasksBoardRepositoryDecorator implements TasksBoardRepository {

    private final TasksBoardRepository repository;
    private final DbTxManager tx;

    public TxTasksBoardRepositoryDecorator(TasksBoardRepository repository, DbTxManager tx) {
        this.repository = repository;
        this.tx = tx;
    }

    @Override
    public List<TasksBoard> findAll() {
        return tx.tx(repository::findAll);
    }

    @Override
    public Optional<TasksBoard> findByEntityId(Long entityId) {
        return tx.tx(() -> repository.findByEntityId(entityId));
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        return tx.tx(() -> repository.save(board));
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        return tx.tx(() -> repository.deleteByEntityId(entityId));
    }
}
