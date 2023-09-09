package rgo.tt.main.persistence.storage.repository.task;

import rgo.tt.common.persistence.sqlstatement.retry.OperationParameters;
import rgo.tt.common.persistence.sqlstatement.retry.RetryManager;
import rgo.tt.common.persistence.sqlstatement.retry.RetryableOperation;
import rgo.tt.main.persistence.storage.entity.Task;

import java.util.List;
import java.util.Optional;

public class RetryableTaskRepositoryDecorator implements TaskRepository {

    private final TaskRepository delegate;
    private final RetryManager manager;

    public RetryableTaskRepositoryDecorator(TaskRepository delegate, RetryManager manager) {
        this.delegate = delegate;
        this.manager = manager;
    }

    @Override
    public List<Task> findAllForBoard(Long boardId) {
        return delegate.findAllForBoard(boardId);
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        return delegate.findByEntityId(entityId);
    }

    @Override
    public List<Task> findSoftlyByName(String name, Long boardId) {
        return delegate.findSoftlyByName(name, boardId);
    }

    @Override
    public Task save(Task task) {
        RetryableOperation<Task> operation = () -> delegate.save(task);
        OperationParameters params = OperationParameters.from(Task.class, "save");
        return manager.execute(operation, params);
    }

    @Override
    public Task update(Task task) {
        return delegate.update(task);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        return delegate.deleteByEntityId(entityId);
    }
}
