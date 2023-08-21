package rgo.tt.main.persistence.storage.repository.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.PersistenceException;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlstatement.SqlStatement;
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.sqlstatement.TaskSqlStatement;
import rgo.tt.main.persistence.storage.sqlstatement.TaskStatusSqlStatement;
import rgo.tt.main.persistence.storage.sqlstatement.TaskTypeSqlStatement;
import rgo.tt.main.persistence.storage.sqlstatement.TasksBoardSqlStatement;

import java.util.List;
import java.util.Optional;

public class PostgresTaskRepository implements TaskRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgresTaskRepository.class);

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTaskRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<Task> findAllForBoard(Long boardId) {
        checkBoardIdForExistence(boardId);
        SqlStatement<Task> statement = TaskSqlStatement.findAllForBoard(boardId);
        return jdbc.query(statement);
    }

    @Override
    public Optional<Task> findByEntityId(Long entityId) {
        SqlStatement<Task> statement = TaskSqlStatement.findByEntityId(entityId);
        List<Task> tasks = jdbc.query(statement);
        return getFirstElement(tasks);
    }

    private Optional<Task> getFirstElement(List<Task> tasks) {
        if (tasks.isEmpty()) {
            LOGGER.info("The task not found.");
            return Optional.empty();
        }

        if (tasks.size() > 1) {
            throw new PersistenceException("The number of tasks is not equal to 1.");
        }

        return Optional.of(tasks.get(0));
    }

    @Override
    public List<Task> findSoftlyByName(String name, Long boardId) {
        checkBoardIdForExistence(boardId);
        SqlStatement<Task> statement = TaskSqlStatement.findSoftlyByName(name, boardId);
        return jdbc.query(statement);
    }

    @Override
    public Task save(Task task) {
        checkBoardIdForExistence(task.getBoard().getEntityId());
        checkTypeIdForExistence(task.getType().getEntityId());

        SqlStatement<Task> statement = TaskSqlStatement.save(task);
        int result = jdbc.save(statement);
        Number key = statement.getKeyHolder().getKey();

        if (result != 1 || key == null) {
            throw new PersistenceException("Task save error.");
        }

        Optional<Task> opt = findByEntityId(key.longValue());
        if (opt.isEmpty()) {
            throw new PersistenceException("Task save error during searching.");
        }

        return opt.get();
    }

    private void checkBoardIdForExistence(Long boardId) {
        String errorMsg = "The boardId not found in the storage.";
        SqlStatement<TasksBoard> statement = TasksBoardSqlStatement.findByEntityId(boardId);
        checkForExistence(statement, errorMsg);
    }

    private void checkTypeIdForExistence(Long typeId) {
        String errorMsg = "The typeId not found in the storage.";
        SqlStatement<TaskType> statement = TaskTypeSqlStatement.findByEntityId(typeId);
        checkForExistence(statement, errorMsg);
    }

    private void checkForExistence(SqlStatement<?> statement, String errorMsg) {
        List<?> result = jdbc.query(statement);

        if (result.isEmpty()) {
            throw new InvalidEntityException(errorMsg);
        }
    }

    @Override
    public Task update(Task task) {
        checkStatusIdForExistence(task.getStatus().getEntityId());
        checkTypeIdForExistence(task.getType().getEntityId());

        SqlStatement<Task> statement = TaskSqlStatement.update(task);
        int result = jdbc.update(statement);

        if (result == 0) {
            throw new InvalidEntityException("The entityId not found in the storage.");
        }

        Optional<Task> opt = findByEntityId(task.getEntityId());
        if (opt.isEmpty()) {
            throw new PersistenceException("Task update error during searching.");
        }

        return opt.get();
    }

    private void checkStatusIdForExistence(Long statusId) {
        String errorMsg = "The statusId not found in the storage.";
        SqlStatement<TaskStatus> statement = TaskStatusSqlStatement.findByEntityId(statusId);
        checkForExistence(statement, errorMsg);
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        SqlStatement<Task> statement = TaskSqlStatement.deleteByEntityId(entityId);
        int result = jdbc.update(statement);
        return result == 1;
    }
}
