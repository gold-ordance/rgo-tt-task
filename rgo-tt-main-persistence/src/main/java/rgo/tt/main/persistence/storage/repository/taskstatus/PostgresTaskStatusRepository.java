package rgo.tt.main.persistence.storage.repository.taskstatus;

import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlresult.SqlReadResult;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.sqlstatement.taskstatus.TaskStatusSqlStatement;

import java.util.List;

public class PostgresTaskStatusRepository implements TaskStatusRepository {

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTaskStatusRepository(StatementJdbcTemplateAdapter jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<TaskStatus> findAll() {
        SqlReadStatement<TaskStatus> statement = TaskStatusSqlStatement.findAll();
        SqlReadResult<TaskStatus> result = jdbc.read(statement);
        return result.getEntities();
    }
}
