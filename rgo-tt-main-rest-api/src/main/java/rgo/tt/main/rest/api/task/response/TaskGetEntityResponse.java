package rgo.tt.main.rest.api.task.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.main.persistence.storage.entity.Task;

import java.util.Optional;

import static rgo.tt.common.rest.api.ErrorResponse.notFound;

public class TaskGetEntityResponse implements Response {

    private final Status status;
    private final Task task;

    private TaskGetEntityResponse(Status status, Task task) {
        this.status = status;
        this.task = task;
    }

    public static Response from(Optional<Task> opt) {
        return opt.isPresent()
                ? success(opt.get())
                : notFound();
    }

    private static TaskGetEntityResponse success(Task task) {
        return new TaskGetEntityResponse(Status.success(), task);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @SuppressWarnings("unused")
    public Task getTask() {
        return task;
    }

    @Override
    public String toString() {
        return "TaskGetEntityResponse{" +
                "status=" + status +
                ", task=" + task +
                '}';
    }
}

