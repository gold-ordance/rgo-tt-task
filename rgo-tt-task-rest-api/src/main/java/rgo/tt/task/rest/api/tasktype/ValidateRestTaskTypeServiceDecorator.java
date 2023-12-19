package rgo.tt.task.rest.api.tasktype;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import rgo.tt.task.rest.api.ExceptionCommonHandler;

@Blocking
@ExceptionHandler(ExceptionCommonHandler.class)
public class ValidateRestTaskTypeServiceDecorator implements RestTaskTypeService {

    private final RestTaskTypeService delegate;

    public ValidateRestTaskTypeServiceDecorator(RestTaskTypeService delegate) {
        this.delegate = delegate;
    }

    @Get
    @Override
    public HttpResponse findAll() {
        return delegate.findAll();
    }
}
