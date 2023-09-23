package rgo.tt.task.rest.api.tasksboard.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.task.rest.api.tasksboard.dto.TasksBoardDto;

public class TasksBoardModifyResponse implements Response {

    private Status status;
    private TasksBoardDto board;


    public static TasksBoardModifyResponse saved(TasksBoardDto board) {
        TasksBoardModifyResponse response = new TasksBoardModifyResponse();
        response.status = Status.success(StatusCode.STORED);
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

    @Override
    public String toString() {
        return "TasksBoardModifyResponse{" +
                "status=" + status +
                ", board=" + board +
                '}';
    }
}
