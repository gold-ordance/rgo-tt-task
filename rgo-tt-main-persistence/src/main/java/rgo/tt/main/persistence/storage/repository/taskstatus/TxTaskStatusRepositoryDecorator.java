package rgo.tt.main.persistence.storage.repository.taskstatus;

import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TaskStatus;

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
