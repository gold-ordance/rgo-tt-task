package rgo.tt.main.persistence.storage.sqlstatement.taskstatus;

import org.springframework.jdbc.core.RowMapper;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;
import rgo.tt.main.persistence.storage.entity.TaskStatus;

public final class TaskStatusSqlStatement {

    private TaskStatusSqlStatement() {
    }

    public static SqlReadStatement<TaskStatus> findAll() {
        SqlRequest request = TaskStatusSqlRequest.findAll();
        return SqlReadStatement.from(request, TASK_STATUS_ROW_MAPPER);
    }

    private static final RowMapper<TaskStatus> TASK_STATUS_ROW_MAPPER = (rs, num) -> TaskStatus.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .build();
}
