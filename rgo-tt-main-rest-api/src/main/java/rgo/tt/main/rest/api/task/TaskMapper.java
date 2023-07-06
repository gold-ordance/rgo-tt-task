package rgo.tt.main.rest.api.task;

import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.rest.api.task.request.TaskSaveRequest;
import rgo.tt.main.rest.api.task.request.TaskPutRequest;

public final class TaskMapper {

    private TaskMapper() {
    }

    public static Task map(TaskSaveRequest rq) {
        return Task.builder()
                .setName(rq.getName())
                .build();
    }

    public static Task map(TaskPutRequest rq) {
        return Task.builder()
                .setEntityId(rq.getEntityId())
                .setName(rq.getName())
                .build();
    }
}
