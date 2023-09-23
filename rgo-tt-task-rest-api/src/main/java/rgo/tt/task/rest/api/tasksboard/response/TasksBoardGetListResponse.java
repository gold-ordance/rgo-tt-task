package rgo.tt.task.rest.api.tasksboard.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.task.rest.api.tasksboard.dto.TasksBoardDto;

import java.util.List;

public class TasksBoardGetListResponse implements Response {

    private Status status;
    private List<TasksBoardDto> boards;

    public static TasksBoardGetListResponse success(List<TasksBoardDto> boards) {
        TasksBoardGetListResponse response = new TasksBoardGetListResponse();
        response.status = Status.success();
        response.boards = boards;
        return response;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<TasksBoardDto> getBoards() {
        return boards;
    }

    public void setBoards(List<TasksBoardDto> boards) {
        this.boards = boards;
    }

    @Override
    public String toString() {
        return "TasksBoardGetListResponse{" +
                "status=" + status +
                ", boards=<size=" + boards.size() + ">" +
                '}';
    }
}
