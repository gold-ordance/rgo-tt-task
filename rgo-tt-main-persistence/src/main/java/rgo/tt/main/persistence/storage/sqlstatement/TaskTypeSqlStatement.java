package rgo.tt.main.persistence.storage.sqlstatement;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import rgo.tt.common.persistence.sqlquery.SqlStatement;
import rgo.tt.main.persistence.storage.entity.TaskType;

public final class TaskTypeSqlStatement {

    private static final String TABLE_NAME = "task_type";

    private TaskTypeSqlStatement() {
    }

    public static SqlStatement<TaskType> findAll() {
        return SqlStatement.from(select(), TASK_TYPE_ROW_MAPPER);
    }

    public static SqlStatement<TaskType> findByEntityId(Long entityId) {
        String request = select() + "WHERE entity_id = :entity_id";
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return SqlStatement.from(request, TASK_TYPE_ROW_MAPPER, params);
    }

    private static String select() {
        return """
                SELECT entity_id,
                       name
                  FROM %s
                """.formatted(TABLE_NAME);
    }

    private static final RowMapper<TaskType> TASK_TYPE_ROW_MAPPER = (rs, num) -> TaskType.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .build();
}
