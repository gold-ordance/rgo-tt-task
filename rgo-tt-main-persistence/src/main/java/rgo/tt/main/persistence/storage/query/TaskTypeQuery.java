package rgo.tt.main.persistence.storage.query;

public final class TaskTypeQuery {

    private static final String TABLE_NAME = "task_type";

    private TaskTypeQuery() {
    }

    public static String findAll() {
        return select();
    }

    public static String findByEntityId() {
        return select() + "WHERE entity_id = :entity_id";
    }

    private static String select() {
        return """
                SELECT entity_id,
                       name
                  FROM %s
                """.formatted(TABLE_NAME);
    }
}
