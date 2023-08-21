package rgo.tt.main.persistence.storage.sqlstatement;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import rgo.tt.common.persistence.sqlquery.SqlStatement;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

public final class TasksBoardSqlStatement {

    private static final String TABLE_NAME = "tasks_board";

    private TasksBoardSqlStatement() {
    }

    public static SqlStatement<TasksBoard> findAll() {
        return SqlStatement.from(select(), TASKS_BOARD_ROW_MAPPER);
    }

    public static SqlStatement<TasksBoard> findByEntityId(Long entityId) {
        String request = select() + "WHERE entity_id = :entity_id";
        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return SqlStatement.from(request, TASKS_BOARD_ROW_MAPPER, params);
    }

    public static SqlStatement<TasksBoard> save(TasksBoard board) {
        String request = """
                INSERT INTO %s(name, short_name)
                VALUES(:name, :short_name)
                """.formatted(TABLE_NAME);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", board.getName())
                .addValue("short_name", board.getShortName());

        return SqlStatement.from(request, TASKS_BOARD_ROW_MAPPER, params);
    }

    public static SqlStatement<TasksBoard> deleteByEntityId(Long entityId) {
        String request = """
                DELETE
                  FROM %s
                 WHERE entity_id = :entity_id
                """.formatted(TABLE_NAME);

        MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
        return SqlStatement.from(request, TASKS_BOARD_ROW_MAPPER, params);
    }

    private static String select() {
        return """
                SELECT entity_id,
                       name,
                       short_name
                  FROM %s
                """.formatted(TABLE_NAME);
    }

    private static final RowMapper<TasksBoard> TASKS_BOARD_ROW_MAPPER = (rs, rowNum) -> TasksBoard.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setName(rs.getString("name"))
            .setShortName(rs.getString("short_name"))
            .build();
}
