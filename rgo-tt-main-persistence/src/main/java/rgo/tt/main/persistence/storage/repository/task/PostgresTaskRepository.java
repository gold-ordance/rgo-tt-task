package rgo.tt.main.persistence.storage.repository.task;

import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.PersistenceException;
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlWriteStatement;
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
        return jdbc.query(statement);
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        SqlReadStatement<Task> statement = TaskSqlStatement.findByEntityId(entityId);
        List<Task> tasks = jdbc.query(statement);
        return getFirstElement(tasks);
    }

    @Override
    public List<Task> findSoftlyByName(String name, Long boardId) {
        checkBoardIdForExistence(boardId);
        SqlReadStatement<Task> statement = TaskSqlStatement.findSoftlyByName(name, boardId);
        return jdbc.query(statement);
    }

    @Override
    public Task save(Task task) {
        checkBoardIdForExistence(task.getBoard().getEntityId());
        checkTypeIdForExistence(task.getType().getEntityId());

        SqlWriteStatement statement = TaskSqlStatement.save(task);
        int result = jdbc.save(statement);
        Number key = statement.getKeyHolder().getKey();

        if (result != 1 || key == null) {
            throw new PersistenceException("Task save error.");
        }

        return getEntityById(key.longValue());
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
        List<?> result = jdbc.query(statement);

        if (result.isEmpty()) {
            throw new InvalidEntityException(errorMsg);
        }
    }

    @Override
    public Task update(Task task) {
        checkStatusIdForExistence(task.getStatus().getEntityId());
        checkTypeIdForExistence(task.getType().getEntityId());

        SqlWriteStatement statement = TaskSqlStatement.update(task);
        int result = jdbc.update(statement);

        if (result == 0) {
            throw new InvalidEntityException("The entityId not found in the storage.");
        }

        return getEntityById(task.getEntityId());
    }

    private void checkStatusIdForExistence(Long statusId) {
        String errorMsg = "The statusId not found in the storage.";
        SqlReadStatement<TaskStatus> statement = TaskStatusSqlStatement.findByEntityId(statusId);
        checkForExistence(statement, errorMsg);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        SqlWriteStatement statement = TaskSqlStatement.deleteByEntityId(entityId);
        int result = jdbc.update(statement);
        return result == 1;
    }
}
