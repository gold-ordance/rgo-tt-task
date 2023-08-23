package rgo.tt.main.persistence.storage.repository.task;

import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlresult.SqlCreateResult;
import rgo.tt.common.persistence.sqlresult.SqlDeleteResult;
import rgo.tt.common.persistence.sqlresult.SqlReadResult;
import rgo.tt.common.persistence.sqlresult.SqlUpdateResult;
import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.SqlDeleteStatement;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlUpdateStatement;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.sqlstatement.task.TaskSqlStatement;
import rgo.tt.main.persistence.storage.sqlstatement.tasksboard.TasksBoardSqlStatement;
import rgo.tt.main.persistence.storage.sqlstatement.taskstatus.TaskStatusSqlStatement;
import rgo.tt.main.persistence.storage.sqlstatement.tasktype.TaskTypeSqlStatement;

import java.util.List;
import java.util.Optional;
import java.util.function.LongFunction;
import java.util.function.Supplier;

import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.getFirstElement;

public class PostgresTaskRepository implements TaskRepository {

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTaskRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<Task> findAllForBoard(Long boardId) {
        checkBoardIdForExistence(boardId);
        SqlReadStatement<Task> statement = TaskSqlStatement.findAllForBoard(boardId);
        SqlReadResult<Task> result = jdbc.read(statement);
        return result.getEntities();
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        SqlReadStatement<Task> statement = TaskSqlStatement.findByEntityId(entityId);
        SqlReadResult<Task> result = jdbc.read(statement);
        return getFirstElement(result.getEntities());
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
        checkBoardIdForExistence(task.getBoard().getEntityId());
        checkTypeIdForExistence(task.getType().getEntityId());

        LongFunction<Task> fetchEntity = this::getEntityById;
        SqlCreateStatement<Task> statement = TaskSqlStatement.save(task, fetchEntity);
        SqlCreateResult<Task> result = jdbc.save(statement);

        return result.getEntity();
    }

    private void checkBoardIdForExistence(Long boardId) {
        String errorMsg = "The boardId not found in the storage.";
        SqlReadStatement<TasksBoard> statement = TasksBoardSqlStatement.findByEntityId(boardId);
        checkForExistence(statement, errorMsg);
    }

    private void checkTypeIdForExistence(Long typeId) {
        String errorMsg = "The typeId not found in the storage.";
        SqlReadStatement<TaskType> statement = TaskTypeSqlStatement.findByEntityId(typeId);
        checkForExistence(statement, errorMsg);
    }

    private void checkForExistence(SqlReadStatement<?> statement, String errorMsg) {
        SqlReadResult<?> result = jdbc.read(statement);

        if (result.getEntities().isEmpty()) {
            throw new InvalidEntityException(errorMsg);
        }
    }

    @Override
    public Task update(Task task) {
        checkStatusIdForExistence(task.getStatus().getEntityId());
        checkTypeIdForExistence(task.getType().getEntityId());

        Supplier<Task> fetchEntity = () -> getEntityById(task.getEntityId());
        SqlUpdateStatement<Task> statement = TaskSqlStatement.update(task, fetchEntity);
        SqlUpdateResult<Task> result = jdbc.update(statement);

        return result.getEntity();
    }

    private void checkStatusIdForExistence(Long statusId) {
        String errorMsg = "The statusId not found in the storage.";
        SqlReadStatement<TaskStatus> statement = TaskStatusSqlStatement.findByEntityId(statusId);
        checkForExistence(statement, errorMsg);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        SqlDeleteStatement statement = TaskSqlStatement.deleteByEntityId(entityId);
        SqlDeleteResult result = jdbc.delete(statement);
        return result.isDeleted();
    }
}
