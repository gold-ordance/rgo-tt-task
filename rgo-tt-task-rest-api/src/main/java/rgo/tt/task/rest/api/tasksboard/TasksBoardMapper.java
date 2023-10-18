package rgo.tt.task.rest.api.tasksboard;

import rgo.tt.task.persistence.storage.entity.TasksBoard;
import rgo.tt.task.rest.api.tasksboard.dto.TasksBoardDto;
import rgo.tt.task.rest.api.tasksboard.request.TasksBoardSaveRequest;

import java.util.List;

public final class TasksBoardMapper {

    private TasksBoardMapper() {
    }

    public static TasksBoardDto map(TasksBoard board) {
        TasksBoardDto dto = new TasksBoardDto();
        dto.setEntityId(board.getEntityId());
        dto.setName(board.getName());
        dto.setShortName(board.getShortName());
        return dto;
    }

    public static List<TasksBoardDto> map(List<TasksBoard> boards) {
        return boards.stream()
                .map(TasksBoardMapper::map)
                .toList();
    }

    public static TasksBoard map(TasksBoardSaveRequest rq) {
        return TasksBoard.builder()
                .setName(rq.getName())
                .build();
    }

    public static TasksBoard map(Long entityId) {
        return TasksBoard.builder().setEntityId(entityId).build();
    }
}
