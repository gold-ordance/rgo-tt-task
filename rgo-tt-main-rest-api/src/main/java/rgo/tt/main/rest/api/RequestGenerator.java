package rgo.tt.main.rest.api;

import rgo.tt.main.rest.api.task.request.TaskPutRequest;
import rgo.tt.main.rest.api.task.request.TaskSaveRequest;

import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTaskStatus;

public final class RequestGenerator {

    private RequestGenerator() {
    }

    public static TaskSaveRequest createTaskSaveRequest() {
        TaskSaveRequest rq = new TaskSaveRequest();
        rq.setName(randomString());
        return rq;
    }

    public static TaskPutRequest createTaskPutRequest() {
        TaskPutRequest rq = new TaskPutRequest();
        rq.setEntityId(randomPositiveLong());
        rq.setName(randomString());
        rq.setStatusId(randomTaskStatus().getEntityId());
        return rq;
    }
}
