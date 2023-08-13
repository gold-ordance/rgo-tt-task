package rgo.tt.main.persistence.storage.query;

public final class TasksBoardQuery {

    private static final String TABLE_NAME = "tasks_board";

    private TasksBoardQuery() {
    }

    public static String findAll() {
        return select();
    }

    public static String findByEntityId() {
        return select() + "WHERE entity_id = :entity_id";
    }

    public static String save() {
        return """
                INSERT INTO %s(name, short_name)
                VALUES(:name, :short_name)
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
                SELECT entity_id,
                       name,
                       short_name
                  FROM %s
                """.formatted(TABLE_NAME);
    }
}
