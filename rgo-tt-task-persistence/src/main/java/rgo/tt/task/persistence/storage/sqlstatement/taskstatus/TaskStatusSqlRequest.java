package rgo.tt.task.persistence.storage.sqlstatement.taskstatus;

import rgo.tt.common.persistence.sqlstatement.SqlRequest;

public final class TaskStatusSqlRequest {

    private static final String TABLE_NAME = "task_status";

    private TaskStatusSqlRequest() {
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
