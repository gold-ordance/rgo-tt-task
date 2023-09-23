package rgo.tt.task.rest.api.tasksboard;

import com.linecorp.armeria.common.HttpResponse;
import rgo.tt.task.rest.api.tasksboard.request.TasksBoardSaveRequest;

public interface RestTasksBoardService {

    HttpResponse findAll();

    HttpResponse findByEntityId(Long entityId);

    HttpResponse save(TasksBoardSaveRequest rq);

    HttpResponse deleteByEntityId(Long entityId);
}
