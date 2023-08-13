package rgo.tt.main.rest.api;

import rgo.tt.main.rest.api.task.request.TaskPutRequest;
import rgo.tt.main.rest.api.task.request.TaskSaveRequest;
import rgo.tt.main.rest.api.tasksboard.request.TasksBoardSaveRequest;

import static rgo.tt.common.utils.RandomUtils.randomBigString;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTaskStatus;
import static rgo.tt.main.persistence.storage.utils.EntityGenerator.randomTaskType;

public final class RequestGenerator {

    private RequestGenerator() {
    }

    public static TaskSaveRequest createTaskSaveRequest() {
        TaskSaveRequest rq = new TaskSaveRequest();
        rq.setName(randomString());
        rq.setDescription(randomBigString());
        rq.setBoardId(randomPositiveLong());
        rq.setTypeId(randomTaskType().getEntityId());
        return rq;
    }

    public static TaskPutRequest createTaskPutRequest() {
        TaskPutRequest rq = new TaskPutRequest();
        rq.setEntityId(randomPositiveLong());
        rq.setName(randomString());
        rq.setDescription(randomBigString());
        rq.setTypeId(randomTaskType().getEntityId());
        rq.setStatusId(randomTaskStatus().getEntityId());
        return rq;
    }

    public static TasksBoardSaveRequest createTasksBoardSaveRequest() {
        TasksBoardSaveRequest rq = new TasksBoardSaveRequest();
        rq.setName(randomString());
        return rq;
    }
}
