package rgo.tt.main.persistence.storage.repository.tasktype;

import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlquery.SqlStatement;
import rgo.tt.main.persistence.storage.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.sqlstatement.TaskTypeSqlStatement;

import java.util.List;

public class PostgresTaskTypeRepository implements TaskTypeRepository {

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTaskTypeRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<TaskType> findAll() {
        SqlStatement<TaskType> statement = TaskTypeSqlStatement.findAll();
        return jdbc.query(statement);
    }
}
