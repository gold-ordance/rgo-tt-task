package rgo.tt.main.persistence.storage.sqlstatement.taskstatus;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;

public final class TaskStatusSqlRequest {

    private static final String TABLE_NAME = "task_status";

    private TaskStatusSqlRequest() {
    }

    public static SqlRequest findAll() {
        return new SqlRequest(select());
    }

    public static SqlRequest findByEntityId(Long entityId) {
        String request = select() + "WHERE entity_id = :entity_id";
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return new SqlRequest(request, params);
    }

    private static String select() {
        return """
                SELECT entity_id,
                       name
                  FROM %s
                """.formatted(TABLE_NAME);
    }
}
