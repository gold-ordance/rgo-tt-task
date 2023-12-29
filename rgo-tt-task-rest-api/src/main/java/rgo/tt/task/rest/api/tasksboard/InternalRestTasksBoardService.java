package rgo.tt.task.rest.api.tasksboard;

import com.linecorp.armeria.common.HttpResponse;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.task.persistence.storage.entity.TasksBoard;
import rgo.tt.task.rest.api.tasksboard.request.TasksBoardSaveRequest;
import rgo.tt.task.rest.api.tasksboard.response.TasksBoardGetEntityResponse;
import rgo.tt.task.rest.api.tasksboard.response.TasksBoardGetListResponse;
import rgo.tt.task.rest.api.tasksboard.response.TasksBoardModifyResponse;
import rgo.tt.task.service.tasksboard.TasksBoardService;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.armeria.rest.HttpDataProcessor.mapToHttp;
import static rgo.tt.task.rest.api.tasksboard.TasksBoardMapper.map;

public class InternalRestTasksBoardService implements RestTasksBoardService {

    private final TasksBoardService service;

    public InternalRestTasksBoardService(TasksBoardService service) {
        this.service = service;
    }

    @Override
    public HttpResponse findAll() {
        List<TasksBoard> boards = service.findAll();
        Response response = TasksBoardGetListResponse.success(map(boards));
        return mapToHttp(response);
    }

    @Override
    public HttpResponse findByEntityId(Long entityId) {
        Optional<TasksBoard> board = service.findByEntityId(entityId);
        Response response = board.isPresent()
                ? TasksBoardGetEntityResponse.success(map(board.get()))
                : ErrorResponse.notFound();

        return mapToHttp(response);
    }

    @Override
    public HttpResponse save(TasksBoardSaveRequest rq) {
        TasksBoard board = service.save(map(rq));
        Response response = TasksBoardModifyResponse.saved(map(board));
        return mapToHttp(response);
    }

    @Override
    public HttpResponse deleteByEntityId(Long entityId) {
        boolean isDeleted = service.deleteByEntityId(entityId);
        Response response = Response.from(isDeleted);
        return mapToHttp(response);
    }
}
