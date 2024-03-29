package rgo.tt.task.persistence.storage.repository.taskstatus;

import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.task.persistence.storage.entity.TaskStatus;

import java.util.List;

public class TxTaskStatusRepositoryDecorator implements TaskStatusRepository {

    private final TaskStatusRepository delegate;
    private final DbTxManager tx;

    public TxTaskStatusRepositoryDecorator(TaskStatusRepository delegate, DbTxManager tx) {
        this.delegate = delegate;
        this.tx = tx;
    }

    @Override
    public List<TaskStatus> findAll() {
        return tx.tx(delegate::findAll);
    }
}
