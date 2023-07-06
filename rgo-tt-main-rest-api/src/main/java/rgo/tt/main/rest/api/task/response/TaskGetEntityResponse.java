package rgo.tt.main.rest.api.task.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.main.persistence.storage.entity.Task;

public class TaskGetEntityResponse implements Response {

    private final Status status;
    private final Task task;

    private TaskGetEntityResponse(Status status, Task task) {
        this.status = status;
        this.task = task;
    }

    public static TaskGetEntityResponse success(Task task) {
        return new TaskGetEntityResponse(Status.success(StatusCode.SUCCESS), task);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public Task getTask() {
        return task;
    }
}

