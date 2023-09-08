package rgo.tt.main.persistence.storage.repository.task;

import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.function.FetchEntity;
import rgo.tt.common.persistence.function.FetchEntityById;
import rgo.tt.common.persistence.sqlresult.SqlCreateResult;
import rgo.tt.common.persistence.sqlresult.SqlDeleteResult;
import rgo.tt.common.persistence.sqlresult.SqlReadResult;
import rgo.tt.common.persistence.sqlresult.SqlUpdateResult;
import rgo.tt.common.persistence.sqlstatement.SqlDeleteStatement;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlUpdateStatement;
import rgo.tt.common.persistence.sqlstatement.retry.RetryPolicyProperties;
import rgo.tt.common.persistence.sqlstatement.retry.RetryableSqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.retry.SqlRetryParameters;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.sqlstatement.task.TaskRetryableSqlStatement;
import rgo.tt.main.persistence.storage.sqlstatement.task.TaskSqlStatement;
import rgo.tt.main.persistence.storage.sqlstatement.tasksboard.TasksBoardSqlStatement;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.getFirstEntity;

public class PostgresTaskRepository implements TaskRepository {

    private final StatementJdbcTemplateAdapter jdbc;
    private final RetryPolicyProperties config;

    public PostgresTaskRepository(StatementJdbcTemplateAdapter jdbc, RetryPolicyProperties config) {
        this.jdbc = jdbc;
        this.config = config;
    }

    @Override
    public List<Task> findAllForBoard(Long boardId) {
        checkBoardIdForExistence(boardId);
        SqlReadStatement<Task> statement = TaskSqlStatement.findAllForBoard(boardId);
        SqlReadResult<Task> result = jdbc.read(statement);
        return result.getEntities();
    }

    private void checkBoardIdForExistence(Long boardId) {
        SqlReadStatement<TasksBoard> statement = TasksBoardSqlStatement.findByEntityId(boardId);
        SqlReadResult<?> result = jdbc.read(statement);

        if (result.getEntities().isEmpty()) {
            throw new InvalidEntityException("The boardId not found in the storage.");
        }
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        SqlReadStatement<Task> statement = TaskSqlStatement.findByEntityId(entityId);
        SqlReadResult<Task> result = jdbc.read(statement);
        return getFirstEntity(result.getEntities());
    }

    @Override
    public List<Task> findSoftlyByName(String name, Long boardId) {
        checkBoardIdForExistence(boardId);
        SqlReadStatement<Task> statement = TaskSqlStatement.findSoftlyByName(name, boardId);
        SqlReadResult<Task> result = jdbc.read(statement);
        return result.getEntities();
    }

    @Override
    public Task save(Task task) {
        FetchEntityById<Task> function = this::getEntityById;
        SqlRetryParameters retryParams = config.policy(Task.class, "save");
        RetryableSqlCreateStatement<Task> statement = TaskRetryableSqlStatement.saveWithRetry(task, function, retryParams);
        SqlCreateResult<Task> result = jdbc.save(statement);
        return result.getEntity();
    }

    @Override
    public Task update(Task task) {
        FetchEntity<Task> function = () -> getEntityById(task.getEntityId());
        SqlUpdateStatement<Task> statement = TaskSqlStatement.update(task, function);
        SqlUpdateResult<Task> result = jdbc.update(statement);
        return result.getEntity();
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        SqlDeleteStatement statement = TaskSqlStatement.deleteByEntityId(entityId);
        SqlDeleteResult result = jdbc.delete(statement);
        return result.isDeleted();
    }
}
