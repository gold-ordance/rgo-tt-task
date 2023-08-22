package rgo.tt.main.persistence.storage.sqlstatement.tasksboard;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

public final class TasksBoardSqlRequest {

    private static final String TABLE_NAME = "tasks_board";

    private TasksBoardSqlRequest() {
    }

    public static SqlRequest findAll() {
        return new SqlRequest(select());
    }

    public static SqlRequest findByEntityId(Long entityId) {
        String request = select() + "WHERE entity_id = :entity_id";
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return new SqlRequest(request, params);
    }

    public static SqlRequest save(TasksBoard board) {
        String request = """
                INSERT INTO %s(name, short_name)
                VALUES(:name, :short_name)
                """.formatted(TABLE_NAME);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", board.getName())
                .addValue("short_name", board.getShortName());

        return new SqlRequest(request, params);
    }

    public static SqlRequest deleteByEntityId(Long entityId) {
        String request = """
                DELETE
                  FROM %s
                 WHERE entity_id = :entity_id
                """.formatted(TABLE_NAME);

        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return new SqlRequest(request, params);
    }

    private static String select() {
        return """
                SELECT entity_id,
                       name,
                       short_name
                  FROM %s
                """.formatted(TABLE_NAME);
    }
}
