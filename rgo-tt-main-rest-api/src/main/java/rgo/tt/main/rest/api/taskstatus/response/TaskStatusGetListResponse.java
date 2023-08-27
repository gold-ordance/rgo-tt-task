package rgo.tt.main.rest.api.taskstatus.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.main.persistence.storage.entity.TaskStatus;

import java.util.List;

public class TaskStatusGetListResponse implements Response {

    private final Status status;
    private final List<TaskStatus> taskStatuses;

    private TaskStatusGetListResponse(Status status, List<TaskStatus> taskStatuses) {
        this.status = status;
        this.taskStatuses = taskStatuses;
    }

    public static TaskStatusGetListResponse success(List<TaskStatus> statuses) {
        return new TaskStatusGetListResponse(Status.success(), statuses);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @SuppressWarnings("unused")
    public List<TaskStatus> getTaskStatuses() {
        return taskStatuses;
    }
}
