package rgo.tt.task.persistence.storage.sqlstatement.task;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;
import rgo.tt.task.persistence.storage.entity.Task;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class TaskSqlRequest {

    private static final String TABLE_NAME = "task";

    private TaskSqlRequest() {
    }

    public static SqlRequest findAllForBoard(Long boardId) {
        String query = select() + "WHERE t.board_id = :board_id";
        MapSqlParameterSource params = new MapSqlParameterSource("board_id", boardId);
        return new SqlRequest(query, params);
    }

    public static SqlRequest findByEntityId(Long entityId) {
        String query = select() + "WHERE t.entity_id = :entity_id";
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return new SqlRequest(query, params);
    }

    public static SqlRequest findSoftlyByName(String name, Long boardId) {
        String query = select() + """
                  WHERE t.board_id = :board_id
                    AND lower(t.name) LIKE lower(:name)
                 """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("board_id", boardId)
                .addValue("name", "%" + name + "%");

        return new SqlRequest(query, params);
    }

    public static SqlRequest save(Task task) {
        String query = """
                INSERT INTO %table_name(name, description, number, board_id, type_id)
                VALUES(:name,
                       :description,
                       COALESCE((SELECT max(number) FROM %table_name WHERE board_id = :board_id), 0) + 1,
                       :board_id,
                       :type_id);
                """.replace("%table_name", TABLE_NAME);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", task.getName())
                .addValue("board_id", task.getBoard().getEntityId())
                .addValue("type_id", task.getType().getEntityId())
                .addValue("description", task.getDescription());

        return new SqlRequest(query, params);
    }

    public static SqlRequest update(Task task) {
        String query = """
                UPDATE %s
                   SET name = :name,
                       last_modified_date = :lmd,
                       description = :description,
                       type_id = :type_id,
                       status_id = :status_id
                 WHERE entity_id = :entity_id
                """.formatted(TABLE_NAME);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("entity_id", task.getEntityId())
                .addValue("name", task.getName())
                .addValue("lmd", LocalDateTime.now(ZoneOffset.UTC))
                .addValue("type_id", task.getType().getEntityId())
                .addValue("status_id", task.getStatus().getEntityId())
                .addValue("description", task.getDescription());

        return new SqlRequest(query, params);
    }

    public static SqlRequest deleteByEntityId(Long entityId) {
        String query = """
                DELETE
                  FROM %s
                 WHERE entity_id = :entity_id
                """.formatted(TABLE_NAME);

        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return new SqlRequest(query, params);
    }

    private static String select() {
        return """
                SELECT t.entity_id          AS t_entity_id,
                       t.name               AS t_name,
                       t.created_date       AS t_created_date,
                       t.last_modified_date AS t_last_modified_date,
                       t.description        AS t_description,
                       t.number             AS t_number,
                       tb.entity_id         AS tb_entity_id,
                       tb.name              AS tb_name,
                       tb.short_name        AS tb_short_name,
                       ts.entity_id         AS ts_entity_id,
                       ts.name              AS ts_name,
                       tt.entity_id         AS tt_entity_id,
                       tt.name              AS tt_name
                  FROM %s AS t
                       JOIN task_status AS ts
                       ON t.status_id = ts.entity_id
                       
                       JOIN tasks_board tb
                       ON t.board_id = tb.entity_id
                       
                       JOIN task_type tt
                       ON t.type_id = tt.entity_id
                """.formatted(TABLE_NAME);
    }
}
