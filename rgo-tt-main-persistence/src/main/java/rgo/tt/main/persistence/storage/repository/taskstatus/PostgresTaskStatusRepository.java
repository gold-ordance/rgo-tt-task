package rgo.tt.main.persistence.storage.repository.taskstatus;

import rgo.tt.common.persistence.StatementJdbcTemplateDecorator;
import rgo.tt.common.persistence.sqlquery.SqlStatement;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.sqlstatement.TaskStatusSqlStatement;

import java.util.List;

public class PostgresTaskStatusRepository implements TaskStatusRepository {

    private final StatementJdbcTemplateDecorator jdbc;

    public PostgresTaskStatusRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<TaskStatus> findAll() {
        SqlStatement<TaskStatus> statement = TaskStatusSqlStatement.findAll();
        return jdbc.query(statement);
    }
}
