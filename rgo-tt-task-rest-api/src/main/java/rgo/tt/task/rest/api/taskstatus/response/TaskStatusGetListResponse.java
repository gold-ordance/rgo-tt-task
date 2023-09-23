package rgo.tt.task.rest.api.taskstatus.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.task.rest.api.taskstatus.dto.TaskStatusDto;

import java.util.List;

public class TaskStatusGetListResponse implements Response {

    private Status status;
    private List<TaskStatusDto> taskStatuses;

    public static TaskStatusGetListResponse success(List<TaskStatusDto> statuses) {
        TaskStatusGetListResponse response =  new TaskStatusGetListResponse();
        response.status = Status.success();
        response.taskStatuses = statuses;
        return response;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<TaskStatusDto> getTaskStatuses() {
        return taskStatuses;
    }

    public void setTaskStatuses(List<TaskStatusDto> taskStatuses) {
        this.taskStatuses = taskStatuses;
    }

    @Override
    public String toString() {
        return "TaskStatusGetListResponse{" +
                "status=" + status +
                ", taskStatuses=<size=" + taskStatuses.size() + ">" +
                '}';
    }
}
