package rgo.tt.main.persistence.storage.sqlstatement;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import rgo.tt.common.persistence.sqlstatement.SqlStatement;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.persistence.storage.entity.TaskStatus;
import rgo.tt.main.persistence.storage.entity.TaskType;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class TaskSqlStatement {

    private static final String TABLE_NAME = "task";

    private TaskSqlStatement() {
    }

    public static SqlStatement<Task> findAllForBoard(Long boardId) {
        String query = select() + "WHERE t.board_id = :board_id";
        MapSqlParameterSource params = new MapSqlParameterSource("board_id", boardId);
        return SqlStatement.from(query, TASK_ROW_MAPPER, params);
    }

    public static SqlStatement<Task> findByEntityId(Long entityId) {
        String query = select() + "WHERE t.entity_id = :entity_id";
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return SqlStatement.from(query, TASK_ROW_MAPPER, params);
    }

    public static SqlStatement<Task> findSoftlyByName(String name, Long boardId) {
        String query = select() + """
                  WHERE t.board_id = :board_id
                    AND lower(t.name) LIKE lower(:name)
                 """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("board_id", boardId)
                .addValue("name", "%" + name + "%");

        return SqlStatement.from(query, TASK_ROW_MAPPER, params);
    }

    public static SqlStatement<Task> save(Task task) {
        String query = """
                INSERT INTO %s(name, description, board_id, type_id)
                VALUES(:name, :description, :board_id, :type_id)
                """.formatted(TABLE_NAME);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", task.getName())
                .addValue("board_id", task.getBoard().getEntityId())
                .addValue("type_id", task.getType().getEntityId())
                .addValue("description", task.getDescription());

        return SqlStatement.from(query, TASK_ROW_MAPPER, params);
    }

    public static SqlStatement<Task> update(Task task) {
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

        return SqlStatement.from(query, TASK_ROW_MAPPER, params);
    }

    public static SqlStatement<Task> deleteByEntityId(Long entityId) {
        String query = """
                DELETE
                  FROM %s
                 WHERE entity_id = :entity_id
                """.formatted(TABLE_NAME);

        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return SqlStatement.from(query, TASK_ROW_MAPPER, params);
    }

    private static String select() {
        return """
                SELECT t.entity_id          AS t_entity_id,
                       t.name               AS t_name,
                       t.created_date       AS t_created_date,
                       t.last_modified_date AS t_last_modified_date,
                       t.description        AS t_description,
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
