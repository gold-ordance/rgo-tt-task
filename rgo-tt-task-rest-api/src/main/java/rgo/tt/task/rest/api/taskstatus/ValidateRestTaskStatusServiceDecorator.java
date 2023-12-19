package rgo.tt.task.rest.api.taskstatus;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import rgo.tt.task.rest.api.ExceptionCommonHandler;

@Blocking
@ExceptionHandler(ExceptionCommonHandler.class)
public class ValidateRestTaskStatusServiceDecorator implements RestTaskStatusService {

    private final RestTaskStatusService delegate;

    public ValidateRestTaskStatusServiceDecorator(RestTaskStatusService delegate) {
        this.delegate = delegate;
    }

    @Get
    @Override
    public HttpResponse findAll() {
        return delegate.findAll();
    }
}
