package rgo.tt.main.persistence.storage.repository.tasksboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.common.exceptions.PersistenceException;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlstatement.SqlStatement;
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.sqlstatement.TasksBoardSqlStatement;

import java.util.List;
import java.util.Optional;

public class PostgresTasksBoardRepository implements TasksBoardRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgresTasksBoardRepository.class);

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTasksBoardRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<TasksBoard> findAll() {
        SqlStatement<TasksBoard> statement = TasksBoardSqlStatement.findAll();
        return jdbc.query(statement);
    }

    @Override
    public Optional<TasksBoard> findByEntityId(Long entityId) {
        SqlStatement<TasksBoard> statement = TasksBoardSqlStatement.findByEntityId(entityId);
        List<TasksBoard> boards = jdbc.query(statement);
        return getFirstElement(boards);
    }

    private Optional<TasksBoard> getFirstElement(List<TasksBoard> boards) {
        if (boards.isEmpty()) {
            LOGGER.info("The board not found.");
            return Optional.empty();
        }

        if (boards.size() > 1) {
            throw new PersistenceException("The number of boards is not equal to 1.");
        }

        return Optional.of(boards.get(0));
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        SqlStatement<TasksBoard> statement = TasksBoardSqlStatement.save(board);
        int result = jdbc.save(statement);
        Number key = statement.getKeyHolder().getKey();

        if (result != 1 || key == null) {
            throw new PersistenceException("TasksBoard save error.");
        }

        Optional<TasksBoard> opt = findByEntityId(key.longValue());
        if (opt.isEmpty()) {
            throw new PersistenceException("TasksBoard save error during searching.");
        }

        return opt.get();
    }

    @Override
    public boolean deleteByEntityId(Long entityId) {
        SqlStatement<TasksBoard> statement = TasksBoardSqlStatement.deleteByEntityId(entityId);
        int result = jdbc.update(statement);
        return result == 1;
    }
}
