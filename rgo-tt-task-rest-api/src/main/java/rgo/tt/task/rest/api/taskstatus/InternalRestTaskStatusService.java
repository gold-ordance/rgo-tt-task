package rgo.tt.task.rest.api.taskstatus;

import com.linecorp.armeria.common.HttpResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.task.persistence.storage.entity.TaskStatus;
import rgo.tt.task.rest.api.taskstatus.response.TaskStatusGetListResponse;
import rgo.tt.task.service.taskstatus.TaskStatusService;

import java.util.List;

import static rgo.tt.common.armeria.rest.RestUtils.mapToHttp;
import static rgo.tt.task.rest.api.taskstatus.TaskStatusMapper.map;

public class InternalRestTaskStatusService implements RestTaskStatusService {

    private final TaskStatusService service;

    public InternalRestTaskStatusService(TaskStatusService service) {
        this.service = service;
    }

    @Override
    public HttpResponse findAll() {
        List<TaskStatus> statuses = service.findAll();
        Response response = TaskStatusGetListResponse.success(map(statuses));
        return mapToHttp(response);
    }
}
