package rgo.tt.task.rest.api.tasktype;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.task.rest.api.ExceptionCommonHandler;

@Blocking
@ExceptionHandler(ExceptionCommonHandler.class)
public class ValidateRestTaskTypeServiceDecorator implements RestTaskTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateRestTaskTypeServiceDecorator.class);

    private final RestTaskTypeService delegate;

    public ValidateRestTaskTypeServiceDecorator(RestTaskTypeService delegate) {
        this.delegate = delegate;
    }

    @Get
    @Override
    public HttpResponse findAll() {
        LOGGER.info("Request 'findAll' received.");
        return delegate.findAll();
    }
}
