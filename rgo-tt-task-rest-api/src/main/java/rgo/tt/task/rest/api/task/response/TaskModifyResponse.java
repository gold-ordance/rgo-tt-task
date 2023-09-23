package rgo.tt.task.rest.api.task.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.task.rest.api.task.dto.TaskDto;

public class TaskModifyResponse implements Response {

    private Status status;
    private TaskDto task;

    public static TaskModifyResponse saved(TaskDto task) {
        return response(StatusCode.STORED, task);
    }

    public static TaskModifyResponse updated(TaskDto task) {
        return response(StatusCode.SUCCESS, task);
    }

    private static TaskModifyResponse response(StatusCode code, TaskDto task) {
        TaskModifyResponse response = new TaskModifyResponse();
        response.status = Status.success(code);
        response.task = task;
        return response;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TaskDto getTask() {
        return task;
    }

    public void setTask(TaskDto task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "TaskModifyResponse{" +
                "status=" + status +
                ", task=" + task +
                '}';
    }
}
