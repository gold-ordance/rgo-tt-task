package rgo.tt.main.rest.api.tasksboard.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.main.persistence.storage.entity.TasksBoard;

import java.util.Optional;

import static rgo.tt.common.rest.api.ErrorResponse.notFound;

public class TasksBoardGetEntityResponse implements Response {

    private final Status status;
    private final TasksBoard board;

    private TasksBoardGetEntityResponse(Status status, TasksBoard board) {
        this.status = status;
        this.board = board;
    }

    public static Response from(Optional<TasksBoard> board) {
        return board.isPresent()
                ? success(board.get())
                : notFound();
    }

    private static TasksBoardGetEntityResponse success(TasksBoard board) {
        return new TasksBoardGetEntityResponse(Status.success(), board);
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
        return "TasksBoardGetEntityResponse{" +
                "status=" + status +
                ", board=" + board +
                '}';
    }
}

