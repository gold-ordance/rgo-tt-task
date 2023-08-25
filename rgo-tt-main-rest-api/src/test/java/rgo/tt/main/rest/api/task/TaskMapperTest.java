package rgo.tt.main.rest.api.task;

import org.junit.jupiter.api.Test;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.rest.api.task.request.TaskPutRequest;
import rgo.tt.main.rest.api.task.request.TaskSaveRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static rgo.tt.common.utils.TestUtils.assertNullFields;
import static rgo.tt.main.rest.api.RequestGenerator.createTaskPutRequest;
import static rgo.tt.main.rest.api.RequestGenerator.createTaskSaveRequest;

class TaskMapperTest {

    @Test
    void map_saveRequest() throws IllegalAccessException {
        TaskSaveRequest rq = createTaskSaveRequest();
        Task task = TaskMapper.map(rq);

        assertEquals(rq.getName(), task.getName());
        assertEquals(rq.getDescription(), task.getDescription());
        assertEquals(rq.getBoardId(), task.getBoard().getEntityId());
        assertEquals(rq.getTypeId(), task.getType().getEntityId());

        List<String> nonEmptyFields = List.of("name", "description", "board", "type");
        assertNullFields(task, nonEmptyFields);
    }

    @Test
    void map_putRequest() throws IllegalAccessException {
        TaskPutRequest rq = createTaskPutRequest();
        Task task = TaskMapper.map(rq);

        assertEquals(rq.getEntityId(), task.getEntityId());
        assertEquals(rq.getName(), task.getName());
        assertEquals(rq.getDescription(), task.getDescription());
        assertEquals(rq.getTypeId(), task.getType().getEntityId());
        assertEquals(rq.getStatusId(), task.getStatus().getEntityId());

        List<String> nonEmptyFields = List.of("entityId", "name", "description", "type", "status");
        assertNullFields(task, nonEmptyFields);
    }
}
