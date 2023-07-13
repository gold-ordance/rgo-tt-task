package rgo.tt.main.rest.api.task.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.main.persistence.storage.entity.Task;

import java.util.List;

public class TaskGetListResponse implements Response {

    private final Status status;
    private final List<Task> tasks;

    private TaskGetListResponse(Status status, List<Task> tasks) {
        this.status = status;
        this.tasks = tasks;
    }

    public static TaskGetListResponse success(List<Task> tasks) {
        return new TaskGetListResponse(Status.success(StatusCode.SUCCESS), tasks);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "TaskGetListResponse{" +
                "status=" + status +
                ", tasksSize=" + tasks.size() +
                '}';
    }
}
