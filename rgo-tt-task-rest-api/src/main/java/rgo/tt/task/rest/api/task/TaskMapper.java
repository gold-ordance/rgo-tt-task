package rgo.tt.task.rest.api.task;

import rgo.tt.task.persistence.storage.entity.Task;
import rgo.tt.task.rest.api.task.dto.TaskDto;
import rgo.tt.task.rest.api.task.request.TaskPutRequest;
import rgo.tt.task.rest.api.task.request.TaskSaveRequest;
import rgo.tt.task.rest.api.tasksboard.TasksBoardMapper;
import rgo.tt.task.rest.api.taskstatus.TaskStatusMapper;
import rgo.tt.task.rest.api.tasktype.TaskTypeMapper;

import java.util.List;

public final class TaskMapper {

    private TaskMapper() {
    }

    public static TaskDto map(Task task) {
        TaskDto dto = new TaskDto();
        dto.setEntityId(task.getEntityId());
        dto.setName(task.getName());
        dto.setCreatedDate(task.getCreatedDate());
        dto.setLastModifiedDate(task.getLastModifiedDate());
        dto.setDescription(task.getDescription());
        dto.setNumber(task.getNumber());
        dto.setBoard(TasksBoardMapper.map(task.getBoard()));
        dto.setType(TaskTypeMapper.map(task.getType()));
        dto.setStatus(TaskStatusMapper.map(task.getStatus()));
        return dto;
    }

    public static List<TaskDto> map(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper::map)
                .toList();
    }

    public static Task map(TaskSaveRequest rq) {
        return Task.builder()
                .setName(rq.getName())
                .setDescription(rq.getDescription())
                .setBoard(TasksBoardMapper.map(rq.getBoardId()))
                .setType(TaskTypeMapper.map(rq.getTypeId()))
                .setStatus(TaskStatusMapper.map(rq.getStatusId()))
                .build();
    }

    public static Task map(TaskPutRequest rq) {
        return Task.builder()
                .setEntityId(rq.getEntityId())
                .setName(rq.getName())
                .setDescription(rq.getDescription())
                .setType(TaskTypeMapper.map(rq.getTypeId()))
                .setStatus(TaskStatusMapper.map(rq.getStatusId()))
                .build();
    }
}
