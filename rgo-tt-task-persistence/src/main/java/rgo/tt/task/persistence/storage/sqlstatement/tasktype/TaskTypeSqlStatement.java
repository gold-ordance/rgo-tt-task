package rgo.tt.task.persistence.storage.sqlstatement.tasktype;

import org.springframework.jdbc.core.RowMapper;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;
import rgo.tt.task.persistence.storage.entity.TaskType;

public final class TaskTypeSqlStatement {

    private TaskTypeSqlStatement() {
    }

    public static SqlReadStatement<TaskType> findAll() {
        SqlRequest request = TaskTypeSqlRequest.findAll();
        return SqlReadStatement.from(request, TASK_TYPE_ROW_MAPPER);
    }

    private static final RowMapper<TaskType> TASK_TYPE_ROW_MAPPER = (rs, num) -> TaskType.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .build();
}
