package rgo.tt.main.persistence.storage.sqlstatement.task;

import org.springframework.jdbc.core.RowMapper;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;
import rgo.tt.common.persistence.sqlstatement.SqlWriteStatement;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

public final class TaskSqlStatement {

    private TaskSqlStatement() {
    }

    public static SqlReadStatement<Task> findAllForBoard(Long boardId) {
        SqlRequest request = TaskSqlRequest.findAllForBoard(boardId);
        return SqlReadStatement.from(request, TASK_ROW_MAPPER);
    }

    public static SqlReadStatement<Task> findByEntityId(Long entityId) {
        SqlRequest request = TaskSqlRequest.findByEntityId(entityId);
        return SqlReadStatement.from(request, TASK_ROW_MAPPER);
    }

    public static SqlReadStatement<Task> findSoftlyByName(String name, Long boardId) {
        SqlRequest request = TaskSqlRequest.findSoftlyByName(name, boardId);
        return SqlReadStatement.from(request, TASK_ROW_MAPPER);
    }

    public static SqlWriteStatement save(Task task) {
        SqlRequest request = TaskSqlRequest.save(task);
        return SqlWriteStatement.from(request);
    }

    public static SqlWriteStatement update(Task task) {
        SqlRequest request = TaskSqlRequest.update(task);
        return SqlWriteStatement.from(request);
    }

    public static SqlWriteStatement deleteByEntityId(Long entityId) {
        SqlRequest request = TaskSqlRequest.deleteByEntityId(entityId);
        return SqlWriteStatement.from(request);
    }

    private static final RowMapper<Task> TASK_ROW_MAPPER = (rs, num) -> Task.builder()
            .setEntityId(rs.getLong("t_entity_id"))
            .setName(rs.getString("t_name"))
            .setCreatedDate(rs.getTimestamp("t_created_date").toLocalDateTime())
            .setLastModifiedDate(rs.getTimestamp("t_last_modified_date").toLocalDateTime())
            .setDescription(rs.getString("t_description"))
            .setBoard(TasksBoard.builder()
                    .setEntityId(rs.getLong("tb_entity_id"))
                    .setName(rs.getString("tb_name"))
                    .setShortName(rs.getString("tb_short_name"))
                    .build())
            .setType(TaskType.builder()
                    .setEntityId(rs.getLong("tt_entity_id"))
                    .setName(rs.getString("tt_name"))
                    .build())
            .setStatus(TaskStatus.builder()
                    .setEntityId(rs.getLong("ts_entity_id"))
                    .setName(rs.getString("ts_name"))
                    .build())
            .build();
}