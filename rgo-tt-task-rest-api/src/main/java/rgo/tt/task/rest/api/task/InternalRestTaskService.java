package rgo.tt.task.rest.api.task;

import com.linecorp.armeria.common.HttpResponse;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.task.persistence.storage.entity.Task;
import rgo.tt.task.rest.api.task.request.TaskPutRequest;
import rgo.tt.task.rest.api.task.request.TaskSaveRequest;
import rgo.tt.task.rest.api.task.response.TaskGetEntityResponse;
import rgo.tt.task.rest.api.task.response.TaskGetListResponse;
import rgo.tt.task.rest.api.task.response.TaskModifyResponse;
import rgo.tt.task.service.task.TaskService;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.strip;
import static rgo.tt.common.armeria.rest.HttpDataProcessor.mapToHttp;
import static rgo.tt.task.rest.api.task.TaskMapper.map;

public class InternalRestTaskService implements RestTaskService {

    private final TaskService service;

    public InternalRestTaskService(TaskService service) {
        this.service = service;
    }

    @Override
    public HttpResponse findAllForBoard(Long boardId) {
        List<Task> tasks = service.findAllForBoard(boardId);
        Response response = TaskGetListResponse.success(map(tasks));
        return mapToHttp(response);
    }

    @Override
    public HttpResponse findByEntityId(Long entityId) {
        Optional<Task> task = service.findByEntityId(entityId);
        Response response = task.isPresent()
                ? TaskGetEntityResponse.success(map(task.get()))
                : ErrorResponse.notFound();

        return mapToHttp(response);
    }

    @Override
    public HttpResponse findByName(String name, Long boardId) {
        List<Task> tasks = service.findSoftlyByName(strip(name), boardId);
        Response response = TaskGetListResponse.success(map(tasks));
        return mapToHttp(response);
    }

    @Override
    public HttpResponse save(TaskSaveRequest rq) {
        Task stored = service.save(map(rq));
        Response response = TaskModifyResponse.saved(map(stored));
        return mapToHttp(response);
    }

    @Override
    public HttpResponse update(TaskPutRequest rq) {
        Task updated = service.update(map(rq));
        Response response = TaskModifyResponse.updated(map(updated));
        return mapToHttp(response);
    }

    @Override
    public HttpResponse deleteByEntityId(Long entityId) {
        boolean isDeleted = service.deleteByEntityId(entityId);
        Response response = Response.from(isDeleted);
        return mapToHttp(response);
    }
}
