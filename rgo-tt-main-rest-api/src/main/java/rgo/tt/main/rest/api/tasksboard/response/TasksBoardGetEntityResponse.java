package rgo.tt.main.rest.api.tasksboard.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

public class TasksBoardGetEntityResponse implements Response {

    private final Status status;
    private final TasksBoard board;

    private TasksBoardGetEntityResponse(Status status, TasksBoard board) {
        this.status = status;
        this.board = board;
    }

    public static TasksBoardGetEntityResponse success(TasksBoard board) {
        return new TasksBoardGetEntityResponse(Status.success(StatusCode.SUCCESS), board);
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
        return "TasksBoardGetEntityResponse{" +
                "status=" + status +
                ", board=" + board +
                '}';
    }
}

