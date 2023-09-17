package rgo.tt.task.persistence.storage.repository.tasksboard;

import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.task.persistence.storage.entity.TasksBoard;

import java.util.List;
import java.util.Optional;

public class TxTasksBoardRepositoryDecorator implements TasksBoardRepository {

    private final TasksBoardRepository delegate;
    private final DbTxManager tx;

    public TxTasksBoardRepositoryDecorator(TasksBoardRepository delegate, DbTxManager tx) {
        this.delegate = delegate;
        this.tx = tx;
    }

    @Override
    public List<TasksBoard> findAll() {
        return tx.tx(delegate::findAll);
    }

    @Override
    public Optional<TasksBoard> findByEntityId(Long entityId) {
        return tx.tx(() -> delegate.findByEntityId(entityId));
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        return tx.tx(() -> delegate.save(board));
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        return tx.tx(() -> delegate.deleteByEntityId(entityId));
    }
}
