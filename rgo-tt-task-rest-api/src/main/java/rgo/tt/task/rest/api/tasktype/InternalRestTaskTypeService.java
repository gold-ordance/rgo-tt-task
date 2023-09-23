package rgo.tt.task.rest.api.tasktype;

import com.linecorp.armeria.common.HttpResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.task.persistence.storage.entity.TaskType;
import rgo.tt.task.rest.api.tasktype.response.TaskTypeGetListResponse;
import rgo.tt.task.service.tasktype.TaskTypeService;

import java.util.List;

import static rgo.tt.common.rest.api.utils.RestUtils.mapToHttp;
import static rgo.tt.task.rest.api.tasktype.TaskTypeMapper.map;

public class InternalRestTaskTypeService implements RestTaskTypeService {

    private final TaskTypeService service;

    public InternalRestTaskTypeService(TaskTypeService service) {
        this.service = service;
    }

    @Override
    public HttpResponse findAll() {
        List<TaskType> types = service.findAll();
        Response response = TaskTypeGetListResponse.success(map(types));
        return mapToHttp(response);
    }
}
