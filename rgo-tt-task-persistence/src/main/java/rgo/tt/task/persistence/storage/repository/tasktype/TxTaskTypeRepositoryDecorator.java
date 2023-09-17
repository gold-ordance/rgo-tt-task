package rgo.tt.task.persistence.storage.repository.tasktype;

import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.task.persistence.storage.entity.TaskType;

import java.util.List;

public class TxTaskTypeRepositoryDecorator implements TaskTypeRepository {

    private final TaskTypeRepository delegate;
    private final DbTxManager tx;

    public TxTaskTypeRepositoryDecorator(TaskTypeRepository delegate, DbTxManager tx) {
        this.delegate = delegate;
        this.tx = tx;
    }

    @Override
    public List<TaskType> findAll() {
        return tx.tx(delegate::findAll);
    }
}
