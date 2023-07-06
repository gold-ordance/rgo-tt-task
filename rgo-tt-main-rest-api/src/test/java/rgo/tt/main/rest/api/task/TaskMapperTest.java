package rgo.tt.main.rest.api.task;

import org.junit.jupiter.api.Test;
import rgo.tt.main.persistence.storage.entity.Task;
import rgo.tt.main.rest.api.task.request.TaskPutRequest;
import rgo.tt.main.rest.api.task.request.TaskSaveRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.common.utils.TestUtils.assertNullFields;

class TaskMapperTest {

    @Test
    void map_saveRequest() throws IllegalAccessException {
        TaskSaveRequest rq = new TaskSaveRequest();
        rq.setName(randomString());

        Task task = TaskMapper.map(rq);

        assertEquals(rq.getName(), task.getName());

        List<String> nonEmptyFields = List.of("name");
        assertNullFields(task, nonEmptyFields);
    }

    @Test
    void map_putRequest() throws IllegalAccessException {
        TaskPutRequest rq = new TaskPutRequest();
        rq.setEntityId(randomPositiveLong());
        rq.setName(randomString());

        Task task = TaskMapper.map(rq);

        assertEquals(rq.getEntityId(), task.getEntityId());
        assertEquals(rq.getName(), task.getName());

        List<String> nonEmptyFields = List.of("entityId", "name");
        assertNullFields(task, nonEmptyFields);
    }
}
