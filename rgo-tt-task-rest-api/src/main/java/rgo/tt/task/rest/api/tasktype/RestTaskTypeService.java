package rgo.tt.task.rest.api.tasktype;

import com.linecorp.armeria.common.HttpResponse;

public interface RestTaskTypeService {

    HttpResponse findAll();
}
