package rgo.tt.main.persistence.storage.query;

public final class TaskQuery {

    private static final String TABLE_NAME = "task";

    private TaskQuery() {
    }

    public static String findAll() {
        return select() + "WHERE t.board_id = :board_id";
    }

    public static String findByEntityId() {
        return select() + "WHERE t.entity_id = :entity_id";
    }

    public static String findSoftlyByName() {
        return select() + """
                WHERE t.board_id = :board_id
                  AND lower(t.name) LIKE lower(:name)
                 """;
    }

    public static String save() {
        return """
                INSERT INTO %s(name, board_id, description)
                VALUES(:name, :board_id, :description)
                """.formatted(TABLE_NAME);
    }

    public static String update() {
        return """
                UPDATE %s
                   SET name = :name,
                       last_modified_date = :lmd,
                       status_id = :status_id,
                       description = :description
                 WHERE entity_id = :entity_id
                """.formatted(TABLE_NAME);
    }

    public static String deleteByEntityId() {
        return """
                DELETE
                  FROM %s
                 WHERE entity_id = :entity_id
                """.formatted(TABLE_NAME);
    }

    private static String select() {
        return """
                SELECT t.entity_id          AS t_entity_id,
                       t.name               AS t_name,
                       t.created_date       AS t_created_date,
                       t.last_modified_date AS t_last_modified_date,
                       tb.entity_id         AS tb_entity_id,
                       tb.name              AS tb_name,
                       ts.entity_id         AS ts_entity_id,
                       ts.name              AS ts_name,
                       t.description        AS t_description
                  FROM %s AS t
                       JOIN task_status AS ts
                       ON t.status_id = ts.entity_id
                       
                       JOIN tasks_board tb
                       ON t.board_id = tb.entity_id
                """.formatted(TABLE_NAME);
    }
}
