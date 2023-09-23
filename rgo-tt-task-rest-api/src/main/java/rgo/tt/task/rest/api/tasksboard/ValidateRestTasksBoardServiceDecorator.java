package rgo.tt.task.rest.api.tasksboard;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.Delete;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.task.rest.api.ExceptionCommonHandler;
import rgo.tt.task.rest.api.tasksboard.request.TasksBoardSaveRequest;

import static rgo.tt.common.validator.ValidatorUtils.validateObjectId;
import static rgo.tt.common.validator.ValidatorUtils.validateString;

@Blocking
@ExceptionHandler(ExceptionCommonHandler.class)
public class ValidateRestTasksBoardServiceDecorator implements RestTasksBoardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateRestTasksBoardServiceDecorator.class);

    private final RestTasksBoardService delegate;

    public ValidateRestTasksBoardServiceDecorator(RestTasksBoardService delegate) {
        this.delegate = delegate;
    }

    @Get
    @Override
    public HttpResponse findAll() {
        LOGGER.info("Request 'findAll' received.");
        return delegate.findAll();
    }

    @Get("/{entityId}")
    @Override
    public HttpResponse findByEntityId(@Param Long entityId) {
        LOGGER.info("Request 'findByEntityId' received: entityId={}", entityId);
        validateObjectId(entityId, "entityId");
        return delegate.findByEntityId(entityId);
    }

    @Post
    @Override
    public HttpResponse save(TasksBoardSaveRequest rq) {
        LOGGER.info("Request 'save' received: request={}", rq);
        validateString(rq.getName(), "name");
        return delegate.save(rq);
    }

    @Delete("/{entityId}")
    @Override
    public HttpResponse deleteByEntityId(@Param Long entityId) {
        LOGGER.info("Request 'deleteByEntityId' received: entityId={}", entityId);
        validateObjectId(entityId, "entityId");
        return delegate.deleteByEntityId(entityId);
    }
}
