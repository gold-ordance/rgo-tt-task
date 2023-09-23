package rgo.tt.task.rest.api.task;

import org.junit.jupiter.api.Test;
import rgo.tt.task.persistence.storage.entity.Task;
import rgo.tt.task.rest.api.task.dto.TaskDto;
import rgo.tt.task.rest.api.task.request.TaskPutRequest;
import rgo.tt.task.rest.api.task.request.TaskSaveRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.TestUtils.validateNullFieldsExcept;
import static rgo.tt.task.persistence.storage.utils.EntityGenerator.randomTask;
import static rgo.tt.task.rest.api.RequestGenerator.createTaskPutRequest;
import static rgo.tt.task.rest.api.RequestGenerator.createTaskSaveRequest;

class TaskMapperTest {

    @Test
    void map_toTaskDto() {
        Task task = randomTask();
        TaskDto dto = TaskMapper.map(task);
        compare(dto, task);
    }

    @Test
    void map_toTaskDtoList() {
        List<Task> tasks = List.of(randomTask());
        List<TaskDto> dtoList = TaskMapper.map(tasks);

        assertThat(tasks).hasSize(1);
        assertThat(dtoList).hasSize(1);
        compare(dtoList.get(0), tasks.get(0));
    }

    @Test
    void map_saveRequest() {
        TaskSaveRequest rq = createTaskSaveRequest();
        Task task = TaskMapper.map(rq);

        assertThat(task.getName()).isEqualTo(rq.getName());
        assertThat(task.getDescription()).isEqualTo(rq.getDescription());
        assertThat(task.getBoard().getEntityId()).isEqualTo(rq.getBoardId());
        assertThat(task.getType().getEntityId()).isEqualTo(rq.getTypeId());

        List<String> nonEmptyFields = List.of("name", "description", "board", "type");
        validateNullFieldsExcept(task, nonEmptyFields);
    }

    @Test
    void map_putRequest() {
        TaskPutRequest rq = createTaskPutRequest();
        Task task = TaskMapper.map(rq);

        assertThat(task.getEntityId()).isEqualTo(rq.getEntityId());
        assertThat(task.getName()).isEqualTo(rq.getName());
        assertThat(task.getDescription()).isEqualTo(rq.getDescription());
        assertThat(task.getType().getEntityId()).isEqualTo(rq.getTypeId());
        assertThat(task.getStatus().getEntityId()).isEqualTo(rq.getStatusId());

        List<String> nonEmptyFields = List.of("entityId", "name", "description", "type", "status");
        validateNullFieldsExcept(task, nonEmptyFields);
    }

    private void compare(TaskDto dto, Task task) {
        assertThat(dto.getEntityId()).isEqualTo(task.getEntityId());
        assertThat(dto.getName()).isEqualTo(task.getName());
        assertThat(dto.getCreatedDate()).isEqualTo(task.getCreatedDate());
        assertThat(dto.getLastModifiedDate()).isEqualTo(task.getLastModifiedDate());
        assertThat(dto.getDescription()).isEqualTo(task.getDescription());
        assertThat(dto.getNumber()).isEqualTo(task.getNumber());
        assertThat(dto.getBoard().getEntityId()).isEqualTo(task.getBoard().getEntityId());
        assertThat(dto.getBoard().getName()).isEqualTo(task.getBoard().getName());
        assertThat(dto.getBoard().getShortName()).isEqualTo(task.getBoard().getShortName());
        assertThat(dto.getType().getEntityId()).isEqualTo(task.getType().getEntityId());
        assertThat(dto.getType().getName()).isEqualTo(task.getType().getName());
        assertThat(dto.getStatus().getEntityId()).isEqualTo(task.getStatus().getEntityId());
        assertThat(dto.getStatus().getName()).isEqualTo(task.getStatus().getName());
    }
}
