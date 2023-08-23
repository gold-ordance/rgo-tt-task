package rgo.tt.main.persistence.storage.repository.tasksboard;

import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlresult.SqlCreateResult;
import rgo.tt.common.persistence.sqlresult.SqlDeleteResult;
import rgo.tt.common.persistence.sqlresult.SqlReadResult;
import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.SqlDeleteStatement;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.sqlstatement.tasksboard.TasksBoardSqlStatement;

import java.util.List;
import java.util.Optional;
import java.util.function.LongFunction;

import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.getFirstElement;

public class PostgresTasksBoardRepository implements TasksBoardRepository {

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTasksBoardRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<TasksBoard> findAll() {
        SqlReadStatement<TasksBoard> statement = TasksBoardSqlStatement.findAll();
        SqlReadResult<TasksBoard> result = jdbc.read(statement);
        return result.getEntities();
    }

    @Override
    public Optional<TasksBoard> findByEntityId(Long entityId) {
        SqlReadStatement<TasksBoard> statement = TasksBoardSqlStatement.findByEntityId(entityId);
        SqlReadResult<TasksBoard> result = jdbc.read(statement);
        return getFirstElement(result.getEntities());
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        LongFunction<TasksBoard> fetchEntity = this::getEntityById;
        SqlCreateStatement<TasksBoard> statement = TasksBoardSqlStatement.save(board, fetchEntity);
        SqlCreateResult<TasksBoard> result = jdbc.save(statement);
        return result.getEntity();
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        SqlDeleteStatement statement = TasksBoardSqlStatement.deleteByEntityId(entityId);
        SqlDeleteResult result = jdbc.delete(statement);
        return result.isDeleted();
    }
}
