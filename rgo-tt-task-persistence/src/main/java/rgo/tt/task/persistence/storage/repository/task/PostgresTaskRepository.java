package rgo.tt.task.persistence.storage.repository.task;

import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlresult.SqlCreateResult;
import rgo.tt.common.persistence.sqlresult.SqlDeleteResult;
import rgo.tt.common.persistence.sqlresult.SqlReadResult;
import rgo.tt.common.persistence.sqlresult.SqlUpdateResult;
import rgo.tt.common.persistence.sqlstatement.FetchEntity;
import rgo.tt.common.persistence.sqlstatement.FetchEntityById;
import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.SqlDeleteStatement;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlUpdateStatement;
import rgo.tt.task.persistence.storage.entity.Task;
import rgo.tt.task.persistence.storage.entity.TasksBoard;
import rgo.tt.task.persistence.storage.sqlstatement.task.TaskSqlStatement;
import rgo.tt.task.persistence.storage.sqlstatement.tasksboard.TasksBoardSqlStatement;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.getFirstEntity;

public class PostgresTaskRepository implements TaskRepository {

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTaskRepository(StatementJdbcTemplateAdapter jdbc) {
        this.jdbc = jdbc;
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
        SqlCreateStatement<Task> statement = TaskSqlStatement.save(task, function);
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
