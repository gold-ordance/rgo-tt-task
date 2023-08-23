package rgo.tt.main.persistence.storage.sqlstatement.tasksboard;

import org.springframework.jdbc.core.RowMapper;
import rgo.tt.common.persistence.function.FetchEntityById;
import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.SqlDeleteStatement;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

public final class TasksBoardSqlStatement {

    private TasksBoardSqlStatement() {
    }

    public static SqlReadStatement<TasksBoard> findAll() {
        SqlRequest request = TasksBoardSqlRequest.findAll();
        return SqlReadStatement.from(request, TASKS_BOARD_ROW_MAPPER);
    }

    public static SqlReadStatement<TasksBoard> findByEntityId(Long entityId) {
        SqlRequest request = TasksBoardSqlRequest.findByEntityId(entityId);
        return SqlReadStatement.from(request, TASKS_BOARD_ROW_MAPPER);
    }

    public static SqlCreateStatement<TasksBoard> save(TasksBoard board, FetchEntityById<TasksBoard> function) {
        SqlRequest request = TasksBoardSqlRequest.save(board);
        return SqlCreateStatement.from(request, function);
    }

    public static SqlDeleteStatement deleteByEntityId(Long entityId) {
        SqlRequest request = TasksBoardSqlRequest.deleteByEntityId(entityId);
        return SqlDeleteStatement.from(request);
    }

    private static final RowMapper<TasksBoard> TASKS_BOARD_ROW_MAPPER = (rs, rowNum) -> TasksBoard.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .setShortName(rs.getString("short_name"))
            .build();
}
