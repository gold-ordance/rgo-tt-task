package rgo.tt.task.rest.api.task;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.Delete;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.MatchesParam;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.Post;
import com.linecorp.armeria.server.annotation.Put;
import rgo.tt.common.armeria.rest.ExceptionCommonHandler;
import rgo.tt.task.rest.api.task.request.TaskPutRequest;
import rgo.tt.task.rest.api.task.request.TaskSaveRequest;

import static rgo.tt.common.validator.ValidatorUtils.validateObjectId;
import static rgo.tt.common.validator.ValidatorUtils.validateString;

@Blocking
@ExceptionHandler(ExceptionCommonHandler.class)
public class ValidateRestTaskServiceDecorator implements RestTaskService {

    private final RestTaskService delegate;

    public ValidateRestTaskServiceDecorator(RestTaskService delegate) {
        this.delegate = delegate;
    }

    @Get
    @MatchesParam("boardId")
    @Override
    public HttpResponse findAllForBoard(@Param Long boardId) {
        validateObjectId(boardId, "boardId");
        return delegate.findAllForBoard(boardId);
    }

    @Get("/{entityId}")
    @Override
    public HttpResponse findByEntityId(@Param Long entityId) {
        validateObjectId(entityId, "entityId");
        return delegate.findByEntityId(entityId);
    }

    @Get
    @MatchesParam("name")
    @MatchesParam("boardId")
    @Override
    public HttpResponse findByName(@Param String name, @Param Long boardId) {
        validateString(name, "name");
        validateObjectId(boardId, "boardId");
        return delegate.findByName(name, boardId);
    }

    @Post
    @Override
    public HttpResponse save(TaskSaveRequest rq) {
        validateString(rq.getName(), "name");
        validateObjectId(rq.getBoardId(), "boardId");
        validateObjectId(rq.getTypeId(), "typeId");
        return delegate.save(rq);
    }

    @Put
    @Override
    public HttpResponse update(TaskPutRequest rq) {
        validateObjectId(rq.getEntityId(), "entityId");
        validateString(rq.getName(), "name");
        validateObjectId(rq.getTypeId(), "typeId");
        validateObjectId(rq.getStatusId(), "statusId");
        return delegate.update(rq);
    }

    @Delete("/{entityId}")
    @Override
    public HttpResponse deleteByEntityId(@Param Long entityId) {
        validateObjectId(entityId, "entityId");
        return delegate.deleteByEntityId(entityId);
    }
}
