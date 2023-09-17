package rgo.tt.task.persistence.storage.sqlstatement.tasktype;

import rgo.tt.common.persistence.sqlstatement.SqlRequest;

public final class TaskTypeSqlRequest {

    private static final String TABLE_NAME = "task_type";

    private TaskTypeSqlRequest() {
    }

    public static SqlRequest findAll() {
        return new SqlRequest(select());
    }

    private static String select() {
        return """
                SELECT entity_id,
                       name
                  FROM %s
                """.formatted(TABLE_NAME);
    }
}
