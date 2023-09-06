package rgo.tt.main.persistence.storage.sqlstatement.task;

import rgo.tt.common.persistence.function.FetchEntityById;
import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.retry.RetryableSqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.retry.SqlRetryParameters;
import rgo.tt.main.persistence.storage.entity.Task;

public final class TaskRetryableSqlStatement {

    private TaskRetryableSqlStatement() {
    }

    public static RetryableSqlCreateStatement<Task> saveWithRetry(Task task, FetchEntityById<Task> function, SqlRetryParameters params) {
        SqlCreateStatement<Task> statement = TaskSqlStatement.save(task, function);
        return RetryableSqlCreateStatement.from(statement, params);
    }
}
