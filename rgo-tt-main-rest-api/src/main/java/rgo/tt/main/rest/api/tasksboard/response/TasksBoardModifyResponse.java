package rgo.tt.main.rest.api.tasksboard.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

public class TasksBoardModifyResponse implements Response {

    private final Status status;
    private final TasksBoard board;

    private TasksBoardModifyResponse(Status status, TasksBoard board) {
        this.status = status;
        this.board = board;
    }

    public static TasksBoardModifyResponse saved(TasksBoard board) {
        return new TasksBoardModifyResponse(Status.success(StatusCode.STORED), board);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @SuppressWarnings("unused")
    public TasksBoard getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "TasksBoardModifyResponse{" +
                "status=" + status +
                ", board=" + board +
                '}';
    }
}
