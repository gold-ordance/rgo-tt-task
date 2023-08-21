package rgo.tt.main.persistence.storage.repository.task;

import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.main.persistence.storage.entity.Task;

import java.util.List;
import java.util.Optional;

public class TxTaskRepositoryDecorator implements TaskRepository {

    private final TaskRepository delegate;
    private final DbTxManager tx;

    public TxTaskRepositoryDecorator(TaskRepository delegate, DbTxManager tx) {
        this.delegate = delegate;
        this.tx = tx;
    }

    @Override
    public List<Task> findAllForBoard(Long boardId) {
        return tx.tx(() -> delegate.findAllForBoard(boardId));
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        return tx.tx(() -> delegate.findByEntityId(entityId));
    }

    @Override
    public List<Task> findSoftlyByName(String name, Long boardId) {
        return tx.tx(() -> delegate.findSoftlyByName(name, boardId));
    }

    @Override
    public Task save(Task task) {
        return tx.tx(() -> delegate.save(task));
    }

    @Override
    public Task update(Task task) {
        return tx.tx(() -> delegate.update(task));
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        return tx.tx(() -> delegate.deleteByEntityId(entityId));
    }
}
