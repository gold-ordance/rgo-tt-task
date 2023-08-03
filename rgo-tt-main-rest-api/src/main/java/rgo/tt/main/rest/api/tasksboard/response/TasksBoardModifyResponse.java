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
        return response(StatusCode.STORED, board);
    }

    public static TasksBoardModifyResponse updated(TasksBoard board) {
        return response(StatusCode.SUCCESS, board);
    }

    private static TasksBoardModifyResponse response(StatusCode code, TasksBoard board) {
        return new TasksBoardModifyResponse(Status.success(code), board);
    }

    @Override
    public Status getStatus() {
        return status;
    }

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
