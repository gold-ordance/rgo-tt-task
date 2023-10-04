package rgo.tt.task.rest.api.tasktype;

import org.junit.jupiter.api.Test;
import rgo.tt.task.persistence.storage.entity.TaskType;
import rgo.tt.task.rest.api.tasktype.dto.TaskTypeDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTaskType;

class TaskTypeMapperTest {

    @Test
    void map_toTaskTypeDto() {
        TaskType type = randomTaskType();
        TaskTypeDto dto = TaskTypeMapper.map(type);
        compare(dto, type);
    }

    @Test
    void map_toTaskTypeDtoList() {
        List<TaskType> types = List.of(randomTaskType());
        List<TaskTypeDto> dtoList = TaskTypeMapper.map(types);

        assertThat(types).hasSize(1);
        assertThat(dtoList).hasSize(1);
        compare(dtoList.get(0), types.get(0));
    }

    @Test
    void map_entityId() {
        long randomEntityId = randomPositiveLong();
        TaskType type = TaskTypeMapper.map(randomEntityId);

        assertThat(type.getEntityId()).isEqualTo(randomEntityId);
    }

    private void compare(TaskTypeDto dto, TaskType type) {
        assertThat(dto.getEntityId()).isEqualTo(type.getEntityId());
        assertThat(dto.getName()).isEqualTo(type.getName());
    }
}
