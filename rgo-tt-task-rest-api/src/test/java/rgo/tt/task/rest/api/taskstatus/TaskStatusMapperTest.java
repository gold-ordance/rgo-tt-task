package rgo.tt.task.rest.api.taskstatus;

import org.junit.jupiter.api.Test;
import rgo.tt.task.persistence.storage.entity.TaskStatus;
import rgo.tt.task.rest.api.taskstatus.dto.TaskStatusDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTaskStatus;

class TaskStatusMapperTest {

    @Test
    void map_toTaskStatusDto() {
        TaskStatus status = randomTaskStatus();
        TaskStatusDto dto = TaskStatusMapper.map(status);
        compare(dto, status);
    }

    @Test
    void map_toTaskStatusDtoList() {
        List<TaskStatus> statuses = List.of(randomTaskStatus());
        List<TaskStatusDto> dtoList = TaskStatusMapper.map(statuses);

        assertThat(statuses).hasSize(1);
        assertThat(dtoList).hasSize(1);
        compare(dtoList.get(0), statuses.get(0));
    }

    @Test
    void map_entityId() {
        long randomEntityId = randomPositiveLong();
        TaskStatus type = TaskStatusMapper.map(randomEntityId);

        assertThat(type.getEntityId()).isEqualTo(randomEntityId);
    }

    private void compare(TaskStatusDto dto, TaskStatus status) {
        assertThat(dto.getEntityId()).isEqualTo(status.getEntityId());
        assertThat(dto.getName()).isEqualTo(status.getName());
    }
}
