package rgo.tt.main.persistence.storage.query;

public final class TaskStatusQuery {

    private static final String TABLE_NAME = "task_status";

    private TaskStatusQuery() {
    }

    public static String findByEntityId() {
        return select() + "WHERE entity_id = :entity_id";
    }

    private static String select() {
        return  "SELECT entity_id, " +
                "       name " +
                "FROM " + TABLE_NAME + " ts ";
    }
}
