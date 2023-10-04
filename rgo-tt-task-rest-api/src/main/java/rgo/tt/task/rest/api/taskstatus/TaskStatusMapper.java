package rgo.tt.task.rest.api.taskstatus;

import rgo.tt.task.persistence.storage.entity.TaskStatus;
import rgo.tt.task.rest.api.taskstatus.dto.TaskStatusDto;

import java.util.List;

public final class TaskStatusMapper {

    private TaskStatusMapper() {
    }

    public static TaskStatusDto map(TaskStatus status) {
        TaskStatusDto dto = new TaskStatusDto();
        dto.setEntityId(status.getEntityId());
        dto.setName(status.getName());
        return dto;
    }

    public static List<TaskStatusDto> map(List<TaskStatus> statuses) {
        return statuses.stream()
                .map(TaskStatusMapper::map)
                .toList();
    }

    public static TaskStatus map(Long entityId) {
        if (entityId == null) return null;
        return TaskStatus.builder().setEntityId(entityId).build();
    }
}
