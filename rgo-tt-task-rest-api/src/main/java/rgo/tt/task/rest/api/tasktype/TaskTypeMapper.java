package rgo.tt.task.rest.api.tasktype;

import rgo.tt.task.persistence.storage.entity.TaskType;
import rgo.tt.task.rest.api.tasktype.dto.TaskTypeDto;

import java.util.List;

public final class TaskTypeMapper {

    private TaskTypeMapper() {
    }

    public static TaskTypeDto map(TaskType type) {
        TaskTypeDto dto = new TaskTypeDto();
        dto.setEntityId(type.getEntityId());
        dto.setName(type.getName());
        return dto;
    }

    public static List<TaskTypeDto> map(List<TaskType> types) {
        return types.stream()
                .map(TaskTypeMapper::map)
                .toList();
    }
}
