package rgo.tt.main.rest.api.tasksboard;

import rgo.tt.main.persistence.storage.entity.TasksBoard;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardSaveRequest;

public final class TasksBoardMapper {

    private TasksBoardMapper() {
    }

    public static TasksBoard map(TasksBoardSaveRequest rq) {
        return TasksBoard.builder()
                .setName(rq.getName())
                .build();
    }
}
