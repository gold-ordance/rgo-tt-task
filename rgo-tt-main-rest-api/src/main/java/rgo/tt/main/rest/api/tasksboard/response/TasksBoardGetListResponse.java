package rgo.tt.main.rest.api.tasksboard.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

import java.util.List;

public class TasksBoardGetListResponse implements Response {

    private final Status status;
    private final List<TasksBoard> boards;

    private TasksBoardGetListResponse(Status status, List<TasksBoard> boards) {
        this.status = status;
        this.boards = boards;
    }

    public static TasksBoardGetListResponse success(List<TasksBoard> boards) {
        return new TasksBoardGetListResponse(Status.success(StatusCode.SUCCESS), boards);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public List<TasksBoard> getBoards() {
        return boards;
    }

    @Override
    public String toString() {
        return "TasksBoardGetListResponse{" +
                "status=" + status +
                ", boardsSize=" + boards.size() +
                '}';
    }
}
