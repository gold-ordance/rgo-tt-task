package rgo.tt.task.rest.api.task.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.task.rest.api.task.dto.TaskDto;

import java.util.List;

public class TaskGetListResponse implements Response {

    private Status status;
    private List<TaskDto> tasks;

    public static TaskGetListResponse success(List<TaskDto> tasks) {
        TaskGetListResponse response = new TaskGetListResponse();
        response.status = Status.success();
        response.tasks = tasks;
        return response;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
