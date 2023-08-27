package rgo.tt.main.rest.api.tasktype.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.main.persistence.storage.entity.TaskType;

import java.util.List;

public class TaskTypeGetListResponse implements Response {

    private final Status status;
    private final List<TaskType> types;

    private TaskTypeGetListResponse(Status status, List<TaskType> types) {
        this.status = status;
        this.types = types;
    }

    public static TaskTypeGetListResponse success(List<TaskType> types) {
        return new TaskTypeGetListResponse(Status.success(), types);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public List<TaskType> getTypes() {
        return types;
    }
}
