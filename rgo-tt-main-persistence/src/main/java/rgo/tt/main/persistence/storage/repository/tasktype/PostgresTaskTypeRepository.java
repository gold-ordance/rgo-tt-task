package rgo.tt.main.persistence.storage.repository.tasktype;

import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.sqlstatement.tasktype.TaskTypeSqlStatement;

import java.util.List;

public class PostgresTaskTypeRepository implements TaskTypeRepository {

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTaskTypeRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<TaskType> findAll() {
        SqlReadStatement<TaskType> statement = TaskTypeSqlStatement.findAll();
        return jdbc.query(statement);
    }
}
