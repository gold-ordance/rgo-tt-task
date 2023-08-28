package rgo.tt.main.rest.api;

import org.junit.jupiter.api.Test;
import rgo.tt.main.rest.api.task.request.TaskPutRequest;
import rgo.tt.main.rest.api.task.request.TaskSaveRequest;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardSaveRequest;

import static rgo.tt.common.utils.TestUtils.assertNonNullFields;

class RequestGeneratorTest {

    @Test
    void createTaskSaveRequest() throws IllegalAccessException {
        TaskSaveRequest rq = RequestGenerator.createTaskSaveRequest();
        assertNonNullFields(rq);
    }

    @Test
    void createTaskPutRequest() throws IllegalAccessException {
        TaskPutRequest rq = RequestGenerator.createTaskPutRequest();
        assertNonNullFields(rq);
    }

    @Test
    void createTasksBoardSaveRequest() throws IllegalAccessException {
        TasksBoardSaveRequest rq = RequestGenerator.createTasksBoardSaveRequest();
        assertNonNullFields(rq);
    }
}
