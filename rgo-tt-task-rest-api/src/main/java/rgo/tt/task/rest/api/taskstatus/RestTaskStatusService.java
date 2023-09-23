package rgo.tt.task.rest.api.taskstatus;

import com.linecorp.armeria.common.HttpResponse;

public interface RestTaskStatusService {

    HttpResponse findAll();
}
