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
        return  "INSERT INTO " + TABLE_NAME + "(name) " +
                "VALUES(:name)";
    }

    public static String update() {
        return  "UPDATE " + TABLE_NAME + " " +
                "SET name = :name " +
                "WHERE entity_id = :entity_id";
    }

    public static String deleteByEntityId() {
        return  "DELETE FROM " + TABLE_NAME + " " +
                "WHERE entity_id = :entity_id";
    }

    private static String select() {
        return  "SELECT entity_id, " +
                "       name " +
                "FROM " + TABLE_NAME + " tb ";
    }
}
