package rgo.tt.main.persistence.storage.repository.taskstatus;

import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlstatement.SqlStatement;
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.sqlstatement.TaskStatusSqlStatement;

import java.util.List;

public class PostgresTaskStatusRepository implements TaskStatusRepository {

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTaskStatusRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<TaskStatus> findAll() {
        SqlStatement<TaskStatus> statement = TaskStatusSqlStatement.findAll();
        return jdbc.query(statement);
    }
}
