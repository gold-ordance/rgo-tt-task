package rgo.tt.main.persistence.storage.sqlstatement;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import rgo.tt.common.persistence.sqlstatement.SqlStatement;
import rgo.tt.main.persistence.storage.entity.TaskStatus;

public final class TaskStatusSqlStatement {

    private static final String TABLE_NAME = "task_status";

    private TaskStatusSqlStatement() {
    }

    public static SqlStatement<TaskStatus> findAll() {
        return SqlStatement.from(select(), TASK_STATUS_ROW_MAPPER);
    }

    public static SqlStatement<TaskStatus> findByEntityId(Long entityId) {
        String request = select() + "WHERE entity_id = :entity_id";
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return SqlStatement.from(request, TASK_STATUS_ROW_MAPPER, params);
    }

    private static String select() {
        return """
                SELECT entity_id,
                       name
                  FROM %s
                """.formatted(TABLE_NAME);
    }

    private static final RowMapper<TaskStatus> TASK_STATUS_ROW_MAPPER = (rs, num) -> TaskStatus.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .build();
}
