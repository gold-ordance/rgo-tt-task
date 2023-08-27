package rgo.tt.main.persistence.storage.repository.tasksboard;

import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.function.FetchEntityById;
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

import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.getFirstEntity;

public class PostgresTasksBoardRepository implements TasksBoardRepository {

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTasksBoardRepository(StatementJdbcTemplateAdapter jdbc) {
        this.jdbc = jdbc;
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
        return getFirstEntity(result.getEntities());
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        FetchEntityById<TasksBoard> function = this::getEntityById;
        SqlCreateStatement<TasksBoard> statement = TasksBoardSqlStatement.save(board, function);
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
