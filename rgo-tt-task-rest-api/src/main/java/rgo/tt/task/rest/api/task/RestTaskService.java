package rgo.tt.task.rest.api.task;

import com.linecorp.armeria.common.HttpResponse;
import rgo.tt.task.rest.api.task.request.TaskPutRequest;
import rgo.tt.task.rest.api.task.request.TaskSaveRequest;

public interface RestTaskService {

    HttpResponse findAllForBoard(Long boardId);

    HttpResponse findByEntityId(Long entityId);

    HttpResponse findByName(String name, Long boardId);

    HttpResponse save(TaskSaveRequest rq);

    HttpResponse update(TaskPutRequest rq);

    HttpResponse deleteByEntityId(Long entityId);
}
