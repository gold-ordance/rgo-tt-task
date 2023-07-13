package rgo.tt.main.rest.api.task.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.main.persistence.storage.entity.Task;

public class TaskModifyResponse implements Response {

    private final Status status;
    private final Task task;

    private TaskModifyResponse(Status status, Task task) {
        this.status = status;
        this.task = task;
    }

    public static TaskModifyResponse saved(Task task) {
        return response(StatusCode.STORED, task);
    }

    public static TaskModifyResponse updated(Task task) {
        return response(StatusCode.SUCCESS, task);
    }

    private static TaskModifyResponse response(StatusCode code, Task task) {
        return new TaskModifyResponse(Status.success(code), task);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public Task getTask() {
        return task;
    }

    @Override
    public String toString() {
        return "TaskModifyResponse{" +
                "status=" + status +
                ", task=" + task +
                '}';
    }
}
