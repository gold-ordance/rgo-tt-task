package rgo.tt.task.rest.api.tasksboard.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.task.rest.api.tasksboard.dto.TasksBoardDto;

public class TasksBoardGetEntityResponse implements Response {

    private Status status;
    private TasksBoardDto board;

    public static TasksBoardGetEntityResponse success(TasksBoardDto board) {
        TasksBoardGetEntityResponse response = new TasksBoardGetEntityResponse();
        response.status = Status.success();
        response.board = board;
        return response;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TasksBoardDto getBoard() {
        return board;
    }

    public void setBoard(TasksBoardDto board) {
        this.board = board;
    }
}

