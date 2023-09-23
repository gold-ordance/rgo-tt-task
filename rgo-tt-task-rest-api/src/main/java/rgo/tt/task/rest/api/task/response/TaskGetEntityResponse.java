package rgo.tt.task.rest.api.task.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.task.rest.api.task.dto.TaskDto;

public class TaskGetEntityResponse implements Response {

    private Status status;
    private TaskDto task;

    public static TaskGetEntityResponse success(TaskDto task) {
        TaskGetEntityResponse response = new TaskGetEntityResponse();
        response.status = Status.success();
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
}

