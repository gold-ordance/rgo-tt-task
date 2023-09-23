package rgo.tt.task.rest.api.tasktype.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.task.rest.api.tasktype.dto.TaskTypeDto;

import java.util.List;

public class TaskTypeGetListResponse implements Response {

    private Status status;
    private List<TaskTypeDto> types;

    public static TaskTypeGetListResponse success(List<TaskTypeDto> types) {
        TaskTypeGetListResponse response = new TaskTypeGetListResponse();
        response.status = Status.success();
        response.types = types;
        return response;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<TaskTypeDto> getTypes() {
        return types;
    }

    public void setTypes(List<TaskTypeDto> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "TaskTypeGetListResponse{" +
                "status=" + status +
                ", types=<size=" + types.size() + ">" +
                '}';
    }
}
