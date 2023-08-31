package rgo.tt.main.rest.api;

import org.junit.jupiter.api.Test;
import rgo.tt.main.rest.api.task.request.TaskPutRequest;
import rgo.tt.main.rest.api.task.request.TaskSaveRequest;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardSaveRequest;

import static rgo.tt.common.utils.TestUtils.validateNonNullFields;

class RequestGeneratorTest {

    @Test
    void createTaskSaveRequest() {
        TaskSaveRequest rq = RequestGenerator.createTaskSaveRequest();
        validateNonNullFields(rq);
    }

    @Test
    void createTaskPutRequest() {
        TaskPutRequest rq = RequestGenerator.createTaskPutRequest();
        validateNonNullFields(rq);
    }

    @Test
    void createTasksBoardSaveRequest() {
        TasksBoardSaveRequest rq = RequestGenerator.createTasksBoardSaveRequest();
        validateNonNullFields(rq);
    }
}
