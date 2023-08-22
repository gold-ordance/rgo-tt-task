package rgo.tt.main.persistence.storage.repository.tasksboard;

import rgo.tt.common.exceptions.PersistenceException;
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlWriteStatement;
import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.persistence.storage.sqlstatement.tasksboard.TasksBoardSqlStatement;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.getFirstElement;

public class PostgresTasksBoardRepository implements TasksBoardRepository {

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresTasksBoardRepository(DbTxManager dataSource) {
        this.jdbc = dataSource.jdbc();
    }

    @Override
    public List<TasksBoard> findAll() {
        SqlReadStatement<TasksBoard> statement = TasksBoardSqlStatement.findAll();
        return jdbc.query(statement);
    }

    @Override
    public Optional<TasksBoard> findByEntityId(Long entityId) {
        SqlReadStatement<TasksBoard> statement = TasksBoardSqlStatement.findByEntityId(entityId);
        List<TasksBoard> boards = jdbc.query(statement);
        return getFirstElement(boards);
    }

    @Override
    public TasksBoard save(TasksBoard board) {
        SqlWriteStatement statement = TasksBoardSqlStatement.save(board);
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
        SqlWriteStatement statement = TasksBoardSqlStatement.deleteByEntityId(entityId);
        int result = jdbc.update(statement);
        return result == 1;
    }
}
